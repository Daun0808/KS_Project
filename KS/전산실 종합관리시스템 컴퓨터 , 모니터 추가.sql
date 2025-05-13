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

-- 테이블 ks.computer 구조 내보내기
CREATE TABLE IF NOT EXISTS `computer` (
  `computer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '컴퓨터 키값',
  `department_id` int(11) NOT NULL COMMENT '부서 키값',
  `computer_place` varchar(30) NOT NULL COMMENT '배치장소',
  `computer_os` varchar(30) DEFAULT NULL COMMENT 'os',
  `computer_ip` varchar(30) DEFAULT NULL COMMENT 'ip',
  `computer_hwp` varchar(30) DEFAULT NULL COMMENT '한글',
  `computer_exel` varchar(30) DEFAULT NULL COMMENT '엑셀',
  `computer_office` varchar(30) DEFAULT NULL COMMENT '오피스',
  `computer_s1` varchar(20) DEFAULT NULL COMMENT '에스원',
  `computer_alyac` varchar(20) DEFAULT NULL COMMENT '알약',
  `computer_use` varchar(10) DEFAULT NULL COMMENT '용도',
  `computer_model` varchar(40) DEFAULT NULL COMMENT '모델',
  `computer_product_date` date DEFAULT NULL COMMENT '생산년도',
  `computer_chipset` varchar(30) DEFAULT NULL COMMENT '칩셋',
  `computer_cpu` varchar(50) DEFAULT NULL COMMENT 'cpu',
  `computer_memory` varchar(20) DEFAULT NULL COMMENT '메모리',
  `computer_manufacturer` varchar(30) DEFAULT NULL COMMENT '제조회사',
  `computer_sale_date` date DEFAULT NULL COMMENT '구입연도',
  `computer_unique` varchar(30) DEFAULT NULL COMMENT '원내고유번호',
  `computer_text` varchar(50) DEFAULT NULL COMMENT '비고',
  `computer_del` varchar(1) DEFAULT NULL COMMENT '폐기 여부 ( Y/N)',
  `computer_del_text` varchar(50) DEFAULT NULL COMMENT '폐기 사유',
  `del` varchar(1) NOT NULL COMMENT '삭제 플래그 값 / Y or N',
  PRIMARY KEY (`computer_id`),
  KEY `fk_computer_department` (`department_id`),
  CONSTRAINT `fk_computer_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.computer:~4 rows (대략적) 내보내기
INSERT IGNORE INTO `computer` (`computer_id`, `department_id`, `computer_place`, `computer_os`, `computer_ip`, `computer_hwp`, `computer_exel`, `computer_office`, `computer_s1`, `computer_alyac`, `computer_use`, `computer_model`, `computer_product_date`, `computer_chipset`, `computer_cpu`, `computer_memory`, `computer_manufacturer`, `computer_sale_date`, `computer_unique`, `computer_text`, `computer_del`, `computer_del_text`, `del`) VALUES
	(1, 18, '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', 'N', '', 'Y'),
	(2, 18, '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', 'N', '', 'Y'),
	(3, 18, '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', 'N', '', 'Y'),
	(4, 18, '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', '테스트', '테스트', '2025-05-12', '테스트', '테스트', 'N', '', 'Y');

-- 테이블 ks.monitor 구조 내보내기
CREATE TABLE IF NOT EXISTS `monitor` (
  `monitor_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) NOT NULL COMMENT '부서 키값',
  `monitor_place` varchar(30) NOT NULL COMMENT '배치장소',
  `monitor_unique` varchar(30) DEFAULT NULL COMMENT '원내고유번호',
  `monitor_manufacturer` varchar(30) DEFAULT NULL COMMENT '제조회사',
  `monitor_sale_date` date DEFAULT NULL COMMENT '구입년도',
  `monitor_size` varchar(10) DEFAULT NULL COMMENT '사이즈',
  `monitor_text` varchar(30) DEFAULT NULL COMMENT '메모',
  `monitor_del` varchar(1) DEFAULT NULL COMMENT '폐기 여부 (Y/N)',
  `monitor_del_text` varchar(50) DEFAULT NULL COMMENT '폐기 사유',
  `del` varchar(1) NOT NULL COMMENT '삭제 플래그 값 / Y or N',
  PRIMARY KEY (`monitor_id`),
  KEY `fk_monitor_department` (`department_id`),
  CONSTRAINT `fk_monitor_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 테이블 데이터 ks.monitor:~0 rows (대략적) 내보내기
INSERT IGNORE INTO `monitor` (`monitor_id`, `department_id`, `monitor_place`, `monitor_unique`, `monitor_manufacturer`, `monitor_sale_date`, `monitor_size`, `monitor_text`, `monitor_del`, `monitor_del_text`, `del`) VALUES
	(1, 10, '테스트', '테스트', '테스트', '2025-05-13', '테스트', '테스트', 'N', '', 'Y');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
