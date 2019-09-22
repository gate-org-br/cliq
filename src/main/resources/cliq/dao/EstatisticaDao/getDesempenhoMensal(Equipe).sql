SELECT 
    DATE(Evento.data) AS Mês,
    SUM(Evento.tipo = 'CRIACAO') AS Criados,
    SUM(Evento.tipo = 'CONCLUSAO') AS Concluídos,
    SUM(Evento.tipo = 'CANCELAMENTO') AS Cancelados
FROM
    Evento
        JOIN
    Chamado ON Evento.Chamado$id = Chamado.id
WHERE
    Chamado.Localizacao$id = ?
GROUP BY YEAR(Evento.data) , MONTH(Evento.data)
ORDER BY YEAR(Evento.data) DESC , MONTH(Evento.data) DESC