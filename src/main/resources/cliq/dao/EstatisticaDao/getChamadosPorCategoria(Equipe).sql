SELECT 
    COALESCE(Categoria.nome, 'Não Categorizado') AS Categoria,
    COUNT(Chamado.id) AS Quantidade
FROM
    Chamado
        LEFT JOIN
    Categoria ON Chamado.Categoria$id = Categoria.id
WHERE
    Chamado.situacao = 'PENDENTE'
        AND Chamado.Localizacao$id = ?
GROUP BY Categoria.id
ORDER BY COUNT(Chamado.id) DESC