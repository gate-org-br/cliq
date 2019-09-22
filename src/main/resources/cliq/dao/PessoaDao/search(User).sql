select
    Uzer.id as id,
    Uzer.userID as userID,
    Uzer.name as name,
    Role.id as 'role.id',
    Role.name as 'role.name'
from
    gate.Uzer
        join
    gate.Role ON Uzer.Role$id = Role.id