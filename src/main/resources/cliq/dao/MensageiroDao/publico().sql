SELECT 
    id, Role$id AS 'role.id', name, email
FROM
    gate.Uzer
WHERE
    active AND email IS NOT NULL
        AND (Role$id IN (? , ?)
        OR Role$id IN (SELECT 
            Equipe$id
        FROM
            Compartilhamento
        WHERE
            Chamado$id = ?)
        OR id IN (?, ?, ?)
        OR id IN (SELECT 
            Pessoa$id
        FROM
            Compartilhamento
        WHERE
            Chamado$id = ?))