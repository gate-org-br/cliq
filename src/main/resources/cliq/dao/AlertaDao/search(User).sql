SELECT 
    Chamado.id AS id,
    Categoria.id AS 'categoria.id',
    Categoria.nome AS 'categoria.nome',
    Chamado.notificados as notificados,
    Chamado.data AS data,
    Origem.id AS 'origem.id',
    Origem.name AS 'origem.name',
    Solicitante.id AS 'solicitante.id',
    Solicitante.name AS 'solicitante.name',
    Localizacao.id AS 'localizacao.id',
    Localizacao.name AS 'localizacao.name',
    Atendente.id AS 'atendente.id',
    Atendente.name AS 'atendente.name',
    Chamado.titulo AS titulo,
    Chamado.descricao AS descricao,
    Evento.id AS 'evento.id',
    Evento.tipo AS 'evento.tipo',
    Evento.data AS 'evento.data',
    Uzer.id AS 'evento.user.id',
    Uzer.name AS 'evento.user.name',
    Evento.descricao AS 'evento.descricao',
    Evento.observacoes AS 'evento.observacoes'
FROM
    Chamado
        LEFT JOIN
    Categoria ON Chamado.Categoria$id = Categoria.id
        LEFT JOIN
    gate.Role AS Origem ON Chamado.Origem$id = Origem.id
        LEFT JOIN
    gate.Uzer AS Solicitante ON Chamado.Solicitante$id = Solicitante.id
        LEFT JOIN
    gate.Role AS Localizacao ON Chamado.Localizacao$id = Localizacao.id
        LEFT JOIN
    gate.Uzer AS Atendente ON Chamado.Atendente$id = Atendente.id
        LEFT JOIN
    Evento ON Chamado.Evento$id = Evento.id
        LEFT JOIN
    Uzer ON Evento.Uzer$id = Uzer.id