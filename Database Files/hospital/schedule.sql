-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.28 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table hospital.schedule
CREATE TABLE IF NOT EXISTS `schedule` (
  `idpatient` varchar(45) NOT NULL,
  `idemployee` varchar(45) NOT NULL,
  `time` datetime NOT NULL,
  `doctor_present` tinyint NOT NULL DEFAULT '1',
  `patient_present` tinyint NOT NULL DEFAULT '0',
  `diagnosis` longtext,
  `treatment` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table hospital.schedule: ~2 rows (approximately)
INSERT INTO `schedule` (`idpatient`, `idemployee`, `time`, `doctor_present`, `patient_present`, `diagnosis`, `treatment`) VALUES
	('537a8c3d', 'f72a8b29', '2022-04-30 15:30:00', 1, 0, NULL, NULL),
	('537a8c3d', 'f72a8b29', '2022-04-30 15:00:00', 1, 0, NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
