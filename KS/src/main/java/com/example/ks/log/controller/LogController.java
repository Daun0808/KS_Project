package com.example.ks.log.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class LogController {

    private static final File LOG_FILE = new File("logs/app.log");
    private static final int MAX_TAIL_BYTES = 300_000; // 파일이 커도 최근 부분만 읽음

    @GetMapping("/logs")
    public ModelAndView logsPage() {
        return new ModelAndView("logs");
    }

    // 로그 파일의 최근 N줄을 읽어서 그대로 반환 (프론트에서 주기적으로 호출)
    @GetMapping("/logs/tail")
    @ResponseBody
    public String tail(@RequestParam(value = "lines", defaultValue = "300") int lines) {
        if (!LOG_FILE.exists()) {
            return "(로그 파일이 아직 없습니다. 서버가 재시작되면 생성됩니다.)";
        }

        try (RandomAccessFile raf = new RandomAccessFile(LOG_FILE, "r")) {
            long length = raf.length();
            long start = Math.max(0, length - MAX_TAIL_BYTES);
            raf.seek(start);

            byte[] buffer = new byte[(int) (length - start)];
            raf.readFully(buffer);
            String content = new String(buffer, StandardCharsets.UTF_8);

            List<String> allLines = List.of(content.split("\n", -1));
            int from = Math.max(0, allLines.size() - lines);
            return String.join("\n", allLines.subList(from, allLines.size()));
        } catch (IOException e) {
            return "로그 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage();
        }
    }
}
