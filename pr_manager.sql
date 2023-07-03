-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2023 at 06:56 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pr_manager`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_id` varchar(36) NOT NULL,
  `item_category` varchar(50) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_description` varchar(200) DEFAULT NULL,
  `item_quantity` double NOT NULL,
  `quantity_unit` varchar(10) NOT NULL,
  `item_status` varchar(50) NOT NULL,
  `purchase_request_id` varchar(36) NOT NULL,
  `price_quotation_report_id` varchar(36) DEFAULT NULL,
  `purchase_order_id` varchar(36) DEFAULT NULL,
  `grn` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_id`, `item_category`, `item_name`, `item_description`, `item_quantity`, `quantity_unit`, `item_status`, `purchase_request_id`, `price_quotation_report_id`, `purchase_order_id`, `grn`) VALUES
('4540a4e9-a732-488b-ba81-355635861a61', 'SAFETY', 'fsf', 'sdf', 3, 'pcs', 'PROCESSING', 'PR004', NULL, NULL, NULL),
('6bc515cd-40a1-4a28-ac91-0fb99c601b2f', 'STATIONARY', 'FIle covers', 'color : black', 10, 'pcs', 'DELIVERED', 'PR001', 'PQR001', 'PO001', ''),
('8597524c-87b4-4b21-89c6-c3ee58f987e5', 'SAFETY', 'fsaf', 'sdfasdf', 32, 'pcs', 'PROCESSING', 'PR002', NULL, NULL, NULL),
('96b91d3b-75fc-4e5a-858f-1fa93cecc96f', 'STATIONARY', 'adsfasdf', 'adsfadssdc', 200, 'pcs', 'PENDING_APPROVAL', 'PR003', NULL, NULL, NULL),
('a8c6c3c6-c040-4acd-9822-003638bac408', 'STATIONARY', 'CR book', '--', 10, 'pcs', 'DELIVERED', 'PR001', 'PQR001', 'PO001', '123');

-- --------------------------------------------------------

--
-- Table structure for table `price_quotation`
--

CREATE TABLE `price_quotation` (
  `quotation_id` varchar(36) NOT NULL,
  `supplier_name` varchar(200) NOT NULL,
  `supplier_address` varchar(300) NOT NULL,
  `approved` tinyint(1) NOT NULL,
  `price_quotation_report_id` varchar(36) NOT NULL,
  `quoted_total` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `price_quotation`
--

INSERT INTO `price_quotation` (`quotation_id`, `supplier_name`, `supplier_address`, `approved`, `price_quotation_report_id`, `quoted_total`) VALUES
('e27114f8-1192-423e-91a6-a88e5700959b', 'DEF Stationary', 'Negombo', 1, 'PQR001', 1205),
('f2a9fbd3-4a01-4908-afea-f96ff55d3006', 'ABC stationary', 'Naiwala, Gampaha', 0, 'PQR001', 1305);

-- --------------------------------------------------------

--
-- Table structure for table `price_quotation_report`
--

CREATE TABLE `price_quotation_report` (
  `report_id` varchar(36) NOT NULL,
  `created_date` date NOT NULL,
  `quotation_report_status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `price_quotation_report`
--

INSERT INTO `price_quotation_report` (`report_id`, `created_date`, `quotation_report_status`) VALUES
('PQR001', '2023-07-03', 'APPROVED');

-- --------------------------------------------------------

--
-- Table structure for table `price_quotation__quoted_price`
--

CREATE TABLE `price_quotation__quoted_price` (
  `id` int(11) NOT NULL,
  `quotation_id` varchar(36) NOT NULL,
  `price_quotation_report_id` varchar(36) NOT NULL,
  `item_id` varchar(36) NOT NULL,
  `quoted_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `price_quotation__quoted_price`
--

INSERT INTO `price_quotation__quoted_price` (`id`, `quotation_id`, `price_quotation_report_id`, `item_id`, `quoted_price`) VALUES
(1, 'f2a9fbd3-4a01-4908-afea-f96ff55d3006', 'PQR001', '6bc515cd-40a1-4a28-ac91-0fb99c601b2f', 10),
(2, 'f2a9fbd3-4a01-4908-afea-f96ff55d3006', 'PQR001', 'a8c6c3c6-c040-4acd-9822-003638bac408', 120.5),
(3, 'e27114f8-1192-423e-91a6-a88e5700959b', 'PQR001', '6bc515cd-40a1-4a28-ac91-0fb99c601b2f', 10.5),
(4, 'e27114f8-1192-423e-91a6-a88e5700959b', 'PQR001', 'a8c6c3c6-c040-4acd-9822-003638bac408', 110);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_order`
--

CREATE TABLE `purchase_order` (
  `order_id` varchar(36) NOT NULL,
  `order_date` varchar(20) NOT NULL,
  `quotation_report_id` varchar(36) NOT NULL,
  `delivery` varchar(200) NOT NULL,
  `delivery_location` varchar(200) NOT NULL,
  `payment` varchar(200) NOT NULL,
  `additional_note` varchar(200) NOT NULL,
  `status` varchar(100) NOT NULL DEFAULT 'AWAITING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchase_order`
--

INSERT INTO `purchase_order` (`order_id`, `order_date`, `quotation_report_id`, `delivery`, `delivery_location`, `payment`, `additional_note`, `status`) VALUES
('PO001', '2023-07-03', 'PQR001', 'aaa', 'bbb', 'ccc', 'ddd', 'AWAITING');

-- --------------------------------------------------------

--
-- Table structure for table `purchase_request`
--

CREATE TABLE `purchase_request` (
  `request_id` varchar(36) NOT NULL,
  `requested_date` date NOT NULL,
  `due_date` date NOT NULL,
  `requested_department` varchar(50) NOT NULL,
  `approved` tinyint(1) NOT NULL DEFAULT 0,
  `request_status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchase_request`
--

INSERT INTO `purchase_request` (`request_id`, `requested_date`, `due_date`, `requested_department`, `approved`, `request_status`) VALUES
('PR001', '2023-07-03', '2023-07-20', 'HUMAN_RESOURCES', 1, 'PROCESSING'),
('PR002', '2023-07-03', '2023-07-13', 'PLANNING', 1, 'PROCESSING'),
('PR003', '2023-07-03', '2023-07-31', 'ACCOUNTING', 0, 'PENDING_APPROVAL'),
('PR004', '2023-07-03', '2023-06-29', 'PRODUCTION', 1, 'PROCESSING');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `user_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `user_role`) VALUES
(1, 'p', '123', 'PURCHASER'),
(2, 'm', '123', 'MANAGER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `purchase_request_id` (`purchase_request_id`),
  ADD KEY `price_quotation_report_id` (`price_quotation_report_id`),
  ADD KEY `purchase_order_id` (`purchase_order_id`);

--
-- Indexes for table `price_quotation`
--
ALTER TABLE `price_quotation`
  ADD PRIMARY KEY (`quotation_id`),
  ADD KEY `price_quotation_report_id` (`price_quotation_report_id`);

--
-- Indexes for table `price_quotation_report`
--
ALTER TABLE `price_quotation_report`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `price_quotation__quoted_price`
--
ALTER TABLE `price_quotation__quoted_price`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `purchase_order_ibfk_1` (`quotation_report_id`);

--
-- Indexes for table `purchase_request`
--
ALTER TABLE `purchase_request`
  ADD PRIMARY KEY (`request_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `price_quotation__quoted_price`
--
ALTER TABLE `price_quotation__quoted_price`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`purchase_request_id`) REFERENCES `purchase_request` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `item_ibfk_2` FOREIGN KEY (`price_quotation_report_id`) REFERENCES `price_quotation_report` (`report_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `item_ibfk_3` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`order_id`) ON DELETE CASCADE;

--
-- Constraints for table `price_quotation`
--
ALTER TABLE `price_quotation`
  ADD CONSTRAINT `price_quotation_ibfk_1` FOREIGN KEY (`price_quotation_report_id`) REFERENCES `price_quotation_report` (`report_id`) ON DELETE CASCADE;

--
-- Constraints for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD CONSTRAINT `purchase_order_ibfk_1` FOREIGN KEY (`quotation_report_id`) REFERENCES `price_quotation_report` (`report_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
