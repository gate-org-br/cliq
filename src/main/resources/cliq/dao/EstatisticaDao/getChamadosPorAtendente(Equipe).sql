SELECT 
    IFNULL(Uzer.name, 'Não Atribuído') AS Atendente,
    COUNT(Chamado.id) AS Quantidade
FROM
    Chamado
        LEFT JOIN
    gate.Uzer ON Chamado.Atendente$id = Uzer.id
WHERE
    Chamado.situacao = 'PENDENTE'
        AND Chamado.Localizacao$id = ?
GROUP BY Uzer.id
ORDER BY COUNT(Chamado.id) DESC