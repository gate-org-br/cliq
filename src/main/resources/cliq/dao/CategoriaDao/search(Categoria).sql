select
    id,
    Role$id as 'role.id',
    Parent$id as 'parent.id',
    nome,
    visibilidade,
    nivel,
    triagem,
    prioridade,
    complexidade,
    projeto,
    avaliacao,
    feedback,
    duracao,
    atalho
from
    Categoria
where
    Parent$id = ?
order by nome