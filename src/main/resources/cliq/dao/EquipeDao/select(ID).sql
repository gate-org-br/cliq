select
    Role.id as id,
    Role.roleID as roleID,
    Role.name as name,
    Role.email as email,
    Role.description as description,
    Parent.id as 'role.id',
    Parent.name as 'role.name',
    Uzer.id as 'user.id',
    Uzer.userID as 'user.userID',
    Uzer.name as 'user.name',
    Uzer.email as 'user.email',
    Categoria.id as 'categoria.id',
    Categoria.Parent$id as 'categoria.parent.id',
    Categoria.visibilidade as 'categoria.visibilidade',
    Categoria.nome as 'categoria.nome',
    Categoria.triagem as 'categoria.triagem',
    Categoria.prioridade as 'categoria.prioridade',
    Categoria.complexidade as 'categoria.complexidade',
    Categoria.projeto as 'categoria.projeto',
    Categoria.descricao as 'categoria.descricao'
from
    gate.Role
        left join
    gate.Uzer ON Uzer.active and Role.id = Uzer.Role$id
        left join
    gate.Role as Parent ON Role.Role$id = Parent.id
        left join
    Categoria ON (Role.id = Categoria.Role$id
        and not Categoria.triagem)
        or (Role.Role$id = Categoria.Role$id
        and Categoria.triagem)
where
    Role.id = ?
order by Categoria.nome , Uzer.name