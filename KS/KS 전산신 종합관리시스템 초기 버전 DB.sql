-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        11.7.2-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- ks 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `ks` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `ks`;

-- 테이블 ks.department 구조 내보내기
CREATE TABLE IF NOT EXISTS `department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '부서 키값',
  `department_name` varchar(255) DEFAULT NULL,
  `department_floor` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.department:~51 rows (대략적) 내보내기
INSERT IGNORE INTO `department` (`department_id`, `department_name`, `department_floor`, `del`) VALUES
	(1, '입력테스트 2', '1', 'Y'),
	(2, 'test', '2', 'Y'),
	(3, '입력테스트', '2', 'Y'),
	(4, '영상의학과', '1', 'N'),
	(5, '원무과', '1', 'N'),
	(6, '안내데스크', '1', 'N'),
	(7, '감염관리실', '1', 'N'),
	(8, '정형외과', '1', 'N'),
	(9, '약제과', '1', 'N'),
	(10, '전산실', '1', 'N'),
	(11, '진료협력실', '1', 'N'),
	(12, '신경외과', '1', 'N'),
	(13, '주사실', '1', 'N'),
	(14, '무인수납기', '1', 'N'),
	(15, '재활치료실', '2', 'N'),
	(16, '내과', '2', 'N'),
	(17, '심장내과', '2', 'N'),
	(18, '전산실', '2', 'N'),
	(19, '신경과', '2', 'N'),
	(20, '외과', '2', 'N'),
	(21, '소아청소년과', '2', 'N'),
	(22, '치과', '2', 'N'),
	(23, '원무과', '2', 'N'),
	(24, '내시경실', '2', 'N'),
	(25, '진단검사실', '2', 'N'),
	(26, '영상의학과', '2', 'N'),
	(27, '인공신장실', '3', 'N'),
	(28, '중앙공급실', '3', 'N'),
	(29, '심혈관조영실', '3', 'N'),
	(30, '중환자실', '3', 'N'),
	(31, '수술실', '3', 'N'),
	(32, '5A병동', '5', 'N'),
	(33, '5B병동', '5', 'N'),
	(34, '6A병동', '6', 'N'),
	(35, '6B병동', '6', 'N'),
	(36, '7A병동', '7', 'N'),
	(37, '7B병동', '7', 'N'),
	(38, '간호과장실', '8', 'N'),
	(39, '교육실', '8', 'N'),
	(40, '병원장실', '8', 'N'),
	(41, '심사과', '8', 'N'),
	(42, '연구실03', '8', 'N'),
	(43, '연구실04', '8', 'N'),
	(44, '연구실06', '8', 'N'),
	(45, '연구실08', '8', 'N'),
	(46, '영양실', '8', 'N'),
	(47, '총무과', '8', 'N'),
	(48, '행정부장실', '8', 'N'),
	(49, '회계팀', '8', 'N'),
	(50, '응급센터', '1', 'N'),
	(51, '관리과', '지하', 'N'),
	(52, '종합검진', '지하', 'N');

-- 테이블 ks.printer 구조 내보내기
CREATE TABLE IF NOT EXISTS `printer` (
  `printer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '프린트 키값',
  `department_id` int(11) NOT NULL COMMENT '부서 키값',
  `print_place` varchar(255) DEFAULT NULL,
  `print_place_date` date NOT NULL COMMENT '배치 날짜',
  `print_name` varchar(255) DEFAULT NULL,
  `print_code` varchar(255) DEFAULT NULL,
  `print_color` varchar(255) DEFAULT NULL,
  `print_product_date` date NOT NULL COMMENT '생산년도',
  `print_buy_date` date NOT NULL COMMENT '구입년도',
  `print_serial_number` varchar(255) DEFAULT NULL,
  `print_unique_number` varchar(255) DEFAULT NULL,
  `print_ip` varchar(255) DEFAULT NULL,
  `print_text` varchar(255) DEFAULT NULL,
  `print_del` varchar(255) DEFAULT NULL,
  `print_del_date` date DEFAULT NULL COMMENT '폐기 날짜',
  `print_del_text` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`printer_id`),
  KEY `FK_printer_department` (`department_id`),
  CONSTRAINT `FK_printer_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.printer:~127 rows (대략적) 내보내기
INSERT IGNORE INTO `printer` (`printer_id`, `department_id`, `print_place`, `print_place_date`, `print_name`, `print_code`, `print_color`, `print_product_date`, `print_buy_date`, `print_serial_number`, `print_unique_number`, `print_ip`, `print_text`, `print_del`, `print_del_date`, `print_del_text`, `del`) VALUES
	(1, 1, '테스트 장소', '2025-04-22', '삼성', '테스트', 'C', '2025-04-22', '2025-04-22', '', '테스트 번호', '', '', 'Y', NULL, '', 'N'),
	(2, 3, '테스트 장소2', '2025-04-22', '삼성', '테스트2', 'C', '2025-04-22', '2025-04-22', '', '테스트 번호2', '', '', 'Y', NULL, '', 'N'),
	(3, 4, '영상의학과 본관', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-30', '', 'PR-11-005', '', '', 'N', NULL, NULL, 'N'),
	(4, 5, '원무 접수수납7', '2011-03-01', 'Samsung', 'SL-M3710ND', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-006', '3.129', '처방전', 'N', NULL, NULL, 'N'),
	(5, 6, '안내데스크', '2013-03-01', 'Samsung', 'ML-2540R', 'B', '2013-03-01', '2013-03-01', '', 'PR-13-001', '', '', 'N', NULL, '', 'N'),
	(6, 7, '감염관리실', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-001', '', '', 'N', NULL, NULL, 'N'),
	(7, 8, '정형외과3 외래', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', 'ZD17B8GF5B000MH', 'PR-14-002', '', '', 'N', NULL, '', 'N'),
	(8, 9, '약제과 약사', '2014-08-01', 'Samsung', 'SL-M2670FN', 'B', '2014-08-01', '2014-08-01', '', 'PR-14-011', '2.114', '', 'N', NULL, NULL, 'N'),
	(9, 5, '원무 접수수납3', '2016-09-01', 'Samsung', 'SL-M3320ND', 'B', '2016-09-01', '2016-09-01', '', 'PR-16-007', '3.117', '확인증', 'N', NULL, NULL, 'N'),
	(10, 5, '원무 접수수납4-5', '2016-09-01', 'Samsung', 'SL-M3320ND', 'B', '2016-09-01', '2016-09-01', '', 'PR-16-008', '3.144', '확인증', 'N', NULL, NULL, 'N'),
	(11, 5, '원무 접수수납7', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', '', 'PR-16-010', '3.139', '확인증 / 픽업롤러 교체 ', 'N', NULL, NULL, 'N'),
	(12, 5, '원무 접수수납1-2', '2016-11-01', 'Samsung', 'SL-M3320ND', 'B', '2016-11-01', '2016-11-01', '', 'PR-16-018', '3.116', '처방전', 'N', NULL, NULL, 'N'),
	(13, 5, '원무 접수수납 3', '2016-11-01', 'Samsung', 'SL-M3320ND', 'B', '2016-11-01', '2016-11-01', '', 'PR-16-019', '3.134', '영수증', 'N', NULL, NULL, 'N'),
	(14, 5, '원무 접수수납 3', '2016-11-01', 'Samsung', 'SL-M3320ND', 'B', '2016-11-01', '2016-11-01', '', 'PR-16-020', '3.135', '처방전', 'N', NULL, NULL, 'N'),
	(15, 8, '정형외과1 진료실', '2025-02-01', 'Samsung', 'SL-M2843DW', 'B', '2025-01-01', '2025-02-01', '', 'PR-25-001', '', '', 'N', NULL, NULL, 'N'),
	(16, 10, '전산실 예비', '2017-02-01', 'Samsung', 'SL-M2620', 'B', '2017-02-01', '2017-02-01', '', 'PR-17-003', '', '하단 출력 1칸 안됨', 'N', NULL, NULL, 'N'),
	(17, 11, '진료협력실', '2017-12-01', 'Samsung', 'SL-M2670FN', 'B', '2017-12-01', '2017-12-01', '', 'PR-17-006', '3.99', '', 'N', NULL, NULL, 'N'),
	(18, 12, '신경외과 외래', '2017-12-01', 'Samsung', 'SL-M3820ND', 'B', '2017-12-01', '2017-12-01', '', 'PR-17-007', '2.138', '', 'N', NULL, NULL, 'N'),
	(19, 5, '원무 접수수납 공통', '2018-05-01', 'Samsung', 'SL-M2670FN', 'B', '2018-05-01', '2018-05-01', '', 'PR-18-002', '2.224', 'Fax', 'N', NULL, NULL, 'N'),
	(20, 13, '주사실', '2018-12-01', 'Samsung', 'SL-M2670FN', 'B', '2018-12-01', '2018-12-01', '', 'PR-18-003', '', '', 'N', NULL, NULL, 'N'),
	(21, 5, '원무접수수납1-2', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-002', '3.131', '영수증', 'N', NULL, NULL, 'N'),
	(22, 5, '원무접수수납4-5', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-003', '3.136', '영수증', 'N', NULL, NULL, 'N'),
	(23, 5, '원무접수수납4-5', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-004', '3.137', '처방전', 'N', NULL, NULL, 'N'),
	(24, 5, '원무접수수납7', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-005', '3.128', '영수증', 'N', NULL, NULL, 'N'),
	(25, 5, '원무과 사무실', '2020-02-01', 'HP', 'Office Jet Pro 8210', 'C', '2020-02-01', '2020-02-01', '', 'PR-20-001', '3.119', '', 'N', NULL, NULL, 'N'),
	(26, 14, '무인수납기', '2021-05-01', 'HP', 'M607', 'C', '2021-05-01', '2021-05-01', 'CNBRN3S01P K0Q14A', 'PR-21-005', '', '무인수납기', 'N', NULL, '', 'N'),
	(27, 5, '원무 접수수납 1-2', '2022-04-01', 'Samsung', 'SL-M3820ND', 'B', '2022-04-01', '2022-04-01', '0A7WB8GT5A00TXK', 'PR-22-003', '3.130', '확인증', 'N', NULL, NULL, 'N'),
	(28, 5, '원무 접수수납 5-6', '2022-04-01', 'Samsung', 'SL-M4030ND', 'B', '2022-04-01', '2202-04-01', '0A7WB8GT5A00TXK', 'PR-22-005', '3.138', '제증명', 'N', NULL, '', 'N'),
	(29, 9, '악제과', '2022-04-01', 'Samsung', 'SL-M3820ND', 'B', '2022-04-01', '2022-04-01', '', 'PR-22-002', '3.119', '약봉투', 'N', NULL, NULL, 'N'),
	(30, 5, '원무 접수수납 5-6', '2022-06-01', 'Samsung', 'SL-M2893FW', 'B', '2022-06-01', '2022-06-01', '', 'PR-22-005', '3.151', 'Scan', 'N', NULL, NULL, 'N'),
	(31, 10, '전산실 예비', '2023-04-01', 'Samsung', 'SL-M3220ND', 'B', '2023-04-01', '2023-04-01', '', 'PR-23-004', '', '', 'N', NULL, NULL, 'N'),
	(32, 5, '원무 접수수납4', '2023-04-01', 'Samsung', 'SL-M3220ND', 'B', '2023-04-01', '2023-04-01', 'ZPMCB8GTAA009SD', 'PR-23-007', '3.132', '제증명', 'N', NULL, NULL, 'N'),
	(33, 10, '전산실 예비', '2023-04-01', 'Samsung', 'C433W', 'C', '2023-04-01', '2023-04-01', '', 'PR-23-009', '', '', 'N', NULL, NULL, 'N'),
	(34, 9, '약제과', '2023-07-01', 'Samsung', 'SL-M3220ND', 'B', '2022-10-01', '2023-07-01', '', 'PR-23-012', '2.116', '복약지도', 'N', NULL, NULL, 'N'),
	(35, 9, '약제과', '2023-08-01', 'HP', 'HP M501dn', 'B', '2023-08-01', '2023-08-01', '', 'PR-23-014', '2.115', '처방전', 'N', NULL, NULL, 'N'),
	(36, 5, '원무 접수수납 공통', '2023-08-01', 'HP', 'HP M501dn', 'B', '2023-08-01', '2023-08-01', '', 'PR-23-013', '2.050', '제증명', 'N', NULL, '', 'N'),
	(37, 5, '원무과 사무실', '2024-03-01', 'Samsung', 'SL-M2843DW', 'B', '2024-03-01', '2024-03-01', 'SEC842519E00376', 'PR-24-005', '3.120', '', 'N', NULL, NULL, 'N'),
	(38, 15, '재활치료실', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-007', '', '', 'N', NULL, NULL, 'N'),
	(39, 16, '내과6 외래', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-008', '', '', 'N', NULL, NULL, 'N'),
	(40, 17, '심장내과 외래', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-009', '', '', 'N', NULL, NULL, 'N'),
	(41, 18, '전산실', '2011-03-01', 'Samsung', 'SCX-4833FR', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-010', '3.221', '', 'N', NULL, NULL, 'N'),
	(42, 19, '신경과 외래', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-003', '', '', 'N', NULL, NULL, 'N'),
	(43, 19, '신경과 진료실', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-004', '', '', 'N', NULL, NULL, 'N'),
	(44, 16, '내과1 외래', '2014-06-01', 'Samsung', 'SL-M2620', 'B', '2014-06-01', '2014-06-01', '', 'PR-14-009', '', '', 'N', NULL, NULL, 'N'),
	(45, 15, '물리치료실', '2014-08-01', 'Samsung', 'SL-M2620', 'B', '2014-08-01', '2014-08-01', '', 'PR-14-012', '', '', 'N', NULL, NULL, 'N'),
	(46, 18, '전산실', '2014-10-01', 'Samsung', 'C433W', 'C', '2014-10-01', '2014-10-01', '', 'PR-14-013', '', '', 'N', NULL, '', 'N'),
	(47, 15, '재활', '2015-04-01', 'Samsung', 'SL-M2620', 'B', '2015-04-01', '2015-04-01', '', 'PR-15-001', '', '', 'N', NULL, NULL, 'N'),
	(48, 25, '진단검사실 혈액학', '2015-05-01', 'Samsung', 'SL-M2620', 'B', '2015-05-01', '2015-05-01', '', 'PR-15-002', '', '혈액학', 'N', NULL, NULL, 'N'),
	(49, 25, '진단검사실', '2016-04-01', 'Samsung', 'SL-M3370FD', 'B', '2016-04-01', '2016-04-01', '', 'PR-16-003', '2.117', '', 'N', NULL, NULL, 'N'),
	(50, 24, '2층 내시경실', '2016-05-01', 'Samsung', 'SL-M2620', 'B', '2016-05-01', '2016-05-01', '', 'PR-16-005', '', '', 'N', NULL, NULL, 'N'),
	(51, 25, '진단검사실 혈액은행', '2016-07-01', 'Samsung', 'SL-M2620', 'B', '2016-07-01', '2016-07-01', '', 'PR-16-006', '', '혈액은행', 'N', NULL, NULL, 'N'),
	(52, 23, '2F 원무과', '2016-09-01', 'Samsung', 'SL-M3320ND', 'B', '2016-09-01', '2016-09-01', 'ZDG0BJFHA0002QA', 'PR-16-009', '2.047', '처방전 / 픽업롤러교체', 'N', NULL, NULL, 'N'),
	(53, 23, '2F 원무과', '2016-09-01', 'Samsung', 'SL-M3320ND', 'B', '2016-09-01', '2016-09-01', 'ZDG0BJFHA0002DF', 'PR-16-011', '2.048', '영수증', 'N', NULL, '', 'N'),
	(54, 23, '2F 원무과', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', 'ZDG0B8GH9A00ZA', 'PR-16-012', '2.049', '확인증', 'N', NULL, NULL, 'N'),
	(55, 21, '소아청소년과1 외래', '2016-11-01', 'Samsung', 'SL-M3320ND', 'B', '2016-11-01', '2016-11-01', 'ZDG0BJEHB00035B', 'PR-16-021', '3.234', '', 'N', NULL, NULL, 'N'),
	(56, 22, '치과', '2017-02-01', 'Samsung', 'SL-M2670FW', 'B', '2017-02-01', '2017-02-01', '', 'PR-17-002', '3.245', '', 'N', NULL, NULL, 'N'),
	(57, 16, '내과4 외래', '2017-05-01', 'Samsung', 'SL-M2620ND', 'B', '2017-05-01', '2017-05-01', 'ZD11B8GJ5A0014H', 'PR-17-004', '2.135', '', 'N', NULL, NULL, 'N'),
	(58, 15, '재활치료실', '2017-06-01', 'Brother', 'DCP-T300', 'C', '2017-06-01', '2017-06-01', '', 'PR-17-005', '', '', 'N', NULL, NULL, 'N'),
	(59, 25, '진단검사실 접수대', '2017-02-01', 'Samsung', 'SL-M2620', 'B', '2017-02-01', '2016-02-01', '', 'PR-17-008', '', '접수대', 'N', NULL, NULL, 'N'),
	(60, 20, '외과 외래', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-006', '', '', 'N', NULL, NULL, 'N'),
	(61, 23, '2F 원무과', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-007', '3.133', '제증명', 'N', NULL, NULL, 'N'),
	(62, 25, '진단검사실', '2020-02-01', 'HP', 'Office Jet Pro 8210', 'C', '2020-02-01', '2020-02-01', '', 'PR-20-002', '2.118', '', 'N', NULL, NULL, 'N'),
	(63, 16, '내과3 외래', '2021-09-01', 'HP', 'Office Jet Pro 8210', 'C', '2021-09-01', '2021-09-01', '', 'PR-21-009', '2.207', '', 'N', NULL, NULL, 'N'),
	(64, 17, '심장내과1 진료실', '2023-04-01', 'Samsung', 'C433W', 'C', '2023-04-01', '2024-04-01', '', 'PR-23-003', '', '', 'N', NULL, NULL, 'N'),
	(65, 16, '내과1 진료실', '2023-04-01', 'Samsung', 'C433W', 'C', '2023-04-01', '2024-04-01', '', 'PR-23-005', '', '', 'N', NULL, NULL, 'N'),
	(66, 26, '2층 영상의학과 차장실', '2024-05-14', 'Samsung', 'SL-M2843DW', 'B', '2024-03-01', '2024-05-14', '', 'PR-24-006', '', '', 'N', NULL, NULL, 'N'),
	(67, 27, '인공신장실', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-011', '', '', 'N', NULL, NULL, 'N'),
	(68, 28, '중앙공급실', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-012', '', '', 'N', NULL, NULL, 'N'),
	(69, 29, '심혈관조영실', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-005', '', '', 'N', NULL, NULL, 'N'),
	(70, 30, '중환자실', '2021-01-05', 'Samsung', 'SL-M2630ND', 'B', '2021-01-05', '0021-01-05', '', 'PR-21-001', '3.237', '', 'N', NULL, '', 'N'),
	(71, 31, '수술실', '2021-07-01', 'Samsung', 'SL-M2680FN', 'B', '2021-07-01', '2021-07-01', '', 'PR-21-007', '3.238', '', 'N', NULL, NULL, 'N'),
	(72, 30, '중환자실', '2023-12-01', 'HP', 'LaserJet Pro 4003dn', 'B', '2023-12-01', '2023-12-01', '', 'PR-23-015', '', 'EKG 연동 전용', 'N', NULL, NULL, 'N'),
	(73, 32, '5A병동', '2021-04-01', 'Samsung', 'SL-M2630ND', 'B', '2021-04-01', '2021-04-01', '', 'PR-21-002', '2.139', '', 'N', NULL, '', 'N'),
	(74, 33, '5B병동', '2021-04-01', 'Samsung', 'SL-M2630ND', 'B', '2021-04-01', '2021-04-01', '', 'PR-21-003', '2.079', '', 'N', NULL, '', 'N'),
	(75, 35, '6B병동', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-006', '', '', 'N', NULL, NULL, 'N'),
	(76, 34, '6A병동', '2024-03-01', 'Samsung', 'SL-M2843DW', 'B', '2023-07-01', '2024-03-01', '', 'PR-24-002', '', '', 'N', NULL, NULL, 'N'),
	(77, 36, '7A병동', '2020-12-01', 'Samsung', 'SL-M2630ND', 'B', '2020-12-01', '2020-12-01', 'ZKLSB8GNCB004NB', 'PR-20-004', '2.070', '', 'N', NULL, '', 'N'),
	(78, 37, '7B병동', '2024-08-01', 'Samsung', 'SL-M2630ND', 'B', '2024-08-01', '2024-08-01', 'ZKLSB8GX8A009MA', 'PR-20-009', '2.196', '', 'N', NULL, NULL, 'N'),
	(79, 44, '연구실06', '2011-02-01', 'Samsung', 'ML-2525K', 'B', '2011-02-01', '2011-02-01', '', 'PR-11-013', '', '', 'N', NULL, NULL, 'N'),
	(80, 47, '총무과', '2011-04-01', 'Samsung', 'SL-M3310ND', 'B', '2011-04-01', '2011-04-01', '', 'PR-11-015', '3.187', '', 'N', NULL, NULL, 'N'),
	(81, 41, '심사과', '2012-11-01', 'Samsung', 'SL-M3310ND', 'B', '2012-11-01', '2012-11-01', '', 'PR-12-002', '2.251', '', 'N', NULL, NULL, 'N'),
	(82, 42, '연구실03', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-007', '', '', 'N', NULL, NULL, 'N'),
	(83, 45, '연구실08', '2014-05-01', 'Samsung', 'SL-M2620', 'B', '2014-05-01', '2014-05-01', '', 'PR-14-008', '', '', 'N', NULL, NULL, 'N'),
	(84, 39, '교육실', '2016-03-01', 'Samsung', 'SL-M2670FN', 'B', '2016-03-01', '2016-03-01', '', 'PR-16-014', '3.147', '', 'N', NULL, NULL, 'N'),
	(85, 48, '행정부장실', '2016-04-01', 'HP', 'CP1025', 'C', '2016-04-01', '2016-04-01', '', 'PR-16-013', '', '', 'N', NULL, NULL, 'N'),
	(86, 40, '병원장실', '2016-04-01', 'HP', 'CP1025', 'C', '2016-04-01', '2016-04-01', '', 'PR-16-004', '', '', 'N', NULL, NULL, 'N'),
	(87, 46, '영양실', '2019-05-01', 'Samsung', 'SL-M3820ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-008', '3.175', '', 'N', NULL, NULL, 'N'),
	(88, 39, '교육실', '2021-04-01', 'HP', 'Office Jet Pro 8210', 'C', '2021-04-01', '2021-04-01', '', 'PR-21-004', '3.174', '', 'N', NULL, NULL, 'N'),
	(89, 38, '간호과장실', '2021-09-01', 'HP', 'Office Jet Pro 8210', 'C', '2021-01-01', '2021-09-01', '', 'PR-21-010', '3.087', '', 'N', NULL, NULL, 'N'),
	(90, 38, '간호과장실', '2021-09-01', 'Samsung', 'SL-M2680FN', 'B', '2021-08-01', '2021-09-01', '', 'PR-21-011', '2.088', '', 'N', NULL, NULL, 'N'),
	(91, 47, '총무과', '2024-03-01', 'Samsung', 'SL-J3520W', 'C', '2022-11-01', '2024-03-01', '', 'PR-24-003', '3.189', '금호월드', 'N', NULL, NULL, 'N'),
	(92, 41, '심사과', '2023-07-01', 'Samsung', 'SL-M2893FW', 'B', '2023-07-01', '2024-03-01', '', 'PR-24-001', '2.250', '', 'N', NULL, NULL, 'N'),
	(93, 49, '회계팀', '2019-06-01', 'Brother', 'DCP-T310', 'C', '2019-06-01', '2019-06-01', '', 'PR-19-010', '', '', 'N', NULL, NULL, 'N'),
	(94, 49, '회계팀', '2019-06-01', 'Brother', 'DCP-T310', 'C', '2019-06-01', '2019-06-01', '', 'PR-19-011', '', '', 'N', NULL, NULL, 'N'),
	(95, 43, '연구실04', '2025-04-15', 'Samsung', 'SL-M2630ND', 'B', '2025-04-15', '2025-04-15', '', 'PR-25-003', '', '', 'N', NULL, NULL, 'N'),
	(96, 52, '종검 작은사무실', '2011-02-01', 'Samsung', 'ML-3310ND', 'B', '2011-02-01', '2011-02-01', '', 'PR-11-001', '3.196', '', 'N', NULL, NULL, 'N'),
	(97, 52, '종검 큰사무실', '2011-02-01', 'Samsung', 'ML-4555N', 'B', '2011-02-11', '2011-02-11', '', 'PR-11-004', '3.198', '', 'N', NULL, NULL, 'N'),
	(98, 52, '종검 기초검사데스크', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-002', '', '', 'N', NULL, NULL, 'N'),
	(99, 50, '응급센터 영상의학과 MRI', '2011-03-01', 'Samsung', 'ML-2525K', 'B', '2011-03-01', '2011-03-01', '', 'PR-11-014', '', '', 'N', NULL, NULL, 'N'),
	(100, 52, '종검 청력검사데스크', '2011-04-01', 'Samsung', 'CF-650P', 'B', '2011-04-01', '2011-04-01', '', 'PR-11-016', '', 'Fax', 'N', NULL, '', 'N'),
	(101, 50, '응급센터 간호데스크', '2012-01-01', 'Samsung', 'SL-M2630ND', 'B', '2012-01-01', '2012-01-01', '', 'PR-12-001', '3.159', '', 'N', NULL, NULL, 'N'),
	(102, 52, '종검 큰사무실', '2014-07-01', 'Samsung', 'SL-M2670FN', 'B', '2014-07-01', '2014-07-01', '', 'PR-14-010', '3.199', '', 'N', NULL, '', 'N'),
	(103, 52, '종검 접수 1-2', '2016-11-01', 'Samsung', 'SL-M3320ND', 'B', '2016-11-01', '2016-11-01', '', 'PR-16-022', '3.205', '확인증', 'N', NULL, NULL, 'N'),
	(104, 52, '종검 접수 1-2', '2016-11-01', 'Samsung', 'SL-M3320ND', 'B', '2016-11-01', '2016-11-01', 'ZDG08BJEHB00063T', 'PR-16-023', '3.206', '제증명', 'N', NULL, NULL, 'N'),
	(105, 5, '응급실 원무과 당직실', '2019-04-01', 'Samsung', 'SL-M3320ND', 'B', '2019-04-01', '2019-04-01', '', 'PR-19-001', '3.141', '영수증', 'N', NULL, '', 'N'),
	(106, 5, '응급실 원무과 당직실', '2019-05-01', 'Samsung', 'SL-M3320ND', 'B', '2019-05-01', '2019-05-01', '', 'PR-19-009', '3.142', '처방전', 'N', NULL, NULL, 'N'),
	(107, 50, '응급실 간호데스크', '2020-07-01', 'HP', 'Office Jet Pro 8210', 'C', '2020-07-01', '2020-07-01', '', 'PR-20-003', '3.166', '', 'N', NULL, NULL, 'N'),
	(108, 52, '부인과 외래', '2020-12-01', 'Samsung', 'SL-M2630ND', 'B', '2020-12-01', '2020-12-01', '', 'PR-20-005', '3.192', '', 'N', NULL, NULL, 'N'),
	(109, 52, '종검 접수 1', '2021-05-01', 'Samsung', 'SL-M2680FN', 'B', '2021-05-01', '2020-05-01', '', 'PR-21-006', '3.200', 'Fax', 'N', NULL, NULL, 'N'),
	(110, 51, '관리과', '2021-07-01', 'Samsung', 'SL-M2680FN', 'B', '2021-07-01', '2021-07-01', '', 'PR-21-008', '2.183', '', 'N', NULL, NULL, 'N'),
	(111, 52, '종검 접수 3', '2022-08-01', 'Samsung', 'SL-M3820ND', 'B', '2022-08-01', '2022-08-01', '', 'PR-22-006', '3.193', '제증명', 'N', NULL, NULL, 'N'),
	(112, 52, '종검 내시경실', '2022-09-01', 'Samsung', 'SL-M3370FD', 'B', '2022-09-01', '2022-09-01', '', 'PR-22-007', '3.155', 'Scan', 'N', NULL, NULL, 'N'),
	(113, 52, '종검 작은사무실', '2022-09-01', 'Samsung', 'SL-M3820ND', 'B', '2022-09-01', '2022-09-01', '', 'PR-22-001', '', '', 'N', NULL, NULL, 'N'),
	(114, 5, '응급실 원무과 당직실', '2023-04-01', 'Samsung', 'SL-M2680FN', 'B', '2023-04-01', '2023-04-01', '', 'PR-23-002', '3.145', '제증명 및 Fax', 'N', NULL, NULL, 'N'),
	(115, 52, '종검 체성분 검사', '2023-06-01', 'Samsung', 'SL-M2843DW', 'B', '2023-06-01', '2023-06-01', '', 'PR-23-011', '', '', 'N', NULL, NULL, 'N'),
	(116, 50, '응급실 처치실', '2023-06-01', 'Samsung', 'SL-M2630ND', 'B', '2023-06-01', '2023-06-01', '', 'PR-23-010', '', '', 'N', NULL, NULL, 'N'),
	(117, 50, '응급실 의무기록실', '2024-01-01', 'Samsung', 'SL-M2893FW', 'B', '2024-01-01', '2024-01-01', '', 'PR-24-004', '3.152', '', 'N', NULL, NULL, 'N'),
	(118, 52, '종검 작은사무실', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', '', 'PR-16-002', '', '', 'N', NULL, NULL, 'N'),
	(119, 52, '종검 외부데스크', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', '', 'PR-16-001', '', '체크리스트', 'N', NULL, NULL, 'N'),
	(120, 52, '종검 접수 3-4', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', 'ZDG0BJHA00033P', 'PR-16-015', '3.203', '확인증', 'N', NULL, NULL, 'N'),
	(121, 52, '종검 접수 3-4', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', '', 'PR-16-016', '3.202', '영수증', 'N', NULL, NULL, 'N'),
	(122, 52, '종검 접수', '2016-10-01', 'Samsung', 'SL-M3320ND', 'B', '2016-10-01', '2016-10-01', '', 'PR-16-017', '3.204', '처방전전', 'N', NULL, NULL, 'N'),
	(123, 52, '종검 작은사무실', '2018-06-01', 'HP', 'Office Jet Pro 8210', 'C', '2018-06-01', '2018-06-01', '', 'PR-18-001', '3.197', '', 'N', NULL, NULL, 'N'),
	(124, 52, '종검 스트레스 검사실', '2021-03-01', 'Samsung', 'C433W', 'C', '2021-03-01', '2021-03-01', '', 'PR-23-008', '08HFB8GR3A00B4K', '', 'N', NULL, NULL, 'N'),
	(125, 52, '종검 작은사무실', '2024-09-03', 'HP', 'HP M501dn', 'B', '2024-09-03', '2024-09-03', 'J8H61A', 'PR-24-007', '3.195', '', 'N', NULL, NULL, 'N'),
	(126, 51, '관리과', '2024-10-01', 'HP', 'Office Jet Pro 8210', 'C', '2024-08-01', '2024-10-01', '', 'PR-24-008', '', '', 'N', NULL, NULL, 'N'),
	(127, 52, '내시경실', '2025-03-19', 'Samsung', 'SL-M2843WD', 'C', '2025-03-19', '2025-03-19', '', 'PR-25-002', '3.223', '', 'N', NULL, NULL, 'N');

-- 테이블 ks.printer_toner 구조 내보내기
CREATE TABLE IF NOT EXISTS `printer_toner` (
  `print_toner_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '프린트와 토너를 연결해주는 키값',
  `print_code` varchar(255) DEFAULT NULL,
  `toner_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`print_toner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.printer_toner:~31 rows (대략적) 내보내기
INSERT IGNORE INTO `printer_toner` (`print_toner_id`, `print_code`, `toner_name`) VALUES
	(32, 'SL-M2630ND', 'MLT-K250L'),
	(33, 'SL-M2843WD', 'MLT-K250L'),
	(34, 'SL-M2843DW', 'MLT-K250L'),
	(35, 'Office Jet Pro 8210', '무한잉크'),
	(36, 'HP M501dn', 'CF287A'),
	(37, 'LaserJet Pro 4003dn', 'W1510A(미구매)'),
	(38, 'SL-M3220ND', 'MLT-D405L'),
	(39, 'C433W', 'CLT-( )404S'),
	(40, 'DCP-T310', 'BT6000/BT5000'),
	(41, 'DCP-T300', 'BT6000/BT5000'),
	(42, 'SL-M3320ND', 'MLT-D203L'),
	(43, 'SL-M2620', 'MLT-D115L'),
	(44, 'SL-J3520W', '비어있음'),
	(45, 'SL-M2893FN', 'MLT-K250L'),
	(46, 'SL-M2893FW', 'MLT-K250L'),
	(47, 'SL-M2680FN', 'MLT-K250L'),
	(48, 'SL-M3370FD', 'MLT-D203L'),
	(49, 'SL-M3820ND', 'MLT-D203L/203E'),
	(50, 'SL-M4030ND', 'MLT-D201S'),
	(51, 'M607', 'CF237A'),
	(52, 'SL-M2670FN', 'MLT-D115L'),
	(53, 'SL-M2670FW', 'MLT-D115L'),
	(54, 'CP1025', 'CE31( )A'),
	(55, 'ML-2540R', 'MLT-D105L'),
	(56, 'SL-M3310ND', 'MLT-D205L'),
	(57, 'CF-650P', 'MLT-D105S'),
	(58, 'ML-2525K', 'MLT-D105L'),
	(59, 'SL-M3710ND', 'MLT-D205L'),
	(60, 'SCX-4833FR', 'MLT-D205L'),
	(61, 'ML-3310ND', 'MLT-D205L'),
	(62, 'ML-4555N', 'MLT-D308L'),
	(63, 'SL-M2620ND', 'MLT-D115L');

-- 테이블 ks.print_locate_history 구조 내보내기
CREATE TABLE IF NOT EXISTS `print_locate_history` (
  `print_locate_history_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '프린터 위치 변경 히스토리 키값',
  `printer_id` int(11) NOT NULL COMMENT '프린트 키값',
  `department_before_name` varchar(255) DEFAULT NULL,
  `department_new_name` varchar(255) DEFAULT NULL,
  `print_after_date` date DEFAULT NULL COMMENT '이동 날짜',
  `print_text` varchar(255) DEFAULT NULL,
  `print_repair` varchar(255) DEFAULT NULL,
  `print_repair_date` date DEFAULT NULL COMMENT '수리 날짜',
  `history_tag` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`print_locate_history_id`),
  KEY `FK_print_locate_history_printer` (`printer_id`),
  CONSTRAINT `FK_print_locate_history_printer` FOREIGN KEY (`printer_id`) REFERENCES `printer` (`printer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.print_locate_history:~8 rows (대략적) 내보내기
INSERT IGNORE INTO `print_locate_history` (`print_locate_history_id`, `printer_id`, `department_before_name`, `department_new_name`, `print_after_date`, `print_text`, `print_repair`, `print_repair_date`, `history_tag`, `del`) VALUES
	(1, 1, '입력테스트 2', '입력테스트', '2025-04-25', '부서 변경에 따른 이력 자동 생성', NULL, NULL, '부서 변경', 'N'),
	(2, 1, NULL, NULL, NULL, '', '수리 테스트', '2025-04-25', '수리', 'N'),
	(3, 1, NULL, NULL, NULL, '', '수리 테스트2', '2025-04-23', '수리', 'Y'),
	(4, 1, NULL, NULL, NULL, '', '수리 테스트3', '2025-04-26', '수리', 'N'),
	(5, 1, NULL, NULL, NULL, '', '수리 테스트4', '2025-04-30', '수리', 'N'),
	(6, 1, '입력테스트', '입력테스트 2', '2025-04-25', '부서 변경에 따른 이력 자동 생성', NULL, NULL, '부서 변경', 'Y'),
	(7, 105, '응급센터', '원무과', '2025-05-01', '부서 변경에 따른 이력 자동 생성', NULL, NULL, '부서 변경', 'N'),
	(8, 36, '약제과', '원무과', '2025-05-03', '부서 변경에 따른 이력 자동 생성', NULL, NULL, '부서 변경', 'N');

-- 테이블 ks.provider 구조 내보내기
CREATE TABLE IF NOT EXISTS `provider` (
  `provider_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '거래처 키값',
  `toner_id` int(11) NOT NULL COMMENT '토너 키값',
  `provider_name` varchar(20) NOT NULL COMMENT '거래처 이름',
  `provider_number` varchar(15) NOT NULL COMMENT '거래처 번호',
  `provider_people` varchar(10) DEFAULT NULL COMMENT '거래처 담당자',
  `provider_address` varchar(50) DEFAULT NULL COMMENT '거래처 주소',
  `provider_del` varchar(1) NOT NULL COMMENT 'Y or N / 삭제 여부',
  `del` varchar(1) DEFAULT NULL COMMENT '플래그 값 / Y or N',
  PRIMARY KEY (`provider_id`),
  KEY `FK_provider_toner` (`toner_id`),
  CONSTRAINT `FK_provider_toner` FOREIGN KEY (`toner_id`) REFERENCES `toner` (`toner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.provider:~0 rows (대략적) 내보내기

-- 테이블 ks.toner 구조 내보내기
CREATE TABLE IF NOT EXISTS `toner` (
  `toner_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '토너 키값',
  `toner_code` varchar(15) NOT NULL COMMENT '제품코드',
  `toner_name` varchar(20) NOT NULL COMMENT '제품명',
  `toner_text` varchar(20) NOT NULL COMMENT '비고',
  `toner_unit` varchar(10) NOT NULL COMMENT '단위',
  `toner_quantity` int(11) NOT NULL COMMENT '수량',
  `del` varchar(1) NOT NULL COMMENT '플래그 값 / Y or N',
  PRIMARY KEY (`toner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.toner:~21 rows (대략적) 내보내기
INSERT IGNORE INTO `toner` (`toner_id`, `toner_code`, `toner_name`, `toner_text`, `toner_unit`, `toner_quantity`, `del`) VALUES
	(1, '테스트 코드', '테스트 제품명', '테스트 비고', '테스트 단위', 20, 'Y'),
	(2, 'KS-TS02', 'MLT-D105L', '삼성프린터', 'EA', 5, 'N'),
	(3, 'KS-TS03', 'MLT-D115L', '삼성프린터', 'EA', 4, 'N'),
	(4, 'KS-TS04', 'MLT-D201S', '삼성프린터', 'EA', 2, 'N'),
	(5, 'KS-TS05', 'MLT-D203L', '삼성프린터', 'EA', 6, 'N'),
	(6, 'KS-TS06', 'MLT-D203E', '삼성프린터', 'EA', 4, 'N'),
	(7, 'KS-TS07', 'MLT-D205L', '삼성프린터', 'EA', 3, 'N'),
	(8, 'KS-TS08', 'MLT-D250L', '삼성프린터', 'EA', 13, 'N'),
	(9, 'KS-TS010', 'MLT-D405L', '삼성프린터', 'EA', 3, 'N'),
	(10, 'KS-TH02', 'CF287A', 'HP', 'EA', 3, 'N'),
	(11, 'KS-THC05', 'CE310A(BK)', 'HP', 'EA', 2, 'N'),
	(12, 'KS-THC06', 'CE311A(C)', 'HP', 'EA', 3, 'N'),
	(13, 'KS-THC07', 'CE312A(Y)', 'HP', 'EA', 3, 'N'),
	(14, 'KS-THC08', 'CE312A(M)', 'HP', 'EA', 4, 'N'),
	(15, 'KS-TSC01', 'CLT-K404S', '삼성프린터', 'EA', 2, 'N'),
	(16, 'KS-TSC02', 'CLT-Y404S', '삼성프린터', 'EA', 3, 'N'),
	(17, 'KS-TSC03', 'CLT-C404S', '삼성프린터', 'EA', 3, 'N'),
	(18, 'KS-TSC04', 'CLT-M404S', '삼성프린터', 'EA', 2, 'N'),
	(19, 'KS-TSC05', 'CLT-K405S', '삼성프린터', 'EA', 5, 'N'),
	(20, 'KS-TSC06', 'CLT-Y405S', '삼성프린터', 'EA', 2, 'N'),
	(21, 'KS-TSC07', 'CLT-C405S', '삼성프린터', 'EA', 4, 'N'),
	(22, 'KS-TSC08', 'CLT-M405S', '삼성프린터', 'EA', 3, 'N'),
	(23, 'KS-TH03', 'CF237A', 'HP', 'EA', 3, 'N');

-- 테이블 ks.toner_history 구조 내보내기
CREATE TABLE IF NOT EXISTS `toner_history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '토너 히스토리 키값',
  `toner_id` int(11) NOT NULL COMMENT '키값',
  `department_name` varchar(255) NOT NULL,
  `history_date` date NOT NULL COMMENT '일자',
  `history_received` int(11) DEFAULT NULL COMMENT '입고수량',
  `history_delivery` int(11) DEFAULT NULL COMMENT '출고수량',
  `history_text` varchar(50) DEFAULT NULL COMMENT '메모',
  `del` varchar(1) DEFAULT NULL COMMENT '플래그 값 / Y or N',
  PRIMARY KEY (`history_id`),
  KEY `FK_toner_history_toner` (`toner_id`),
  CONSTRAINT `FK_toner_history_toner` FOREIGN KEY (`toner_id`) REFERENCES `toner` (`toner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.toner_history:~21 rows (대략적) 내보내기
INSERT IGNORE INTO `toner_history` (`history_id`, `toner_id`, `department_name`, `history_date`, `history_received`, `history_delivery`, `history_text`, `del`) VALUES
	(1, 1, '입력테스트', '2025-04-29', 0, 1, '테스트', 'Y'),
	(2, 1, '입력테스트', '2025-04-29', 0, 1, '테스트2', 'Y'),
	(3, 1, '입력테스트', '2025-04-29', 1, 0, '테스트 입고', 'Y'),
	(11, 2, '영상의학과', '2025-05-02', 1, 1, '테스트', 'Y'),
	(12, 3, '신경과', '2025-05-02', 0, 1, '', 'N'),
	(13, 10, '종합검진', '2025-05-03', 0, 1, '종검 작은사무실', 'N'),
	(14, 8, '7A병동', '2025-05-07', 0, 1, '7A 병동', 'N'),
	(15, 8, '원무과', '2025-05-06', 0, 1, '원무과 응급실', 'N'),
	(16, 8, '5A병동', '2025-05-06', 0, 1, '5A 병동', 'N'),
	(17, 8, '5A병동', '2025-05-06', 0, 1, '5A 불량 교체 (가루 누수 70%)', 'N'),
	(18, 8, '5B병동', '2025-05-06', 0, 2, '5B 병동(교체 후 버림)', 'N'),
	(19, 9, '원무과', '2025-05-06', 0, 1, '원무과 퇴원계', 'N'),
	(20, 8, '종합검진', '2025-05-07', 0, 1, '종검 접수', 'N'),
	(21, 5, '약제과', '2025-05-07', 0, 1, '1F 약제과', 'N'),
	(22, 8, '6A병동', '2025-05-07', 0, 1, '6A 병동', 'N'),
	(23, 8, '원무과', '2025-05-07', 0, 1, '원무과 사무실', 'N'),
	(24, 8, '종합검진', '2025-05-07', 0, 1, '내시경실', 'N'),
	(25, 3, '원무과', '2025-05-08', 0, 1, '1F 원무과', 'N'),
	(26, 8, '심사과', '2025-05-08', 0, 1, '8F 심사과', 'N'),
	(27, 3, '교육실', '2025-05-08', 0, 1, '8F 교육실', 'N'),
	(28, 5, '원무과', '2025-05-08', 0, 1, '1F 원무과', 'N');

-- 테이블 ks.toner_month 구조 내보내기
CREATE TABLE IF NOT EXISTS `toner_month` (
  `toner_month_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '월마감 아이디',
  `toner_name` varchar(20) NOT NULL COMMENT '토너 이름',
  `toner_month_date` date NOT NULL COMMENT '월마감 날짜',
  `toner_previous_month` int(11) NOT NULL COMMENT '전월 재고',
  `toner_current_month` int(11) NOT NULL COMMENT '현재고',
  `toner_month_received` int(11) NOT NULL COMMENT '입고수량',
  `toner_month_delivery` int(11) NOT NULL COMMENT '출고수량',
  PRIMARY KEY (`toner_month_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.toner_month:~44 rows (대략적) 내보내기
INSERT IGNORE INTO `toner_month` (`toner_month_id`, `toner_name`, `toner_month_date`, `toner_previous_month`, `toner_current_month`, `toner_month_received`, `toner_month_delivery`) VALUES
	(1, 'MLT-D105L', '2025-04-30', 3, 3, 0, 0),
	(2, 'MLT-D115L', '2025-04-30', 4, 4, 0, 0),
	(3, 'MLT-D201S', '2025-04-30', 1, 1, 0, 0),
	(4, 'MLT-D203L', '2025-04-30', 6, 6, 0, 0),
	(5, 'MLT-D203E', '2025-04-30', 4, 4, 0, 0),
	(6, 'MLT-D205L', '2025-04-30', 3, 3, 0, 0),
	(7, 'MLT-D250L', '2025-04-30', 13, 13, 0, 0),
	(8, 'MLT-D405L', '2025-04-30', 3, 3, 0, 0),
	(9, 'CF287A', '2025-04-30', 3, 3, 0, 0),
	(10, 'CE310A(BK)', '2025-04-30', 2, 2, 0, 0),
	(11, 'CE311A(C)', '2025-04-30', 3, 3, 0, 0),
	(12, 'CE312A(Y)', '2025-04-30', 3, 3, 0, 0),
	(13, 'CE312A(M)', '2025-04-30', 4, 4, 0, 0),
	(14, 'CLT-K404S', '2025-04-30', 2, 2, 0, 0),
	(15, 'CLT-Y404S', '2025-04-30', 3, 3, 0, 0),
	(16, 'CLT-C404S', '2025-04-30', 3, 3, 0, 0),
	(17, 'CLT-M404S', '2025-04-30', 2, 2, 0, 0),
	(18, 'CLT-K405S', '2025-04-30', 5, 5, 0, 0),
	(19, 'CLT-Y405S', '2025-04-30', 2, 2, 0, 0),
	(20, 'CLT-C405S', '2025-04-30', 4, 4, 0, 0),
	(21, 'CLT-M405S', '2025-04-30', 3, 3, 0, 0),
	(22, 'CF237A', '2025-04-30', 3, 3, 0, 0),
	(23, 'MLT-D105L', '2025-05-01', 3, 3, 0, 0),
	(24, 'MLT-D115L', '2025-05-01', 4, 1, 0, 3),
	(25, 'MLT-D201S', '2025-05-01', 1, 1, 0, 0),
	(26, 'MLT-D203L', '2025-05-01', 6, 4, 0, 2),
	(27, 'MLT-D203E', '2025-05-01', 4, 4, 0, 0),
	(28, 'MLT-D205L', '2025-05-01', 3, 3, 0, 0),
	(29, 'MLT-D250L', '2025-05-01', 13, 2, 0, 11),
	(30, 'MLT-D405L', '2025-05-01', 3, 2, 0, 1),
	(31, 'CF287A', '2025-05-01', 3, 2, 0, 1),
	(32, 'CE310A(BK)', '2025-05-01', 2, 2, 0, 0),
	(33, 'CE311A(C)', '2025-05-01', 3, 3, 0, 0),
	(34, 'CE312A(Y)', '2025-05-01', 3, 3, 0, 0),
	(35, 'CE312A(M)', '2025-05-01', 4, 4, 0, 0),
	(36, 'CLT-K404S', '2025-05-01', 2, 2, 0, 0),
	(37, 'CLT-Y404S', '2025-05-01', 3, 3, 0, 0),
	(38, 'CLT-C404S', '2025-05-01', 3, 3, 0, 0),
	(39, 'CLT-M404S', '2025-05-01', 2, 2, 0, 0),
	(40, 'CLT-K405S', '2025-05-01', 5, 5, 0, 0),
	(41, 'CLT-Y405S', '2025-05-01', 2, 2, 0, 0),
	(42, 'CLT-C405S', '2025-05-01', 4, 4, 0, 0),
	(43, 'CLT-M405S', '2025-05-01', 3, 3, 0, 0),
	(44, 'CF237A', '2025-05-01', 3, 3, 0, 0);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
