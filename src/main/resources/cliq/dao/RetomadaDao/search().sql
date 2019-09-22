SELECT 
    id
FROM
    Chamado
WHERE
    retomada IS NOT NULL
        AND retomada <= NOW()