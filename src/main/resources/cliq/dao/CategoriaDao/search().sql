SELECT
    Role.id AS id,
    Role.name AS name,
    Role.Role$id AS 'role.id',
    COUNT(Categoria.id) > 0 AS ativo
FROM
    gate.Role
        LEFT JOIN
    Categoria ON Categoria.Role$id = Role.id
GROUP BY Role.id