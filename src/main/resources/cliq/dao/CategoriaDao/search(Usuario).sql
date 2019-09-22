select
    id,
    Role$id as 'role.id',
    Parent$id as 'parent.id',
    nome,
    campos,
    formulario,
    descricao,
    atalho
from
    Categoria
where
    (visibilidade = 'PRIVADA'
        and exists( select
            id
        from
            Acesso
        where
            Categoria$id = Categoria.id
                and Role$id = ?))
        or (visibilidade = 'PUBLICA'
        and not exists( select
            id
        from
            Acesso
        where
            Categoria$id = Categoria.id
                and Role$id = ?))
order by nome