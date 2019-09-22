select
    Tarefa.id as id,
    Tarefa.nome as nome,
    Tarefa.inicio as inicio,
    Tarefa.periodo$valor as 'periodo.valor',
    Tarefa.periodo$unidade as 'periodo.unidade',
    Categoria.id as 'categoria.id',
    Categoria.nome as 'categoria.nome'
from
    Tarefa
        left join
    Categoria ON Tarefa.Categoria$id = Categoria.id
        left join
    gate.Role ON Categoria.Role$id = Role.id
where
    Role.id = ?
order by Tarefa.inicio