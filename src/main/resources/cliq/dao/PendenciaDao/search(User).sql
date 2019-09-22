SELECT 
    Chamado.id AS id,
    Chamado.data AS data,
    Chamado.titulo AS titulo,
    Chamado.situacao AS situacao,
    Chamado.pendencia AS pendencia,
    Categoria.id AS 'categoria.id',
    Categoria.nome AS 'categoria.nome',
    Contato.id AS 'contato.id',
    Contato.nome AS 'contato.nome',
    Origem.id AS 'origem.id',
    Origem.name AS 'origem.name',
    Solicitante.id AS 'solicitante.id',
    Solicitante.name AS 'solicitante.name',
    Atendente.id AS 'atendente.id',
    Atendente.name AS 'atendente.name',
    Chamado.notificados AS notificados
FROM
    Chamado
        LEFT JOIN
    Categoria ON Chamado.Categoria$id = Categoria.id
        LEFT JOIN
    gate.Uzer Atendente ON Chamado.Atendente$id = Atendente.id
        LEFT JOIN
    Contato ON Chamado.Contato$id = Contato.id
        LEFT JOIN
    gate.Uzer AS Solicitante ON Chamado.Solicitante$id = Solicitante.id
        LEFT JOIN
    gate.Role AS Origem ON Chamado.Origem$id = Origem.id
WHERE
    (Chamado.pendencia = 'APROVACAO'
        AND (Chamado.PessoaAprovadora$id = ?
        OR Chamado.EquipeAprovadora$id = ?))
        OR (Chamado.pendencia = 'HOMOLOGACAO'
        AND (Chamado.PessoaHomologadora$id = ?
        OR Chamado.EquipeHomologadora$id = ?))
	OR (Chamado.pendencia = 'COMPLEMENTACAO'
        AND Chamado.Solicitante$id = ?)
        OR (Chamado.pendencia = 'FEEDBACK'
        AND Chamado.Solicitante$id = ?)
        OR (Chamado.pendencia = 'AVALIACAO'
        AND Chamado.Solicitante$id = ?)
        OR (Chamado.pendencia = 'TRIAGEM'
        AND Chamado.Localizacao$id = ?)
ORDER BY data