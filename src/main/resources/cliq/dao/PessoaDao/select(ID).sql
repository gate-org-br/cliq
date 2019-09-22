select
    Uzer.id as id,
    Uzer.userID as userID,
    Uzer.active as active,
    Role.id as 'role.id',
    Role.name as 'role.name',
    Uzer.name as name,
    Uzer.email as email,
    Uzer.phone as phone,
    Uzer.cellPhone as cellPhone,
    Uzer.details as details
from
    gate.Uzer
        left join
    gate.Role ON Uzer.Role$id = Role.id
where
    Uzer.id = ?