SELECT 
    id, Role$id AS 'role.id', name, email
FROM
    gate.Uzer
WHERE
    active AND email IS NOT NULL
        AND (Role$id IN (? , ?) OR id IN (? , ?))