-- MySQL dump 10.13  Distrib 8.0.19, for Linux (x86_64)
--
-- Host: localhost    Database: cliq
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Acesso`
--

DROP TABLE IF EXISTS `Acesso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Acesso` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `Role$id` int unsigned NOT NULL,
  `Contato$id` int unsigned DEFAULT NULL,
  `Categoria$id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Acesso$fk$Role` (`Role$id`),
  KEY `Acesso$fk$Categoria` (`Categoria$id`),
  KEY `Acesso$fk$Contato` (`Contato$id`),
  CONSTRAINT `Acesso$fk$Categoria` FOREIGN KEY (`Categoria$id`) REFERENCES `Categoria` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Acesso$fk$Contato` FOREIGN KEY (`Contato$id`) REFERENCES `Contato` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Acesso$fk$Role` FOREIGN KEY (`Role$id`) REFERENCES `gate`.`Role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3206 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Anexo`
--

DROP TABLE IF EXISTS `Anexo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Anexo` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `arquivo` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3782 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Categoria`
--

DROP TABLE IF EXISTS `Categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Categoria` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(64) NOT NULL,
  `descricao` text,
  `Role$id` int unsigned NOT NULL,
  `Anexo$id` int unsigned DEFAULT NULL,
  `visibilidade` varchar(32) NOT NULL,
  `triagem` tinyint(1) NOT NULL DEFAULT '0',
  `formulario` text,
  `prioridade` varchar(32) NOT NULL,
  `complexidade` varchar(32) NOT NULL,
  `Parent$id` int unsigned DEFAULT NULL,
  `temporaria` tinyint unsigned NOT NULL DEFAULT '0',
  `PessoaAprovadora$id` int unsigned DEFAULT NULL,
  `EquipeAprovadora$id` int unsigned DEFAULT NULL,
  `PessoaHomologadora$id` int unsigned DEFAULT NULL,
  `EquipeHomologadora$id` int unsigned DEFAULT NULL,
  `Atribuir$id` int unsigned DEFAULT NULL,
  `Encaminhar$id` int unsigned DEFAULT NULL,
  `campos` text,
  `sigilosa` tinyint unsigned NOT NULL DEFAULT '0',
  `icon` longtext NOT NULL,
  `checklist` text,
  `feedback` tinyint unsigned NOT NULL DEFAULT '0',
  `avaliacao` tinyint unsigned NOT NULL DEFAULT '0',
  `nivel` varchar(32) DEFAULT NULL,
  `projeto` tinyint unsigned NOT NULL DEFAULT '0',
  `duracao` int unsigned DEFAULT NULL,
  `conclusoes` text,
  `atalho` text,
  PRIMARY KEY (`id`),
  KEY `Classe$fk$Role` (`Role$id`),
  KEY `Categoria$fk$Categoria` (`Parent$id`),
  KEY `Categoria$fk$EquipeAprovadora` (`EquipeAprovadora$id`),
  KEY `Classe$fk$PessoaAprovadora` (`PessoaAprovadora$id`),
  KEY `Categoria$fk$Atribuir` (`Atribuir$id`),
  KEY `Categoria$fk$Encaminhar` (`Encaminhar$id`),
  KEY `Categoria$fk$Anexo` (`Anexo$id`),
  KEY `Categoria$fk$EquipeHomologadora` (`EquipeHomologadora$id`),
  KEY `Categoria$fk$PessoaHomologadora` (`PessoaHomologadora$id`),
  CONSTRAINT `Categoria$fk$Anexo` FOREIGN KEY (`Anexo$id`) REFERENCES `Anexo` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `Categoria$fk$Atribuir` FOREIGN KEY (`Atribuir$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Categoria$fk$Encaminhar` FOREIGN KEY (`Encaminhar$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `Categoria$fk$EquipeAprovadora` FOREIGN KEY (`EquipeAprovadora$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `Categoria$fk$EquipeHomologadora` FOREIGN KEY (`EquipeHomologadora$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `Categoria$fk$Parent` FOREIGN KEY (`Parent$id`) REFERENCES `Categoria` (`id`),
  CONSTRAINT `Categoria$fk$PessoaAprovadora` FOREIGN KEY (`PessoaAprovadora$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Categoria$fk$PessoaHomologadora` FOREIGN KEY (`PessoaHomologadora$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Categoria$fk$Role` FOREIGN KEY (`Role$id`) REFERENCES `gate`.`Role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=714 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Chamado`
--

DROP TABLE IF EXISTS `Chamado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Chamado` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `Evento$id` int unsigned DEFAULT NULL,
  `Categoria$id` int unsigned DEFAULT NULL,
  `Tipo$id` int unsigned DEFAULT NULL,
  `Origem$id` int unsigned DEFAULT NULL,
  `Solicitante$id` int unsigned DEFAULT NULL,
  `Contato$id` int unsigned DEFAULT NULL,
  `Localizacao$id` int unsigned NOT NULL,
  `Atendente$id` int unsigned DEFAULT NULL,
  `Chamado$id` int unsigned DEFAULT NULL,
  `PessoaAprovadora$id` int unsigned DEFAULT NULL,
  `EquipeAprovadora$id` int unsigned DEFAULT NULL,
  `PessoaHomologadora$id` int unsigned DEFAULT NULL,
  `EquipeHomologadora$id` int unsigned DEFAULT NULL,
  `Anexo$id` int unsigned DEFAULT NULL,
  `data` datetime NOT NULL,
  `situacao` varchar(32) NOT NULL,
  `pendencia` varchar(32) NOT NULL,
  `prioridade` varchar(32) NOT NULL DEFAULT 'BAIXA',
  `complexidade` varchar(32) NOT NULL DEFAULT 'BAIXA',
  `prazoSolucao` datetime DEFAULT NULL,
  `prazoResposta` datetime DEFAULT NULL,
  `retomada` datetime DEFAULT NULL,
  `sigiloso` tinyint unsigned NOT NULL DEFAULT '0',
  `projeto` tinyint(1) NOT NULL,
  `nivel` varchar(32) DEFAULT NULL,
  `nota` varchar(32) DEFAULT NULL,
  `atendimento` varchar(32) DEFAULT NULL,
  `feedback` datetime DEFAULT NULL,
  `documentacao` tinyint unsigned NOT NULL DEFAULT '0',
  `resposta` datetime DEFAULT NULL,
  `solucao` datetime DEFAULT NULL,
  `titulo` text NOT NULL,
  `descricao` text,
  `formulario` text,
  `notificados` text,
  `checklist` text,
  `alteracao` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Chamado$fk$PessoaAprovadora` (`PessoaAprovadora$id`),
  KEY `Chamado$fk$EquipeAprovadora` (`EquipeAprovadora$id`),
  KEY `Chamado$fk$Evento` (`Evento$id`),
  KEY `Chamado$fk$Localizacao` (`Localizacao$id`),
  KEY `Chamado$fk$Categoria` (`Categoria$id`),
  KEY `Chamado$fk$Solicitante` (`Solicitante$id`),
  KEY `Chamado$fk$Atendente` (`Atendente$id`),
  KEY `Chamado$fk$Contato` (`Contato$id`),
  KEY `Chamado$fk$Chamado` (`Chamado$id`),
  KEY `Chamafo$fk$Origem` (`Origem$id`),
  KEY `Chamado$indx$suspenso$retomada` (`retomada`),
  KEY `Chamado$fk$Anexo` (`Anexo$id`),
  KEY `Chamado$fk$EquipeHomologadora` (`EquipeHomologadora$id`),
  KEY `Chamado$fk$PessoaHomologadora` (`PessoaHomologadora$id`),
  KEY `Chamado$fk$Tipo` (`Tipo$id`),
  CONSTRAINT `Chamado$fk$Anexo` FOREIGN KEY (`Anexo$id`) REFERENCES `Anexo` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `Chamado$fk$Atendente` FOREIGN KEY (`Atendente$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Chamado$fk$Categoria` FOREIGN KEY (`Categoria$id`) REFERENCES `Categoria` (`id`),
  CONSTRAINT `Chamado$fk$Chamado` FOREIGN KEY (`Chamado$id`) REFERENCES `Chamado` (`id`),
  CONSTRAINT `Chamado$fk$Contato` FOREIGN KEY (`Contato$id`) REFERENCES `Contato` (`id`),
  CONSTRAINT `Chamado$fk$EquipeAprovadora` FOREIGN KEY (`EquipeAprovadora$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `Chamado$fk$EquipeHomologadora` FOREIGN KEY (`EquipeHomologadora$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `Chamado$fk$Evento` FOREIGN KEY (`Evento$id`) REFERENCES `Evento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Chamado$fk$Localizacao` FOREIGN KEY (`Localizacao$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `Chamado$fk$PessoaAprovadora` FOREIGN KEY (`PessoaAprovadora$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Chamado$fk$PessoaHomologadora` FOREIGN KEY (`PessoaHomologadora$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Chamado$fk$Solicitante` FOREIGN KEY (`Solicitante$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Chamado$fk$Tipo` FOREIGN KEY (`Tipo$id`) REFERENCES `Categoria` (`id`),
  CONSTRAINT `Chamafo$fk$Origem` FOREIGN KEY (`Origem$id`) REFERENCES `gate`.`Role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95519 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Compartilhamento`
--

DROP TABLE IF EXISTS `Compartilhamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Compartilhamento` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `Equipe$id` int unsigned DEFAULT NULL,
  `Pessoa$id` int unsigned DEFAULT NULL,
  `Chamado$id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Compartilhamento$uk$Chamado$Pessoa` (`Pessoa$id`,`Chamado$id`),
  UNIQUE KEY `Compartilhamento$uk$Chamado$Equipe` (`Chamado$id`,`Equipe$id`),
  KEY `Chamaro$fk$Pessoa` (`Pessoa$id`),
  KEY `Compartilhamento$fk$Equipe` (`Equipe$id`),
  KEY `Compartilhamento$fk$Chamado` (`Chamado$id`),
  CONSTRAINT `Compartilhamento$fk$Chamado` FOREIGN KEY (`Chamado$id`) REFERENCES `Chamado` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Compartilhamento$fk$Equipe` FOREIGN KEY (`Equipe$id`) REFERENCES `gate`.`Role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Compartilhamento$fk$Pessoa` FOREIGN KEY (`Pessoa$id`) REFERENCES `gate`.`Uzer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=373 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Contato`
--

DROP TABLE IF EXISTS `Contato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Contato` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tipo` varchar(32) NOT NULL DEFAULT '0',
  `visibilidade` varchar(32) NOT NULL DEFAULT '2',
  `nome` varchar(256) NOT NULL,
  `Role$id` int unsigned NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `comentarios` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Evento`
--

DROP TABLE IF EXISTS `Evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Evento` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tipo` varchar(32) DEFAULT NULL,
  `Uzer$id` int unsigned DEFAULT NULL,
  `Chamado$id` int unsigned NOT NULL,
  `Origem$id` int unsigned DEFAULT NULL,
  `Anexo$id` int unsigned DEFAULT NULL,
  `data` datetime NOT NULL,
  `descricao` text,
  `observacoes` text,
  `status` text,
  `alteracao` datetime DEFAULT NULL,
  `anexo` mediumtext,
  PRIMARY KEY (`id`),
  KEY `Evento$fk$Chamado` (`Chamado$id`),
  KEY `Evento$fk$Uzer` (`Uzer$id`),
  KEY `Evento$fk$Anexo` (`Anexo$id`),
  KEY `Evento$fk$Origem` (`Origem$id`),
  CONSTRAINT `Evento$fk$Anexo` FOREIGN KEY (`Anexo$id`) REFERENCES `Anexo` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `Evento$fk$Chamado` FOREIGN KEY (`Chamado$id`) REFERENCES `Chamado` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Evento$fk$Origem` FOREIGN KEY (`Origem$id`) REFERENCES `gate`.`Role` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `Evento$fk$Uzer` FOREIGN KEY (`Uzer$id`) REFERENCES `gate`.`Uzer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=232619 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!50001 DROP VIEW IF EXISTS `Role`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `Role` AS SELECT 
 1 AS `id`,
 1 AS `active`,
 1 AS `master`,
 1 AS `name`,
 1 AS `description`,
 1 AS `Role$id`,
 1 AS `email`,
 1 AS `Manager$id`,
 1 AS `roleID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SLA`
--

DROP TABLE IF EXISTS `SLA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SLA` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `Origem$id` int unsigned DEFAULT NULL,
  `Categoria$id` int unsigned DEFAULT NULL,
  `Solicitante$id` int unsigned DEFAULT NULL,
  `projeto` tinyint(1) DEFAULT NULL,
  `prioridade` varchar(32) DEFAULT NULL,
  `complexidade` varchar(32) DEFAULT NULL,
  `ini` int unsigned NOT NULL,
  `uini` int unsigned NOT NULL,
  `fim` int unsigned NOT NULL,
  `ufim` int unsigned NOT NULL,
  `urgente` tinyint unsigned NOT NULL,
  `precedencia` int unsigned NOT NULL,
  `nome` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `SLA$uk$Categoria$Role` (`Categoria$id`,`Origem$id`),
  KEY `SLA$fk$Origem` (`Origem$id`),
  KEY `SLA$fk$Solicitante` (`Solicitante$id`),
  CONSTRAINT `SLA$fk$Categoria` FOREIGN KEY (`Categoria$id`) REFERENCES `Categoria` (`id`),
  CONSTRAINT `SLA$fk$Origem` FOREIGN KEY (`Origem$id`) REFERENCES `gate`.`Role` (`id`),
  CONSTRAINT `SLA$fk$Solicitante` FOREIGN KEY (`Solicitante$id`) REFERENCES `gate`.`Uzer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tarefa`
--

DROP TABLE IF EXISTS `Tarefa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tarefa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(64) NOT NULL,
  `descricao` text NOT NULL,
  `inicio` datetime NOT NULL,
  `periodo$valor` int DEFAULT NULL,
  `Categoria$id` int unsigned NOT NULL,
  `previsao$valor` int DEFAULT NULL,
  `periodo$unidade` int unsigned DEFAULT NULL,
  `previsao$unidade` int unsigned DEFAULT NULL,
  `Atendente$id` int unsigned DEFAULT NULL,
  `Origem$id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Tarefa$fk$Categoria` (`Categoria$id`),
  KEY `Tarefa$fk$Atendente` (`Atendente$id`),
  KEY `Tarefa$fk$Origem` (`Origem$id`),
  CONSTRAINT `Tarefa$fk$Atendente` FOREIGN KEY (`Atendente$id`) REFERENCES `gate`.`Uzer` (`id`),
  CONSTRAINT `Tarefa$fk$Categoria` FOREIGN KEY (`Categoria$id`) REFERENCES `Categoria` (`id`),
  CONSTRAINT `Tarefa$fk$Origem` FOREIGN KEY (`Origem$id`) REFERENCES `gate`.`Role` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `Uzer`
--

DROP TABLE IF EXISTS `Uzer`;
/*!50001 DROP VIEW IF EXISTS `Uzer`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `Uzer` AS SELECT 
 1 AS `id`,
 1 AS `active`,
 1 AS `Role$id`,
 1 AS `userID`,
 1 AS `passwd`,
 1 AS `name`,
 1 AS `email`,
 1 AS `details`,
 1 AS `phone`,
 1 AS `cellPhone`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'cliq'
--

--
-- Final view structure for view `Role`
--

/*!50001 DROP VIEW IF EXISTS `Role`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `Role` AS select `gate`.`Role`.`id` AS `id`,`gate`.`Role`.`active` AS `active`,`gate`.`Role`.`master` AS `master`,`gate`.`Role`.`name` AS `name`,`gate`.`Role`.`description` AS `description`,`gate`.`Role`.`Role$id` AS `Role$id`,`gate`.`Role`.`email` AS `email`,`gate`.`Role`.`Manager$id` AS `Manager$id`,`gate`.`Role`.`roleID` AS `roleID` from `gate`.`Role` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `Uzer`
--

/*!50001 DROP VIEW IF EXISTS `Uzer`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `Uzer` AS select `gate`.`Uzer`.`id` AS `id`,`gate`.`Uzer`.`active` AS `active`,`gate`.`Uzer`.`Role$id` AS `Role$id`,`gate`.`Uzer`.`userID` AS `userID`,`gate`.`Uzer`.`passwd` AS `passwd`,`gate`.`Uzer`.`name` AS `name`,`gate`.`Uzer`.`email` AS `email`,`gate`.`Uzer`.`details` AS `details`,`gate`.`Uzer`.`phone` AS `phone`,`gate`.`Uzer`.`cellPhone` AS `cellPhone` from `gate`.`Uzer` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-16 18:06:15
