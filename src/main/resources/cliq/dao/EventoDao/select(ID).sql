SELECT
    Evento.id,
    Uzer.id AS 'user.id',
    Uzer.name AS 'user.name',
    Evento.Chamado$id AS 'chamado.id',
    Evento.Anexo$id as 'anexo.id',
    Origem.id as 'origem.id',
    Origem.name as 'origem.name',
    Evento.tipo,
    Evento.data,
    Evento.descricao,
    Evento.observacoes,
    Evento.alteracao
FROM
    Evento
    left join gate.Uzer on Evento.Uzer$id = Uzer.id
    left join gate.Role as Origem on Evento.Origem$id = Origem.id
WHERE
    Evento.id = ?