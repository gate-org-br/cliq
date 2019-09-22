SELECT 
    IFNULL(Origem.name, 'Criado pelo CliQ') AS Origem,
    COUNT(Chamado.id) AS Quantidade
FROM
    Chamado
        LEFT JOIN
    gate.Role AS Origem ON Chamado.Origem$id = Origem.id
WHERE
    Chamado.situacao = 'PENDENTE'
        AND Chamado.Localizacao$id = ?
GROUP BY Origem.id
ORDER BY COUNT(Chamado.id) DESC