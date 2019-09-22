SELECT 
    Evento.id as id,
    Chamado.id as 'chamado.id',
    Uzer.id AS 'user.id',
    Uzer.name AS 'user.name',
    Evento.Anexo$id AS 'anexo.id',
    Origem.id AS 'origem.id',
    Origem.name AS 'origem.name',
    Evento.tipo as tipo,
    Evento.data as data,
    Evento.descricao as descricao,
    Evento.observacoes as observacoes,
    Evento.alteracao as alteracao
FROM
    Evento
        JOIN
    Chamado ON Evento.Chamado$id = Chamado.id
        LEFT JOIN
    gate.Uzer ON Evento.Uzer$id = Uzer.id
        LEFT JOIN
    gate.Role AS Origem ON Evento.Origem$id = Origem.id