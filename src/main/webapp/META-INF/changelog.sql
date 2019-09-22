Versão 2.1.0

ALTER TABLE `cliq`.`Categoria` 
CHANGE COLUMN `id` `id` INT(10) UNSIGNED NOT NULL ,
ADD COLUMN `Atribuir$id` INT(10) UNSIGNED NULL AFTER `EquipeAprovadora$id`,
ADD COLUMN `Encaminhar$id` INT(10) UNSIGNED NULL AFTER `Atribuir$id`,
ADD INDEX `Categoria$fk$Atribuir` (`Atribuir$id` ASC),
ADD INDEX `Categoria$fk$Encaminhar` (`Encaminhar$id` ASC);
ALTER TABLE `cliq`.`Categoria` 
ADD CONSTRAINT `Categoria$fk$Atribuir`
  FOREIGN KEY (`Atribuir$id`)
  REFERENCES `gate`.`Uzer` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `Categoria$fk$Encaminhar`
  FOREIGN KEY (`Encaminhar$id`)
  REFERENCES `gate`.`Role` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

Versão 2.0.0:

CREATE TABLE `cliq`.`Compartilhamento` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Equipe$id` INT UNSIGNED NULL,
  `Pessoa$id` INT UNSIGNED NULL,
  `Chamado$id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `Chamaro$fk$Pessoa` (`Pessoa$id` ASC),
  INDEX `Compartilhamento$fk$Equipe` (`Equipe$id` ASC),
  INDEX `Compartilhamento$fk$Chamado` (`Chamado$id` ASC),
  UNIQUE INDEX `Compartilhamento$uk$Chamado$Pessoa` (`Pessoa$id` ASC, `Chamado$id` ASC),
  UNIQUE INDEX `Compartilhamento$uk$Chamado$Equipe` (`Chamado$id` ASC, `Equipe$id` ASC),
  CONSTRAINT `Compartilhamento$fk$Pessoa`
    FOREIGN KEY (`Pessoa$id`)
    REFERENCES `gate`.`Uzer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Compartilhamento$fk$Equipe`
    FOREIGN KEY (`Equipe$id`)
    REFERENCES `gate`.`Role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Compartilhamento$fk$Chamado`
    FOREIGN KEY (`Chamado$id`)
    REFERENCES `cliq`.`Chamado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

Versão 1.8.1:

ALTER TABLE `cliq`.`Chamado` 
DROP INDEX `Chamado$fk1` ,
ADD INDEX `Chamado$fk$Localizacao` (`Localizacao$id` ASC),
DROP INDEX `Chamado$fk2` ,
ADD INDEX `Chamado$fk$Categoria` (`Categoria$id` ASC),
DROP INDEX `Chamado$fk3` ,
ADD INDEX `Chamado$fk$Solicitante` (`Solicitante$id` ASC),
DROP INDEX `Chamado$fk4` ,
ADD INDEX `Chamado$fk$Atendente` (`Atendente$id` ASC),
DROP INDEX `Chamado$fk5` ,
ADD INDEX `Chamado$fk$Contato` (`Contato$id` ASC),
DROP INDEX `Chamado$id` ,
ADD INDEX `Chamado$fk$Chamado` (`Chamado$id` ASC),
DROP INDEX `Chamafo$fk9_idx` ,
ADD INDEX `Chamafo$fk$Origem` (`Origem$id` ASC);

ALTER TABLE `cliq`.`Chamado` 
ADD INDEX `Chamado$indx$suspenso$retomada` (`suspenso` ASC, `retomada` ASC);


Versão 1.8.0:

ALTER TABLE `cliq`.`Chamado` CHANGE COLUMN `nome` `titulo` TEXT NOT NULL;
update Categoria set campos = replace(campos, '#nome', '#titulo'); 
update Categoria set campos = replace(campos, '@nome', '@titulo');
