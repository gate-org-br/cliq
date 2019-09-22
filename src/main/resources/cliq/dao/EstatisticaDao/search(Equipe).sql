SELECT 
    *
FROM
    (SELECT 
        COALESCE(SUM(Evento.tipo = 'CRIACAO'), 0) AS criacoes,
            COALESCE(SUM(Evento.tipo = 'CONCLUSAO'), 0) AS conclusoes,
            COALESCE(SUM(Evento.tipo = 'CANCELAMENTO'), 0) AS cancelamentos
    FROM
        Evento
    JOIN Chamado ON Evento.Chamado$id = Chamado.id
    WHERE
        YEAR(Evento.data) = YEAR(NOW())
            AND MONTH(Evento.data) = MONTH(NOW())
            AND Chamado.Localizacao$id = ?) AS Eventos
        JOIN
    (SELECT 
        COUNT(Chamado.id) AS pendentes,
            COUNT(DISTINCT COALESCE(Chamado.Origem$id, - 1)) AS origens,
            COUNT(DISTINCT COALESCE(Chamado.Categoria$id, - 1)) AS categorias,
            COALESCE(sum(Chamado.resposta is not null)) AS emAtendimento,
            COALESCE(sum(Chamado.Atendente$id is not null)) AS atribuidos,
            COALESCE(SUM(Chamado.pendencia = 'NENHUMA'), 0) AS semPendencias,
            COALESCE(SUM(Chamado.pendencia = 'TRIAGEM'), 0) AS triagem,
            COALESCE(SUM(Chamado.pendencia = 'APROVACAO'), 0) AS aprovacao,
            COALESCE(SUM(Chamado.pendencia = 'COMPLEMENTACAO'), 0) AS complementacao,
            COALESCE(SUM(Chamado.pendencia = 'SUSPENSO'), 0) AS suspensos,
            COALESCE(SUM(Chamado.pendencia = 'HOMOLOGACAO'), 0) AS homologacao,
            COALESCE(SUM(Chamado.pendencia = 'FEEDBACK'), 0) AS feedback,
            COALESCE(SUM(Chamado.pendencia = 'AVALIACAO'), 0) AS avaliacao,
            COALESCE(SUM(Chamado.prioridade = 'BAIXA'), 0) AS prioridadeBaixa,
            COALESCE(SUM(Chamado.prioridade = 'MEDIA'), 0) AS prioridadeMedia,
            COALESCE(SUM(Chamado.prioridade = 'ALTA'), 0) AS prioridadeAlta,
            COALESCE(SUM(Chamado.prioridade = 'URGENTE'), 0) AS prioridadeUrgente,
            COALESCE(SUM(Chamado.complexidade = 'BAIXA'), 0) AS complexidadeBaixa,
            COALESCE(SUM(Chamado.complexidade = 'MEDIA'), 0) AS complexidadeMedia,
            COALESCE(SUM(Chamado.complexidade = 'ALTA'), 0) AS complexidadeAlta,
            COALESCE(SUM(Chamado.complexidade = 'CRITICA'), 0) AS complexidadeCritica
    FROM
        Chamado
    WHERE
        Chamado.situacao = 'PENDENTE'
            AND Chamado.Localizacao$id = ?) AS Chamados
