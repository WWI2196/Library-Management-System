-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2025 at 06:12 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `ID` int(11) NOT NULL,
  `BookNo` varchar(10) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Author` varchar(40) NOT NULL,
  `Available` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`ID`, `BookNo`, `Title`, `Author`, `Available`) VALUES
(1, 'N123', 'Madol Duva', 'Matin Wickramasinghe', 0),
(2, 'N222', 'Oliver Twist', 'Charles Dickens', 0),
(3, 'T654', 'Java Swing', 'Robert Eckstein', 0);

-- --------------------------------------------------------

--
-- Table structure for table `lending`
--

CREATE TABLE `lending` (
  `ID` int(11) NOT NULL,
  `BookID` int(11) NOT NULL,
  `MemberID` int(11) NOT NULL,
  `ReturnDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `ID` int(11) NOT NULL,
  `MemberNo` varchar(10) NOT NULL,
  `FirstName` varchar(32) NOT NULL,
  `LastName` varchar(32) NOT NULL,
  `PhoneNo` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`ID`, `MemberNo`, `FirstName`, `LastName`, `PhoneNo`) VALUES
(1, 'S1111', 'Saman', 'Perera', NULL),
(2, 'S2222', 'Mohomad', 'Reeza', NULL),
(3, 'E3333', 'David', 'Smith', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `lending`
--
ALTER TABLE `lending`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `BookID` (`BookID`),
  ADD KEY `MemberID` (`MemberID`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `lending`
--
ALTER TABLE `lending`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `lending`
--
ALTER TABLE `lending`
  ADD CONSTRAINT `lending_ibfk_1` FOREIGN KEY (`BookID`) REFERENCES `books` (`ID`),
  ADD CONSTRAINT `lending_ibfk_2` FOREIGN KEY (`MemberID`) REFERENCES `members` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
