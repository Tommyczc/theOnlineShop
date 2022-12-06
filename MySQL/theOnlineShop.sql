-- --------------------------------------------------------
-- 主机:                           192.168.1.5
-- 服务器版本:                        10.4.25-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 theonlineshop 的数据库结构
DROP DATABASE IF EXISTS `theonlineshop`;
CREATE DATABASE IF NOT EXISTS `theonlineshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `theonlineshop`;

-- 导出  表 theonlineshop.emailverificationlist 结构
DROP TABLE IF EXISTS `emailverificationlist`;
CREATE TABLE IF NOT EXISTS `emailverificationlist` (
  `email` varchar(50) NOT NULL,
  `code` varchar(100) NOT NULL DEFAULT '',
  `time` datetime NOT NULL,
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

-- 导出  表 theonlineshop.permissionlist 结构
DROP TABLE IF EXISTS `permissionlist`;
CREATE TABLE IF NOT EXISTS `permissionlist` (
  `roleName` varchar(50) NOT NULL,
  `permission` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

-- 导出  表 theonlineshop.productlist 结构
DROP TABLE IF EXISTS `productlist`;
CREATE TABLE IF NOT EXISTS `productlist` (
  `userName` varchar(50) NOT NULL,
  `productName` varchar(50) NOT NULL,
  `productImageUrl` varchar(50) DEFAULT NULL,
  `productDescription` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

-- 导出  表 theonlineshop.rolelist 结构
DROP TABLE IF EXISTS `rolelist`;
CREATE TABLE IF NOT EXISTS `rolelist` (
  `userName` varchar(50) NOT NULL,
  `roleName` varchar(10) NOT NULL,
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

-- 导出  表 theonlineshop.userlist 结构
DROP TABLE IF EXISTS `userlist`;
CREATE TABLE IF NOT EXISTS `userlist` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `headSculpture` varchar(200) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `registerTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
