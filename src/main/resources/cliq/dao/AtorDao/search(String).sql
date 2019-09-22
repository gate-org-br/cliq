SELECT
    *
FROM
    (SELECT
        id, 'PESSOA' AS tipo, userID AS sigla, name as nome, email
    FROM
        gate.Uzer
    WHERE
        active UNION SELECT
        id, 'EQUIPE' AS tipo, roleID AS sigla, name as nome, email
    FROM
        gate.Role
    WHERE
        active) AS Ator