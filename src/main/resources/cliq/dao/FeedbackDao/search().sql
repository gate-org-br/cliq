SELECT 
    Chamado.id AS id,
    Evento.id AS 'evento.id',
    Evento.data AS 'evento.data',
    Uzer.id AS 'evento.user.id',
    Uzer.name AS 'evento.user.name'
FROM
    Chamado
        JOIN
    Evento ON Chamado.id = Evento.Chamado$id
        AND Evento.tipo = 'FEEDBACK'
        and Evento.data  = Chamado.feedback
        JOIN
    gate.Uzer ON Evento.Uzer$id = Uzer.id
WHERE
    feedback IS NOT NULL
        AND DATEDIFF(NOW(), feedback) > 10