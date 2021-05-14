ALTER TABLE `cliq`.`Chamado` 
CHANGE COLUMN `descricao` `descricao` MEDIUMTEXT NULL DEFAULT NULL ;

ALTER TABLE `cliq`.`Evento` 
CHANGE COLUMN `observacoes` `observacoes` MEDIUMTEXT NULL DEFAULT NULL ;

ALTER TABLE `cliq`.`Chamado` 
CHANGE COLUMN `titulo` `titulo` VARCHAR(128) NOT NULL ;

UPDATE Chamado 
SET 
    feedback = (SELECT 
            MAX(data)
        FROM
            Evento
        WHERE
            tipo = 'FEEDBACK'
                AND Evento.Chamado$id = Chamado.id)
WHERE
    pendencia = 'FEEDBACK'
        AND feedback IS NULL