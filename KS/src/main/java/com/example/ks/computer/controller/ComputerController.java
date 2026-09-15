package com.example.ks.computer.controller;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computer.dto.CreateComputer;
import com.example.ks.computer.dto.MonitorReportItem;
import com.example.ks.computer.dto.ReportComputerInfo;
import com.example.ks.computer.dto.UpdateComputer;
import com.example.ks.computer.service.ComputerService;
import com.example.ks.department.domain.Department;
import com.example.ks.department.service.DepartmentService;
import com.example.ks.monitor.domain.Monitor;
import com.example.ks.monitor.service.MonitorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;
    private final DepartmentService departmentService;
    private final MonitorService monitorService;


    @GetMapping("/computer")
    public ModelAndView computerList() {
        ModelAndView mav = new ModelAndView("computer");

        List<Computer> computers = computerService.findAll()
                .stream()
                .filter(computer -> !"Y".equals(computer.getDel()))
                .sorted(Comparator
                        .comparing((Computer c) -> c.getDepartment().getDepartmentFloor(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(c -> c.getDepartment().getDepartmentName(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Computer::getComputerPlace, Comparator.nullsLast(Comparator.naturalOrder()))
                )
                .toList();

        mav.addObject("computers", computers);
        return mav;
    }


    @GetMapping("/computer/create")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("computerCreate");
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("/computer/create")
    public String createComputer(@Valid @ModelAttribute CreateComputer createComputer) {
        computerService.create(createComputer);
        return "redirect:/computer";
    }

    @GetMapping("/computer/{computer_id}")
    public ModelAndView computerDetail(@PathVariable("computer_id") int computerId) {
        ModelAndView mav = new ModelAndView("computerDetail");
        Computer computer = computerService.findByComputerId(computerId);
        List<Monitor> monitors = monitorService.findByComputerId(computerId).stream()
                .filter(monitor -> !"Y".equals(monitor.getDel()))
                .toList();
        List<Monitor> availableMonitors = monitorService.findUnlinked();
        mav.addObject("computer", computer);
        mav.addObject("monitors", monitors);
        mav.addObject("availableMonitors", availableMonitors);
        return mav;
    }

    @PostMapping("/computer/{computer_id}/monitor/link")
    public String linkMonitor(@PathVariable("computer_id") int computerId, @RequestParam("monitorId") int monitorId) {
        Computer computer = computerService.findByComputerId(computerId);
        monitorService.linkToComputer(monitorId, computer);
        return "redirect:/computer/" + computerId;
    }

    @GetMapping("/computer/{computer_id}/monitor/unlink/{monitor_id}")
    public String unlinkMonitor(@PathVariable("computer_id") int computerId, @PathVariable("monitor_id") int monitorId) {
        monitorService.unlinkFromComputer(monitorId);
        return "redirect:/computer/" + computerId;
    }

    // 정보 자동수집 배치파일 다운로드
    @GetMapping("/computer/{computer_id}/report/script")
    public ResponseEntity<byte[]> downloadReportBat(@PathVariable("computer_id") int computerId, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        String bat = "@echo off\r\n" +
                "chcp 65001 >nul\r\n" +
                "echo 이 컴퓨터의 정보를 수집해서 서버로 전송합니다...\r\n" +
                "powershell -NoProfile -ExecutionPolicy Bypass -Command \"iex (irm '" + baseUrl + "/computer/" + computerId + "/report/script.ps1')\"\r\n" +
                "echo.\r\n" +
                "pause\r\n";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=collect_" + computerId + ".bat")
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .body(bat.getBytes(StandardCharsets.UTF_8));
    }

    // 실제 정보 수집 로직 (PowerShell)
    @GetMapping("/computer/{computer_id}/report/script.ps1")
    public ResponseEntity<byte[]> downloadReportScript(@PathVariable("computer_id") int computerId, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        String script = """
                # 이 스크립트가 어디에서 문제가 나든(WMI 클래스가 없는 구형 PC 등) 전체가 멈추지 않도록
                # 기본 동작을 '오류 무시'로 설정. 대신 중요한 구간은 아래에서 개별적으로 try/catch로 감싼다.
                $ErrorActionPreference = 'SilentlyContinue'

                # --- [3단계] 이 PC의 정보를 WMI(Windows Management Instrumentation)로 직접 조회 ---

                # CPU 이름 (예: "Intel(R) Xeon(R) W-2133 CPU @ 3.60GHz")
                $cpu = (Get-CimInstance Win32_Processor | Select-Object -First 1).Name

                # 컴퓨터 제조사 + 모델명 (예: "HP HP Z4 G4 Workstation")
                $cs = Get-CimInstance Win32_ComputerSystem
                $model = ("$($cs.Manufacturer) $($cs.Model)").Trim()

                # 조립PC(화이트박스)는 메인보드가 실제 모델명 대신 "System Product Name" 같은
                # 의미없는 더미 값을 돌려주는 경우가 많다. 이런 값이면 메인보드(Win32_BaseBoard)
                # 제조사+모델로 대체해서, 조립PC도 뭔가 식별 가능한 값이 들어가게 한다.
                $junkPatterns = 'To be filled', 'System Product Name', 'Default string', 'O.E.M', 'Not Applicable', 'System manufacturer'
                if ([string]::IsNullOrWhiteSpace($model) -or ($junkPatterns | Where-Object { $model -match [regex]::Escape($_) })) {
                    $board = Get-CimInstance Win32_BaseBoard
                    $boardModel = ("$($board.Manufacturer) $($board.Product)").Trim()
                    if (-not [string]::IsNullOrWhiteSpace($boardModel)) { $model = $boardModel }
                }

                # 실제 메모리 용량(GB). TotalPhysicalMemory는 바이트 단위라 1GB(1024^3)로 나눠서 환산.
                # Windows는 BIOS/통합그래픽 등이 예약해간 만큼을 빼고 보고해서, 16GB 램이 15.4GB처럼 잡히는 경우가 흔하다.
                # 램은 항상 2의 거듭제곱(4/8/16/32GB)으로 나오므로, 반올림 대신 올림을 써서 정격 용량으로 복원한다.
                $memoryGb = [math]::Ceiling($cs.TotalPhysicalMemory / 1GB)

                # OS 이름 전체 문자열 (예: "Microsoft Windows 11 Pro for Workstations")
                $os = (Get-CimInstance Win32_OperatingSystem).Caption

                # 생산일은 정확히 알 방법이 없어서, BIOS가 마지막으로 구워진(릴리즈된) 날짜로 근사한다.
                # 보통 공장 출하 시점과 크게 다르지 않다. Win32_BIOS가 없는 극히 예외적인 환경도 있어 try/catch.
                $productDate = $null
                try {
                    $biosDate = (Get-CimInstance Win32_BIOS).ReleaseDate
                    if ($biosDate) { $productDate = $biosDate.ToString('yyyy-MM-dd') }
                } catch {}

                # 모니터 정보: 듀얼/트리플 모니터도 전부 잡아야 하므로 -First 1을 쓰지 않고 전체를 순회한다.
                # WmiMonitorID(제조사, EDID상 제조 연/주차)와 WmiMonitorBasicDisplayParams(화면 물리 크기)는
                # 서로 다른 CIM 클래스지만, 같은 물리 모니터라면 InstanceName 값이 동일하다.
                # 그래서 InstanceName을 key로 하는 해시테이블을 만들어 두 정보를 짝지어 합친다.
                $monitors = @()
                try {
                    $ids = @(Get-CimInstance -Namespace root\\wmi -ClassName WmiMonitorID)
                    $paramsAll = @(Get-CimInstance -Namespace root\\wmi -ClassName WmiMonitorBasicDisplayParams)
                    $paramsByInstance = @{}
                    foreach ($p in $paramsAll) { $paramsByInstance[$p.InstanceName] = $p }

                    foreach ($m in $ids) {
                        # ManufacturerName은 문자 코드가 담긴 배열(예: 66,78,81 = "BNQ")이라 문자열로 변환.
                        # 뒤쪽 남는 0(널 문자)은 Trim([char]0)으로 제거.
                        $manufacturer = ([string]::new([char[]]$m.ManufacturerName)).Trim([char]0)

                        # 제조 "연도"와 "주차(1~53주)"만 EDID에 들어있어서, 그 주차의 첫날을
                        # 실제 날짜로 환산해 근사 제조일을 만든다. (예: 2019년 26주차 -> 2019-06-27 근방)
                        $manufactureDate = $null
                        if ($m.YearOfManufacture -gt 0) {
                            $week = if ($m.WeekOfManufacture -gt 0) { $m.WeekOfManufacture } else { 1 }
                            $approxDate = (Get-Date -Year $m.YearOfManufacture -Month 1 -Day 1).AddDays(($week - 1) * 7)
                            $manufactureDate = $approxDate.ToString('yyyy-MM-dd')
                        }

                        # 화면 물리 크기(가로/세로 cm)로 피타고라스 정리를 써서 대각선(인치) 계산.
                        # 이 시스템은 관례적으로 인치 기호를 큰따옴표(")가 아니라 백틱(`)으로 표기하고 있어서
                        # 기존 데이터와 형식을 맞추기 위해 그대로 백틱을 붙인다.
                        $size = $null
                        $p = $paramsByInstance[$m.InstanceName]
                        if ($p -and $p.MaxHorizontalImageSize -gt 0) {
                            $wCm = $p.MaxHorizontalImageSize
                            $hCm = $p.MaxVerticalImageSize
                            $diagInch = [math]::Round([math]::Sqrt($wCm*$wCm + $hCm*$hCm) / 2.54, 1)
                            $size = "$diagInch" + '`'
                        }

                        # 모니터 1대의 정보를 해시테이블로 만들어 배열에 추가. 감지된 모니터 수만큼 쌓인다.
                        $monitors += @{ manufacturer = $manufacturer; size = $size; manufactureDate = $manufactureDate }
                    }
                } catch {}

                # 화면에 수집 결과를 미리 보여줘서, 전송 전에 뭐가 잡혔는지 바로 확인할 수 있게 한다.
                Write-Host "수집된 정보 -> CPU: $cpu / 모델: $model / 메모리: $memoryGb GB / OS: $os / 생산일(근사): $productDate / 모니터 $($monitors.Count)대: $($monitors | ConvertTo-Json -Compress)"

                # --- [4단계] 수집한 값들을 하나의 JSON으로 묶어서 서버로 전송 ---
                # monitors가 "배열 안에 객체가 여러 개" 들어있는 중첩 구조라, ConvertTo-Json의 기본 깊이(2)로는
                # 다 안 펼쳐질 수 있어서 -Depth를 넉넉하게(5) 준다.
                $body = @{
                    cpu = $cpu
                    model = $model
                    memory = "$memoryGb GB"
                    os = $os
                    productDate = $productDate
                    monitors = $monitors
                } | ConvertTo-Json -Depth 5

                # POST로 전송. 서버가 연결을 거부하거나(방화벽 등) 400 에러를 주는 경우를 대비해 try/catch로 감싼다.
                try {
                    $response = Invoke-RestMethod -Uri '%s/computer/%d/report' -Method Post -Body $body -ContentType 'application/json; charset=utf-8'
                    # --- [7단계] 서버가 처리 결과로 돌려준 메시지를 화면에 그대로 출력 ---
                    Write-Host "전송 완료: $response"
                } catch {
                    # $_.ErrorDetails.Message에는 서버가 400 응답과 함께 보낸 에러 문구(예: "IP로 등록된 컴퓨터를 찾을 수 없습니다")가 들어있다.
                    Write-Host "전송 실패: $($_.ErrorDetails.Message)"
                }
                """.formatted(baseUrl, computerId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .body(script.getBytes(StandardCharsets.UTF_8));
    }

    // --- [5단계] 서버가 받는 지점 ---
    // 스크립트가 '/computer/{id}/report'로 POST한 JSON을, Spring이 Jackson 라이브러리를 이용해
    // 자동으로 ReportComputerInfo 객체로 역직렬화해서 파라미터로 넘겨준다. (개발자가 JSON 파싱 코드를 직접 안 짜도 됨)
    @PostMapping("/computer/{computer_id}/report")
    @ResponseBody
    public ResponseEntity<String> reportComputerInfo(@PathVariable("computer_id") int computerId,
                                                      @RequestBody ReportComputerInfo report) {
        // --- [6단계-1] 컴퓨터 테이블 반영: CPU/모델/메모리/OS는 덮어쓰고, 생산일은 기존 값 없을 때만 채움 ---
        Computer computer = computerService.applyReport(computerId, report);

        // --- [6단계-2] 모니터 테이블 반영 ---
        MonitorSyncResult result = applyMonitorReports(computer, report);

        // 이 문자열이 그대로 HTTP 응답 본문이 되고, 스크립트가 $response로 받아서 화면에 출력한다(7단계)
        return ResponseEntity.ok(result.toMessage());
    }

    // 이 컴퓨터에 연결돼있던 모니터는 전부 폐기 처리(자산 기록은 남기고 연결만 해제)하고,
    // 이번에 감지된 모니터를 전부 새 자산으로 등록해서 연결한다.
    private record MonitorSyncResult(int disposed, int registered) {
        String toMessage() {
            return "기존 모니터 폐기 " + disposed + "건, 신규 등록/연결 " + registered + "건";
        }
    }

    private MonitorSyncResult applyMonitorReports(Computer computer, ReportComputerInfo report) {
        // 1) 현재 이 컴퓨터에 연결된(그리고 아직 폐기 안 된) 모니터 목록을 가져온다.
        List<Monitor> linked = monitorService.findByComputerId(computer.getComputerId()).stream()
                .filter(monitor -> !"Y".equals(monitor.getDel()))
                .toList();

        // 2) 그 모니터들을 전부 폐기 처리한다. 실제 DELETE가 아니라 monitor_del=Y로 표시하고
        //    연결(computer_id)만 끊는 것이라, 자산 이력 자체는 남는다.
        for (Monitor monitor : linked) {
            monitorService.disposeAndUnlink(monitor.getMonitorId(), "정보 자동수집으로 대체됨");
        }

        // 3) 이번에 실제로 감지된 모니터 개수만큼 새 모니터 자산을 등록하고 이 컴퓨터에 바로 연결한다.
        //    몇 대가 연결돼 있었는지와 무관하게, 매번 "지금 실제로 꽂혀있는 구성"으로 깔끔하게 다시 만든다.
        List<MonitorReportItem> reportedMonitors = report.monitors() == null ? List.of() : report.monitors();
        for (MonitorReportItem item : reportedMonitors) {
            monitorService.registerAndLink(computer, item.manufacturer(), item.size(), item.manufactureDate());
        }

        return new MonitorSyncResult(linked.size(), reportedMonitors.size());
    }

    // 정보 자동수집 배치파일 다운로드 (전체용 - 실행하는 PC의 IP로 대상 컴퓨터를 찾음)
    @GetMapping("/computer/report/script")
    public ResponseEntity<byte[]> downloadReportBatByIp(HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        String bat = "@echo off\r\n" +
                "chcp 65001 >nul\r\n" +
                "echo 이 컴퓨터의 정보를 수집해서 서버로 전송합니다...\r\n" +
                "powershell -NoProfile -ExecutionPolicy Bypass -Command \"iex (irm '" + baseUrl + "/computer/report/script.ps1')\"\r\n" +
                "echo.\r\n" +
                "pause\r\n";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=collect.bat")
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .body(bat.getBytes(StandardCharsets.UTF_8));
    }

    // 실제 정보 수집 로직 (PowerShell) - 실행 PC의 IP를 함께 전송
    @GetMapping("/computer/report/script.ps1")
    public ResponseEntity<byte[]> downloadReportScriptByIp(HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        String script = """
                $ErrorActionPreference = 'SilentlyContinue'

                # --- [3단계-1] 이 스크립트는 컴퓨터 ID를 모르는 상태로 배포되므로,
                # 먼저 이 PC의 실제 IP를 알아내서 서버가 나중에 어떤 컴퓨터인지 찾을 수 있게 한다.
                # 기본 게이트웨이가 설정돼 있고 상태가 'Up'인 어댑터 = 실제로 쓰고 있는 네트워크 카드로 간주.
                $ip = (Get-NetIPConfiguration | Where-Object {
                    $_.IPv4DefaultGateway -ne $null -and $_.NetAdapter.Status -eq 'Up'
                } | Select-Object -First 1 -ExpandProperty IPv4Address).IPAddress
                if (-not $ip) {
                    # 위 방법으로 못 찾으면(드문 경우) 자기 자신에게 핑을 날려서 응답 IP를 대신 사용.
                    $ip = (Test-Connection -ComputerName $env:COMPUTERNAME -Count 1).IPV4Address.IPAddressToString
                }

                # --- [3단계-2] 이 PC의 정보를 WMI로 직접 조회 (특정 ID용 스크립트와 동일한 로직) ---

                # CPU 이름
                $cpu = (Get-CimInstance Win32_Processor | Select-Object -First 1).Name

                # 컴퓨터 제조사 + 모델명
                $cs = Get-CimInstance Win32_ComputerSystem
                $model = ("$($cs.Manufacturer) $($cs.Model)").Trim()

                # 조립PC는 메인보드가 더미 값을 주는 경우가 많아, 그럴 때 메인보드 제조사+모델로 대체
                $junkPatterns = 'To be filled', 'System Product Name', 'Default string', 'O.E.M', 'Not Applicable', 'System manufacturer'
                if ([string]::IsNullOrWhiteSpace($model) -or ($junkPatterns | Where-Object { $model -match [regex]::Escape($_) })) {
                    $board = Get-CimInstance Win32_BaseBoard
                    $boardModel = ("$($board.Manufacturer) $($board.Product)").Trim()
                    if (-not [string]::IsNullOrWhiteSpace($boardModel)) { $model = $boardModel }
                }

                # 메모리 용량(GB). 반올림 대신 올림 — Windows가 예약분을 뺀 실사용 가능량만 보고해서
                # 16GB 램이 15.x GB로 잡히는 경우가 흔한데, 램은 2의 거듭제곱 단위로 나오므로 올림이 정격 용량과 더 잘 맞는다.
                $memoryGb = [math]::Ceiling($cs.TotalPhysicalMemory / 1GB)

                # OS 이름
                $os = (Get-CimInstance Win32_OperatingSystem).Caption

                # 생산일 근사치: BIOS 릴리즈 날짜
                $productDate = $null
                try {
                    $biosDate = (Get-CimInstance Win32_BIOS).ReleaseDate
                    if ($biosDate) { $productDate = $biosDate.ToString('yyyy-MM-dd') }
                } catch {}

                # 모니터 전체(듀얼/트리플 포함) 수집: WmiMonitorID + WmiMonitorBasicDisplayParams를
                # InstanceName으로 짝지어서 모니터별 제조사/제조년월/화면크기를 뽑는다.
                $monitors = @()
                try {
                    $ids = @(Get-CimInstance -Namespace root\\wmi -ClassName WmiMonitorID)
                    $paramsAll = @(Get-CimInstance -Namespace root\\wmi -ClassName WmiMonitorBasicDisplayParams)
                    $paramsByInstance = @{}
                    foreach ($p in $paramsAll) { $paramsByInstance[$p.InstanceName] = $p }

                    foreach ($m in $ids) {
                        # EDID 제조사 코드(문자 코드 배열)를 문자열로 변환하고 널 문자 제거
                        $manufacturer = ([string]::new([char[]]$m.ManufacturerName)).Trim([char]0)

                        # 제조 연도+주차를 실제 날짜로 환산 (근사치)
                        $manufactureDate = $null
                        if ($m.YearOfManufacture -gt 0) {
                            $week = if ($m.WeekOfManufacture -gt 0) { $m.WeekOfManufacture } else { 1 }
                            $approxDate = (Get-Date -Year $m.YearOfManufacture -Month 1 -Day 1).AddDays(($week - 1) * 7)
                            $manufactureDate = $approxDate.ToString('yyyy-MM-dd')
                        }

                        # 화면 물리 크기(cm)로 대각선 인치 계산. 이 시스템 관례대로 인치 기호는 백틱(`) 사용.
                        $size = $null
                        $p = $paramsByInstance[$m.InstanceName]
                        if ($p -and $p.MaxHorizontalImageSize -gt 0) {
                            $wCm = $p.MaxHorizontalImageSize
                            $hCm = $p.MaxVerticalImageSize
                            $diagInch = [math]::Round([math]::Sqrt($wCm*$wCm + $hCm*$hCm) / 2.54, 1)
                            $size = "$diagInch" + '`'
                        }

                        $monitors += @{ manufacturer = $manufacturer; size = $size; manufactureDate = $manufactureDate }
                    }
                } catch {}

                # 전송 전에 뭐가 잡혔는지 화면에서 바로 확인
                Write-Host "이 PC의 IP: $ip"
                Write-Host "수집된 정보 -> CPU: $cpu / 모델: $model / 메모리: $memoryGb GB / OS: $os / 생산일(근사): $productDate / 모니터 $($monitors.Count)대: $($monitors | ConvertTo-Json -Compress)"

                # --- [4단계] JSON으로 묶어서 전송. IP도 함께 실어보내서, 서버가 이 값으로 어떤 컴퓨터인지 찾는다. ---
                $body = @{
                    ip = $ip
                    cpu = $cpu
                    model = $model
                    memory = "$memoryGb GB"
                    os = $os
                    productDate = $productDate
                    monitors = $monitors
                } | ConvertTo-Json -Depth 5

                try {
                    Invoke-RestMethod -Uri '%s/computer/report' -Method Post -Body $body -ContentType 'application/json; charset=utf-8'
                    # --- [7단계] 서버 처리 결과를 화면에 출력 ---
                    Write-Host "전송 완료"
                } catch {
                    # IP가 등록 안 돼있거나 중복이면 서버가 400과 함께 이유를 알려준다 (ErrorDetails.Message)
                    Write-Host "전송 실패: $($_.ErrorDetails.Message)"
                }
                """.formatted(baseUrl);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .body(script.getBytes(StandardCharsets.UTF_8));
    }

    // --- [5단계] 서버가 받는 지점 (IP 기반 버전) ---
    // 이번엔 URL에 컴퓨터 ID가 없으므로, JSON 안에 실려온 report.ip()로 어떤 컴퓨터인지부터 찾아야 한다.
    @PostMapping("/computer/report")
    @ResponseBody
    public ResponseEntity<String> reportComputerInfoByIp(@RequestBody ReportComputerInfo report) {
        try {
            // --- [6단계-1] IP로 컴퓨터를 특정하고, 컴퓨터 테이블 반영 ---
            // (computer_ip 컬럼이 축약 코드로 저장된 경우까지 함께 비교하는 로직은 ComputerService에 있음)
            Computer computer = computerService.applyReportByIp(report);

            // --- [6단계-2] 모니터 테이블 반영 (특정 ID 버전과 동일한 로직 재사용) ---
            MonitorSyncResult result = applyMonitorReports(computer, report);

            return ResponseEntity.ok("OK: computer_id=" + computer.getComputerId() + ", " + result.toMessage());
        } catch (RuntimeException e) {
            // IP 미등록/중복 등으로 컴퓨터를 특정할 수 없으면 400과 함께 이유를 돌려준다.
            // 이게 스크립트의 catch 블록에서 $_.ErrorDetails.Message로 잡혀 화면에 출력된다.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/computer/update/{computer_id}")
    public ModelAndView updateForm(@PathVariable("computer_id") int computerId) {
        ModelAndView mav = new ModelAndView("computerUpdate");
        Computer computer = computerService.findByComputerId(computerId);
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();
        mav.addObject("computer", computer);
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("/computer/update/{computer_id}")
    public String updateComputer(@Valid @ModelAttribute UpdateComputer updateComputer, @PathVariable("computer_id") int computerId) {
        computerService.update(updateComputer,computerId);
        return "redirect:/computer";
    }

    @GetMapping("/computer/delete/{computer_id}")
    public String deleteComputer(@PathVariable("computer_id") int computerId) {
        computerService.delete(computerId);
        return "redirect:/computer";
    }

}
