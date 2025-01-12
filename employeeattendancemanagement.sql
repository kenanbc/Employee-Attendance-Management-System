-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 12, 2025 at 11:39 AM
-- Server version: 8.0.36
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `employeeattendancemanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
CREATE TABLE IF NOT EXISTS `activity` (
  `activityID` int NOT NULL AUTO_INCREMENT,
  `employeeID` int NOT NULL,
  `date` varchar(20) NOT NULL,
  `login` time NOT NULL,
  `logout` time DEFAULT NULL,
  `totalWork` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`activityID`),
  KEY `emp_act` (`employeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`activityID`, `employeeID`, `date`, `login`, `logout`, `totalWork`) VALUES
(2, 5, '10.01.2025', '08:02:00', '20:03:46', '12:1'),
(3, 8, '10.01.2025', '08:01:47', '20:06:07', '12:4'),
(4, 1, '10.01.2025', '07:53:58', '20:07:23', '12:13'),
(5, 9, '10.01.2025', '10:07:49', '20:08:09', '10:0'),
(6, 10, '10.01.2025', '08:02:18', '20:08:33', '12:6'),
(7, 4, '10.01.2025', '07:09:36', '20:09:55', '13:0'),
(8, 1, '10.01.2025', '20:10:42', '20:11:45', '0:1'),
(9, 8, '10.01.2025', '20:11:59', '20:12:24', '0:0'),
(10, 1, '10.01.2025', '20:12:25', '20:12:50', '0:0'),
(11, 4, '10.01.2025', '20:12:58', '20:13:18', '0:0'),
(12, 1, '11.01.2025', '19:06:31', '19:06:52', '0:0'),
(13, 4, '11.01.2025', '19:06:57', '19:07:04', '0:0'),
(14, 1, '11.01.2025', '19:08:51', '19:09:05', '0:0'),
(15, 1, '11.01.2025', '19:10:03', '19:10:15', '0:0'),
(16, 4, '11.01.2025', '19:10:18', '19:10:23', '0:0'),
(17, 1, '11.01.2025', '19:19:01', '19:19:10', '0:0'),
(18, 1, '11.01.2025', '19:35:11', '19:35:29', '0:0'),
(19, 1, '11.01.2025', '19:37:07', '19:37:30', '0:0'),
(20, 1, '11.01.2025', '19:38:38', '19:39:20', '0:0'),
(21, 4, '11.01.2025', '19:39:24', '19:40:06', '0:0'),
(22, 5, '11.01.2025', '20:06:29', '20:34:40', '0:28'),
(23, 1, '11.01.2025', '20:34:41', '20:43:32', '0:8'),
(24, 4, '11.01.2025', '20:43:37', '21:10:59', '0:27'),
(25, 1, '11.01.2025', '21:11:01', '21:11:12', '0:0'),
(26, 1, '11.01.2025', '21:12:26', '21:12:45', '0:0'),
(27, 1, '11.01.2025', '21:13:37', '21:13:53', '0:0'),
(28, 1, '11.01.2025', '21:22:15', '21:22:17', '0:0'),
(29, 5, '11.01.2025', '21:22:24', '21:32:59', '0:10'),
(30, 1, '11.01.2025', '21:33:02', '21:33:20', '0:0'),
(31, 1, '11.01.2025', '21:33:43', '21:35:00', '0:1'),
(32, 1, '11.01.2025', '22:28:08', '22:28:37', '0:0'),
(33, 1, '11.01.2025', '22:41:49', '22:42:28', '0:0'),
(34, 4, '11.01.2025', '22:43:19', '22:43:42', '0:0'),
(35, 1, '11.01.2025', '22:45:48', '22:46:00', '0:0'),
(36, 4, '11.01.2025', '22:46:04', '22:46:52', '0:0'),
(37, 4, '11.01.2025', '22:48:11', '22:48:51', '0:0'),
(38, 4, '11.01.2025', '22:50:04', '22:50:20', '0:0'),
(39, 4, '11.01.2025', '22:50:35', '22:50:54', '0:0');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `employeeID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `role` varchar(20) NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`employeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employeeID`, `firstName`, `lastName`, `email`, `phoneNumber`, `role`, `username`, `password`) VALUES
(1, 'Ane', 'Kane', 'anekane@gmail.com', '5235262', 'Manager', 'a', '$2a$10$lngUF0eYMKsTSLU3GKfO4OGdBTPEOgvI65htsqQQvHHghSH1Ui8Yu'),
(2, 'Mubina', 'Osmic', 'mubinamemisevic@gmail.com', '560232464', 'Employee', 'mubinaosm', '$2a$12$qYP5D0z6h96e43GOR9fznOAWutvMRq/7Tl.8mb0hkyrQAXkBHOP26'),
(3, 'Rukib', 'Hatunic', 'rukibhatunic@gmail.com', '254636392', 'Employee', 'rukibhat', '$2a$12$4bUUrtf4OSIA9gMgWkyu5uQbzvvevKy5kBbRyoQOMU38RHEYuNT3G'),
(4, 'Amer', 'Hadzic', 'amerhadzic@gmail.com', '15125252', 'Employee', 'amerhadzic', '$2a$10$GqoccNnzHyPvg6WjCm7k8uwUCalQ45nCuTpT5E22wWwJrCDYmYP0W'),
(5, 'Sedin', 'Mujkic', 'sedinmujkic@gmail.com', '535827759', 'Superadmin', 'admin', '$2a$12$hJ.tjOmAIwLlyrWyJuZJzOvs7jIJ8xoxkOSFLWAxzm/SeD.Kgv1pq'),
(6, 'Christopher', 'Gradon', 'chrisb@gmail.com', '6315423323', 'Manager', 'chrisB', '$2a$10$wdWnToQRT7pkvp6B2V43duD9LcievUzWkQ660TxcGDDf/zp3.nD6C'),
(7, 'Jane', 'Smith', 'janesmh@gmail.com', '1593153', 'Employee', 'janesmh', '$2a$10$4yZmSjYJusDaxS30yPsqYe.I/jVxZ.KUAOjXDzF2PlynC3qa77IB6'),
(8, 'Bob', 'Davis', 'bobdav@outlook.com', '86230623', 'Employee', 'bobdvs', '$2a$10$S13agaW/drhHPmiea4roPOg55yTVyrl.vQNlga8B5o.NUk.4tyjDy'),
(9, 'Emily', 'White', 'wmily@hotmail.com', '637341235', 'Manager', 'emilye', '$2a$10$A1KppVLKgU0qSuxGeYL/..5P2CEh.h6atxEoWr8UHopIgdMK90ZRW'),
(10, 'Haris', 'Beslic', 'harisbsc@yahoo.com', '892512414', 'Employee', 'harisbe', '$2a$10$0YPQfRt6LXNpcJLuva3aNOnFzOBkbc8Z4X1Nmxq2LXVC4s9z80EYC'),
(11, 'Sara', 'Fazlic', 'saraf@gmail.com', '67915327326', 'Employee', 'saraf', '$2a$10$wEKun6VbK8NOqna3Ddzld.zQ0i5F2..Kr23NOdyuwcx0G4Jppyekm'),
(12, 'Isabella', 'Taylor', 'bella@gmail.com', '7843546345', 'Employee', 'bella', '$2a$10$ktYfy9ak4XanfOo0D2ykwO4P/Ct2BEda0jL/Py/h/7GKWjYp3B/qy'),
(13, 'Max', 'Leclerc', 'maxi@gmail.com', '768542353', 'Manager', 'maxmax', '$2a$10$5yzIXO26w46ZFpApzQMIKesE2DZp5XEjln.bs17qp.pKgRreU31Cm'),
(14, 'Toby', 'Brooks', 'tobby@gmail.com', '6276323', 'Employee', 'tobby', '$2a$10$DIJeWE7acUI9M/9eIpcTU.3ilwlM3o8MO28GjgsIZMXccaG2Khpbm'),
(15, 'Esmir', 'Susic', 'esma@gmail.com', '28465645', 'Employee', 'esmir', '$2a$10$9LS9vhPTmXwtGpuR3qVhUuC/u93c/RG.XOnVHUFwKlrv7/cKVYTNm'),
(16, 'Esma', 'Tokic', 'esmat@gmail.com', '8423544435', 'Employee', 'esmaT', '$2a$10$yWCtZERvYpbuylcGBgUvHu/vnFqh/KpwX8VDJCunmCzU955SU4FiS'),
(17, 'Liam', 'Lawson', 'liam@gmail.com', '4634351534', 'Employee', 'lialaw', '$2a$10$6nGYia5zR4zG5cZT693tP.c85y1/OO9qNwgcihVLUphkOmWOTC2s2'),
(18, 'Tin', 'Markovic', 'tinm@gmail.com', '75685235', 'Employee', 'mint', '$2a$10$Wu2qxth03XVR6YCdR/G3J.eC64a6QKwZRV/fvbGOCa9VJzaZMUH0q'),
(19, 'Rusmir', 'Alic', 'rusma@gmail.com', '26476374574', 'Employee', 'rusma', '$2a$10$6dPYQYSZw1WdVTKeTg4Cv.XPspdKbS/yahTrXjgM3qqKii61BAxIO'),
(20, 'John', 'Doe', 'johny@gmail.com', '513533526', 'Employee', 'johnd', '$2a$10$u5lxi3KCZMD.7nNuf6GrzOiHcebO6XSBvTshUjiqFydp.NVq7Xl2a');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
CREATE TABLE IF NOT EXISTS `request` (
  `requestID` int NOT NULL AUTO_INCREMENT,
  `employeeID` int NOT NULL,
  `startDate` varchar(10) NOT NULL,
  `endDate` varchar(10) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Waiting',
  PRIMARY KEY (`requestID`),
  KEY `emp_req` (`employeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`requestID`, `employeeID`, `startDate`, `endDate`, `description`, `status`) VALUES
(1, 4, '8.1.2025', '14.1.2025', 'I need this!!', 'Approved'),
(2, 2, '4.1.2025', '8.1.2025', NULL, 'Approved'),
(3, 4, '1.1.2025', '12.1.2025', NULL, 'Denied'),
(4, 4, '7.1.2025', '11.1.2025', NULL, 'Waiting'),
(5, 8, '13.1.2025', '17.1.2025', NULL, 'Waiting');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `emp_act` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `emp_req` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
