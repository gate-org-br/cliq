ALTER TABLE `cliq`.`Evento`
DROP FOREIGN KEY `Ocorrencia$fk$Chamado`,
DROP FOREIGN KEY `Ocorrencia$fk$Uzer`;

ALTER TABLE `cliq`.`Evento`
DROP INDEX `Ocorrencia$fk1` ,
ADD INDEX `Evento$fk$Uzer` (`Uzer$id` ASC),
DROP INDEX `Ocorrencia$fk2` ,
ADD INDEX `Evento$fk$Chamado` (`Chamado$id` ASC);

ALTER TABLE `cliq`.`Evento`
ADD CONSTRAINT `Evento$fk$Chamado`
  FOREIGN KEY (`Chamado$id`)
  REFERENCES `cliq`.`Chamado` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `Evento$fk$Uzer`
  FOREIGN KEY (`Uzer$id`)
  REFERENCES `gate`.`Uzer` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;


//////////////////////////////////////////////////////////////

CREATE TABLE `Anexo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `arquivo` longtext NOT NULL,
  `Chamado$id` int(10) DEFAULT NULL,
  `Evento$id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `cliq`.`Categoria`
DROP COLUMN `icon24`,
CHANGE COLUMN `icon48` `icon` BLOB NOT NULL ;

ALTER TABLE `cliq`.`Categoria`
CHANGE COLUMN `icon` `icon` LONGTEXT NOT NULL ;

ALTER TABLE cliq.Categoria ADD converted TEXT NULL;

// !!!!!!!!!!!!!!!!!!!!! RODAR CONVERSOR !!!!!!!!!!!!!!!!!!!!!!

update Categoria set icon = converted;
ALTER TABLE cliq.Categoria drop column converted;

ALTER TABLE `cliq`.`Chamado`
ADD COLUMN `Anexo$id` INT(10) UNSIGNED NULL AFTER `EquipeAprovadora$id`,
ADD INDEX `Chamado$fk$Anexo` (`Anexo$id` ASC);
ALTER TABLE `cliq`.`Chamado`
ADD CONSTRAINT `Chamado$fk$Anexo`
  FOREIGN KEY (`Anexo$id`)
  REFERENCES `cliq`.`Anexo` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;

ALTER TABLE `cliq`.`Evento`
ADD COLUMN `Anexo$id` INT(10) UNSIGNED NULL AFTER `Chamado$id`,
ADD INDEX `Evento$fk$Anexo` (`Anexo$id` ASC);
ALTER TABLE `cliq`.`Evento`
ADD CONSTRAINT `Evento$fk$Anexo`
  FOREIGN KEY (`Anexo$id`)
  REFERENCES `cliq`.`Anexo` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;

ALTER TABLE `cliq`.`Categoria`
ADD COLUMN `Anexo$id` INT(10) UNSIGNED NULL AFTER `Role$id`,
ADD INDEX `Categoria$fk$Anexo` (`Anexo$id` ASC);
ALTER TABLE `cliq`.`Categoria`
ADD CONSTRAINT `Categoria$fk$Anexo`
  FOREIGN KEY (`Anexo$id`)
  REFERENCES `cliq`.`Anexo` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;


UPDATE Chamado
SET
    Anexo$id = (SELECT
            id
        FROM
            Anexo
        WHERE
            Chamado$id = Chamado.id)
WHERE
    arquivo$name IS NOT NULL;

UPDATE Evento
SET
    Anexo$id = (SELECT
            id
        FROM
            Anexo
        WHERE
            Evento$id = Evento.id)
WHERE
    arquivo$name IS NOT NULL;


ALTER TABLE `cliq`.`Chamado`
DROP COLUMN `arquivo$data`,
DROP COLUMN `arquivo$size`,
DROP COLUMN `arquivo$name`;

ALTER TABLE `cliq`.`Evento`
DROP COLUMN `arquivo$data`,
DROP COLUMN `arquivo$size`,
DROP COLUMN `arquivo$name`;

ALTER TABLE `cliq`.`Anexo`
DROP COLUMN `Evento$id`,
DROP COLUMN `Chamado$id`;

//////////////////////////////////////////////////////////////

ALTER TABLE `cliq`.`Chamado`
DROP COLUMN `backup`;

ALTER TABLE `cliq`.`Evento`
DROP COLUMN `backup`;

ALTER TABLE `cliq`.`Categoria`
DROP FOREIGN KEY `Categoria$fk$Categoria`;
ALTER TABLE `cliq`.`Categoria`
CHANGE COLUMN `Categoria$id` `Parent$id` INT(10) UNSIGNED NULL DEFAULT NULL ;
ALTER TABLE `cliq`.`Categoria`
ADD CONSTRAINT `Categoria$fk$Parent`
  FOREIGN KEY (`Parent$id`)
  REFERENCES `cliq`.`Categoria` (`id`);

ALTER TABLE cliq.Evento MODIFY COLUMN tipo varchar(32) NULL;
update Evento set tipo = 'CRIACAO' where tipo = '0';
update Evento set tipo = 'COMENTARIO_SIMPLES' where tipo = '1';
update Evento set tipo = 'CONCLUSAO' where tipo = '2';
update Evento set tipo = 'CANCELAMENTO' where tipo = '3';
update Evento set tipo = 'ENCAMINHAMENTO' where tipo = '4';
update Evento set tipo = 'CAPTURA' where tipo = '5';
update Evento set tipo = 'ATRIBUICAO' where tipo = '6';
update Evento set tipo = 'SUSPENSAO' where tipo = '7';
update Evento set tipo = 'FEEDBACK' where tipo = '8';
update Evento set tipo = 'IMPORTACAO' where tipo = '9';
update Evento set tipo = 'DISTRIBUICAO' where tipo = '10';
update Evento set tipo = 'REABERTURA' where tipo = '11';
update Evento set tipo = 'LIBERACAO' where tipo = '12';
update Evento set tipo = 'RETOMADA' where tipo = '13';
update Evento set tipo = 'APROVACAO' where tipo = '14';
update Evento set tipo = 'APROVACAO_ACEITE' where tipo = '15';
update Evento set tipo = 'APROVACAO_RECUSA' where tipo = '16';
update Evento set tipo = 'COMENTARIO_INTERNO' where tipo = '17';
update Evento set tipo = 'EXECUCAO_TAREFA' where tipo = '18';
update Evento set tipo = 'CANCELAMENTO_TAREFA' where tipo = '19';
update Evento set tipo = 'PROMOCAO' where tipo = '20';
update Evento set tipo = 'REBAIXAMENTO' where tipo = '21';
update Evento set tipo = 'PRAZO_RESPOSTA' where tipo = '22';
update Evento set tipo = 'PRAZO_SOLUCAO' where tipo = '23';
update Evento set tipo = 'ENVIO_AVALIACAO' where tipo = '24';
update Evento set tipo = 'AVALIACAO' where tipo = '25';
update Evento set tipo = 'FEEDBACK_ACEITE' where tipo = '26';
update Evento set tipo = 'FEEDBACK_RECUSA' where tipo = '27';
update Evento set tipo = 'FEEDBACK_CANCELAMENTO' where tipo = '28';
update Evento set tipo = 'APROVACAO_CANCELAMENTO' where tipo = '29';
update Evento set tipo = 'ATENDIMENTO' where tipo = '30';
update Evento set tipo = 'COMPARTILHAMENTO' where tipo = '31';
update Evento set tipo = 'DESCOMPARTILHAMENTO' where tipo = '32';
update Evento set tipo = 'HOMOLOGACAO' where tipo = '33';
update Evento set tipo = 'HOMOLOGACAO_ACEITE' where tipo = '34';
update Evento set tipo = 'HOMOLOGACAO_RECUSA' where tipo = '35';
UPDATE Evento SET tipo = 'HOMOLOGACAO_CANCELAMENTO' WHERE tipo = '36';

ALTER TABLE cliq.Chamado MODIFY COLUMN situacao varchar(32) NOT NULL;
update Chamado set situacao = 'PENDENTE' where situacao = '0';
update Chamado set situacao = 'CONCLUIDO' where situacao = '1';
update Chamado set situacao = 'CANCELADO' where situacao = '2';

ALTER TABLE cliq.Chamado MODIFY COLUMN prioridade varchar(32) DEFAULT 'BAIXA' NOT NULL;
update Chamado set prioridade = 'BAIXA' where prioridade = '0';
update Chamado set prioridade = 'MEDIA' where prioridade = '1';
update Chamado set prioridade = 'ALTA' where prioridade = '2';
update Chamado set prioridade = 'URGENTE' where prioridade = '3';

ALTER TABLE cliq.Chamado MODIFY COLUMN complexidade varchar(32) DEFAULT 'BAIXA' NOT NULL;
update Chamado set complexidade = 'BAIXA' where complexidade = '0';
update Chamado set complexidade = 'MEDIA' where complexidade = '1';
update Chamado set complexidade = 'ALTA' where complexidade = '2';
update Chamado set complexidade = 'CRITICA' where complexidade = '3';

ALTER TABLE cliq.Chamado MODIFY COLUMN nivel varchar(32) NULL;
update Chamado set nivel = 'NIVEL1' where nivel = '0';
update Chamado set nivel = 'NIVEL2' where nivel = '1';
update Chamado set nivel = 'NIVEL3' where nivel = '2';

ALTER TABLE cliq.Chamado MODIFY COLUMN nota varchar(32) NULL;
update Chamado set nota = 'PESSIMO' where nota = '0';
update Chamado set nota = 'RUIM' where nota = '1';
update Chamado set nota = 'REGULAR' where nota = '2';
update Chamado set nota = 'BOM' where nota = '3';
update Chamado set nota = 'EXCELENTE' where nota = '4';

ALTER TABLE cliq.Chamado MODIFY COLUMN atendimento varchar(32) NULL;
update Chamado set atendimento = 'LOCAL' where atendimento = '0';
update Chamado set atendimento = 'REMOTO' where atendimento = '1';

ALTER TABLE cliq.Categoria MODIFY COLUMN prioridade varchar(32) NOT NULL;
update Categoria set prioridade = 'BAIXA' where prioridade = '0';
update Categoria set prioridade = 'MEDIA' where prioridade = '1';
update Categoria set prioridade = 'ALTA' where prioridade = '2';
update Categoria set prioridade = 'URGENTE' where prioridade = '3';

ALTER TABLE cliq.Categoria MODIFY COLUMN complexidade varchar(32) NOT NULL;
update Categoria set complexidade = 'BAIXA' where complexidade = '0';
update Categoria set complexidade = 'MEDIA' where complexidade = '1';
update Categoria set complexidade = 'ALTA' where complexidade = '2';
update Categoria set complexidade = 'CRITICA' where complexidade = '3';

ALTER TABLE cliq.Categoria CHANGE tipo visibilidade varchar(32) NOT NULL;
update Categoria set visibilidade = 'PRIVADA' where visibilidade = '0';
update Categoria set visibilidade = 'PUBLICA' where visibilidade = '1';

ALTER TABLE cliq.Categoria MODIFY COLUMN nivel varchar(32) NULL;
update Categoria set nivel = 'NIVEL1' where nivel = '0';
update Categoria set nivel = 'NIVEL2' where nivel = '1';
update Categoria set nivel = 'NIVEL3' where nivel = '2';

ALTER TABLE cliq.Categoria MODIFY COLUMN aprovacao varchar(32) DEFAULT 0 NULL;
update Categoria set aprovacao = 'PESSOA' where aprovacao = '0';
update Categoria set aprovacao = 'EQUIPE' where aprovacao = '1';

ALTER TABLE cliq.Contato MODIFY COLUMN tipo varchar(32) DEFAULT 0 NOT NULL;
update Contato set tipo = 'CIDADAO' where tipo = '0';
update Contato set tipo = 'EMPRESA' where tipo = '1';

ALTER TABLE cliq.Contato MODIFY COLUMN visibilidade varchar(32) DEFAULT 2 NOT NULL;
update Contato set visibilidade = 'PRIVADA' where visibilidade = '0';
update Contato set visibilidade = 'PUBLICA' where visibilidade = '1';

ALTER TABLE cliq.SLA MODIFY COLUMN prioridade varchar(32) NULL;
update SLA set prioridade = 'BAIXA' where prioridade = '0';
update SLA set prioridade = 'MEDIA' where prioridade = '1';
update SLA set prioridade = 'ALTA' where prioridade = '2';
update SLA set prioridade = 'URGENTE' where prioridade = '3';

ALTER TABLE cliq.SLA MODIFY COLUMN complexidade varchar(32) NULL;
update SLA set complexidade = 'BAIXA' where complexidade = '0';
update SLA set complexidade = 'MEDIA' where complexidade = '1';
update SLA set complexidade = 'ALTA' where complexidade = '2';
update SLA set complexidade = 'CRITICA' where complexidade = '3';

ALTER TABLE cliq.Categoria MODIFY COLUMN sigilosa tinyint(1) unsigned DEFAULT 0 NOT NULL;

alter table Evento add column anexo mediumtext;

update Chamado set formulario = null where formulario = '{  }';
update Categoria set formulario = null where formulario = '{  }';

update Chamado set formulario = SUBSTRING(formulario, 9, CHAR_LENGTH(formulario)-9) where formulario is not null
update Categoria set formulario = SUBSTRING(formulario, 9, CHAR_LENGTH(formulario)-9) where formulario is not null

update Chamado set formulario = SUBSTRING(formulario,4) where formulario like 's%'
update Categoria set formulario = SUBSTRING(formulario,4) where formulario like 's%'

ALTER TABLE `cliq`.`Categoria`
DROP FOREIGN KEY `Categoria$fk$EquipeAprovadora`,
DROP FOREIGN KEY `Categoria$fk$PessoaAprovadora`;
ALTER TABLE `cliq`.`Categoria`
ADD COLUMN `homologacao` VARCHAR(32) NULL AFTER `EquipeAprovadora$id`,
ADD COLUMN `PessoaHomologadora$id` INT(10) UNSIGNED NULL AFTER `homologacao`,
ADD COLUMN `EquipeHomologadora$id` INT(10) UNSIGNED NULL AFTER `PessoaHomologadora$id`,
ADD INDEX `Categoria$fk$EquipeHomologadora` (`EquipeHomologadora$id` ASC);
ALTER TABLE `cliq`.`Categoria`
ADD CONSTRAINT `Categoria$fk$EquipeAprovadora`
  FOREIGN KEY (`EquipeAprovadora$id`)
  REFERENCES `gate`.`Role` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `Categoria$fk$PessoaAprovadora`
  FOREIGN KEY (`PessoaAprovadora$id`)
  REFERENCES `gate`.`Uzer` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `Categoria$fk$EquipeHomologadora`
  FOREIGN KEY (`EquipeHomologadora$id`)
  REFERENCES `gate`.`Role` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `Categoria$fk$PessoaHomologadora`
  FOREIGN KEY (`PessoaHomologadora$id`)
  REFERENCES `gate`.`Uzer` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `cliq`.`Chamado`
ADD COLUMN `PessoaHomologadora$id` INT(10) UNSIGNED NULL AFTER `EquipeAprovadora$id`,
ADD COLUMN `EquipeHomologadora$id` INT(10) UNSIGNED NULL AFTER `PessoaHomologadora$id`,
ADD INDEX `Chamado$fk$EquipeHomologadora` (`EquipeHomologadora$id` ASC),
ADD INDEX `Chamado$fk$PessoaHomologadora` (`PessoaHomologadora$id` ASC);
ALTER TABLE `cliq`.`Chamado`
ADD CONSTRAINT `Chamado$fk$EquipeHomologadora`
  FOREIGN KEY (`EquipeHomologadora$id`)
  REFERENCES `gate`.`Role` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `Chamado$fk$PessoaHomologadora`
  FOREIGN KEY (`PessoaHomologadora$id`)
  REFERENCES `gate`.`Uzer` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE `cliq`.`Evento`
ADD COLUMN `alteracao` DATETIME NULL AFTER `status`;
ALTER TABLE `cliq`.`Chamado`
ADD COLUMN `alteracao` DATETIME NULL AFTER `checklist`;

ALTER TABLE `cliq`.`Categoria` 
CHANGE COLUMN `generica` `triagem` TINYINT(1) NOT NULL DEFAULT '0' ;

ALTER TABLE `cliq`.`Chamado` 
ADD COLUMN `pendencia` VARCHAR(32) NULL AFTER `situacao`;

UPDATE Chamado SET pendencia = 'APROVACAO' WHERE EquipeAprovadora$id IS NOT NULL OR PessoaAprovadora$id IS NOT NULL;

UPDATE Chamado SET pendencia = 'SUSPENSO' WHERE suspenso; 
alter table Chamado drop column suspenso;

UPDATE Chamado SET pendencia = 'FEEDBACK' WHERE feedback IS NOT NULL;

UPDATE Chamado SET pendencia = 'AVALIACAO' WHERE avaliacao;
alter table Chamado drop column avaliacao;

UPDATE Chamado SET pendencia = 'TRIAGEM' WHERE generico;
ALTER table cliq.Chamado drop column generico;

update Chamado set pendencia = 'NENHUMA' where pendencia is null;

update Chamado set pendencia = 'NENHUMA' where situacao = 'CONCLUIDO' and pendencia = 'SUSPENSO';

ALTER TABLE `cliq`.`Chamado` 
CHANGE COLUMN `pendencia` `pendencia` VARCHAR(32) NOT NULL ;



ALTER TABLE `cliq`.`Categoria` 
DROP COLUMN `homologacao`,
DROP COLUMN `aprovacao`;


ALTER TABLE `cliq`.`Evento` 
ADD COLUMN `Origem$id` INT(10) UNSIGNED NULL AFTER `Chamado$id`,
ADD INDEX `Evento$fk$Origem` (`Origem$id` ASC) VISIBLE;

ALTER TABLE `cliq`.`Evento` 
ADD CONSTRAINT `Evento$fk$Origem`
  FOREIGN KEY (`Origem$id`)
  REFERENCES `gate`.`Role` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;

UPDATE Evento 
SET 
    Origem$id = (SELECT 
            Role$id
        FROM
            gate.Uzer
        WHERE
            Evento.Uzer$id = Uzer.id)
WHERE
    Uzer$id IS NOT NULL


ALTER TABLE `cliq`.`Chamado` 
ADD COLUMN `Tipo$id` INT(10) UNSIGNED NULL AFTER `Categoria$id`,
ADD INDEX `Chamado$fk$Tipo` (`Tipo$id` ASC);
;
ALTER TABLE `cliq`.`Chamado` 
ADD CONSTRAINT `Chamado$fk$Tipo`
  FOREIGN KEY (`Tipo$id`)
  REFERENCES `cliq`.`Categoria` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `Chamado$fk$Categoria`
  FOREIGN KEY (`Categoria$id`)
  REFERENCES `cliq`.`Categoria` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

update Chamado set Tipo$id = Categoria$id

ALTER TABLE `cliq`.`Categoria` 
CHANGE COLUMN `temporaria` `temporaria` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' ;