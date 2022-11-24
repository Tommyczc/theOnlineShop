-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.4.25-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 theonlineshop 的数据库结构
CREATE DATABASE IF NOT EXISTS `theonlineshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `theonlineshop`;

-- 导出  表 theonlineshop.emailverificationlist 结构
CREATE TABLE IF NOT EXISTS `emailverificationlist` (
  `email` varchar(50) NOT NULL,
  `code` varchar(100) NOT NULL DEFAULT '',
  `time` datetime NOT NULL,
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  theonlineshop.emailverificationlist 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `emailverificationlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `emailverificationlist` ENABLE KEYS */;

-- 导出  表 theonlineshop.permissionlist 结构
CREATE TABLE IF NOT EXISTS `permissionlist` (
  `roleName` varchar(50) NOT NULL,
  `permission` varchar(50) NOT NULL,
  KEY `roleName` (`roleName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  theonlineshop.permissionlist 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `permissionlist` DISABLE KEYS */;
REPLACE INTO `permissionlist` (`roleName`, `permission`) VALUES
	('admin', 'admin:view'),
	('admin', 'admin:update'),
	('user', 'user:view'),
	('user', 'user:update'),
	('user', 'user:insert'),
	('user', 'user:delete'),
	('superAdmin', 'admin:view'),
	('superAdmin', 'admin:update'),
	('superAdmin', 'admin:insert'),
	('superAdmin', 'admin:delete');
/*!40000 ALTER TABLE `permissionlist` ENABLE KEYS */;

-- 导出  表 theonlineshop.productlist 结构
CREATE TABLE IF NOT EXISTS `productlist` (
  `userName` varchar(50) NOT NULL,
  `productName` varchar(50) NOT NULL,
  `productImageUrl` varchar(50) DEFAULT NULL,
  `productDescription` varchar(200) DEFAULT NULL,
  KEY `userName` (`userName`),
  CONSTRAINT `FK_productlist_userlist` FOREIGN KEY (`userName`) REFERENCES `userlist` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  theonlineshop.productlist 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `productlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `productlist` ENABLE KEYS */;

-- 导出  表 theonlineshop.rolelist 结构
CREATE TABLE IF NOT EXISTS `rolelist` (
  `userName` varchar(50) NOT NULL,
  `roleName` varchar(10) NOT NULL,
  UNIQUE KEY `userName` (`userName`),
  KEY `roleName` (`roleName`),
  CONSTRAINT `FK_rolelist_permissionlist` FOREIGN KEY (`roleName`) REFERENCES `permissionlist` (`roleName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_rolelist_userlist` FOREIGN KEY (`userName`) REFERENCES `userlist` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  theonlineshop.rolelist 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `rolelist` DISABLE KEYS */;
REPLACE INTO `rolelist` (`userName`, `roleName`) VALUES
	('g+eYmH81rr9DHFcBv7NMEA==', 'user');
/*!40000 ALTER TABLE `rolelist` ENABLE KEYS */;

-- 导出  表 theonlineshop.userlist 结构
CREATE TABLE IF NOT EXISTS `userlist` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  theonlineshop.userlist 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `userlist` DISABLE KEYS */;
REPLACE INTO `userlist` (`id`, `userName`, `password`, `email`, `address`, `age`) VALUES
	(1, 'g+eYmH81rr9DHFcBv7NMEA==', '827ccb0eea8a706c4c34a16891f84e7b', 'HfmY9pLdWfHXkuxmKBe12BQQHlcOiP/w4SDz9AQ6y30=', '', '');
/*!40000 ALTER TABLE `userlist` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
