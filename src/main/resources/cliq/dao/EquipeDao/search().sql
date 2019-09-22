SELECT
    Role.id AS id,
    Role.roleID AS roleID,
    Role.name AS name,
    Role.description AS description,
    Role.Role$id AS 'role.id',
    active AND COUNT(Categoria.id) > 0 AS contemCategorias
FROM
    gate.Role
        LEFT JOIN
    Categoria ON Categoria.Role$id = Role.id
GROUP BY Role.id