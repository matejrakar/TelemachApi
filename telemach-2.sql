-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 30, 2021 at 09:33 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `telemach-2`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `post` varchar(255) DEFAULT NULL,
  `post_no` int(11) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_no` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `city`, `post`, `post_no`, `street`, `street_no`) VALUES
(0, 'Ljubljana', 'Ljubljana', 1000, 'Gregorčičeva ulica', 1),
(2, 'Grosuplje', 'Grosuplje', 1290, 'Tovarniška ulica', 123),
(3, 'Ivančna Gorica', 'Ivančna Gorica', 1295, 'Pavletova ulica', 9);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(48);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `address_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id`, `comment`, `service`, `value`, `address_id`) VALUES
(75, 'Stranka redno plačuje, z nami že 10 let', 'Telefon', 'true', 0),
(76, 'Televizije kmalu ne bodo potrebovali več.', 'Televizija', 'true', 0),
(77, 'Interneta nimajo, ker ga ne znajo uporabljati.', 'Internet', 'false', 0),
(78, 'Telefona nimajo, ker so napredni.', 'Telefon', 'false', 2),
(79, 'Televizije nimajo zaradi osebnih prepričanj.', 'Televizija', 'false', 2),
(80, 'Imajo najhitrejši možen internet na svetu.', 'Internet', 'true', 2),
(81, 'Imajo premium telefonsko storitev.', 'Telefon', 'true', 3),
(82, 'HD Televizija 24/7.', 'Televizija', 'true', 3),
(83, 'Internet s svetlobno hitrostjo 1,5 milijona Gbps.', 'Internet', 'true', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4qrnpkptesumeg08s77vx11v9` (`address_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `FK4qrnpkptesumeg08s77vx11v9` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
