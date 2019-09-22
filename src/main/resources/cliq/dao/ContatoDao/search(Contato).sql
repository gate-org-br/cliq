SELECT
    id,
    login,
    Role$id AS 'role.id',
    tipo,
    visibilidade,
    nome,
    telefone,
    celular,
    email,
    comentarios
FROM
    (SELECT
        id,
            userID as login,
            Role$id,
            'USUARIO' AS tipo,
            'PUBLICA' AS visibilidade,
            name AS nome,
            phone AS telefone,
            cellPhone AS celular,
            email,
            details AS comentarios
    FROM
        gate.Uzer where active UNION SELECT
            id,
            null as login,
            Role$id,
            tipo,
            visibilidade,
            nome,
            telefone,
            celular,
            email,
            comentarios
    FROM
        Contato) AS Contatos