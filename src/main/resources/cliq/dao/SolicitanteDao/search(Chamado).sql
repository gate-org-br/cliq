SELECT 
    Chamado.id AS id,
    Chamado.data AS data,
    Chamado.situacao AS situacao,
    Chamado.pendencia AS pendencia,
    Chamado.prioridade AS prioridade,
    Chamado.titulo AS titulo,
    Chamado.descricao AS descricao,
    Chamado.nivel AS nivel,
    Chamado.resposta AS resposta,
    Chamado.solucao AS solucao,
    Chamado.projeto AS projeto,
    Chamado.feedback AS feedback,
    Chamado.prazoResposta AS prazoResposta,
    Chamado.prazoSolucao AS prazoSolucao,
    Chamado.formulario AS formulario,
    Chamado.documentacao AS documentacao,
    Chamado.notificados AS notificados,
    Tipo.id AS 'tipo.id',
    Tipo.nome AS 'tipo.nome',
    Categoria.id AS 'categoria.id',
    Categoria.nome AS 'categoria.nome',
    Origem.id AS 'origem.id',
    Origem.name AS 'origem.name',
    Solicitante.id AS 'solicitante.id',
    Solicitante.userID AS 'solicitante.userID',
    Solicitante.name AS 'solicitante.name',
    Solicitante.email AS 'solicitante.email',
    Atendente.id AS 'atendente.id',
    Atendente.userID AS 'atendente.userID',
    Atendente.name AS 'atendente.name',
    Atendente.email AS 'atendente.email',
    Contato.id AS 'contato.id',
    Contato.nome AS 'contato.nome',
    Chamado.PessoaAprovadora$id AS 'pessoaAprovadora.id',
    Chamado.EquipeAprovadora$id AS 'equipeAprovadora.id',
    Chamado.situacao = 'PENDENTE'
        AND ((Chamado.prazoResposta IS NOT NULL
        AND Chamado.prazoResposta < NOW())
        OR (Chamado.prazoSolucao IS NOT NULL
        AND Chamado.prazoSolucao < NOW())) AS atrasado
FROM
    Chamado
        LEFT JOIN
    Categoria as Tipo ON Chamado.Tipo$id = Tipo.id
        LEFT JOIN
    Categoria ON Chamado.Categoria$id = Categoria.id
        LEFT JOIN
    gate.Uzer AS Solicitante ON Chamado.Solicitante$id = Solicitante.id
        LEFT JOIN
    gate.Role AS Origem ON Chamado.Origem$id = Origem.id
        LEFT JOIN
    gate.Uzer AS Atendente ON Chamado.Atendente$id = Atendente.id
        LEFT JOIN
    gate.Role ON Chamado.Localizacao$id = Role.id
        LEFT JOIN
    Contato ON Chamado.Contato$id = Contato.id