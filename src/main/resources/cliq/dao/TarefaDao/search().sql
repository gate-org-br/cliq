select
    Tarefa.id as id,
    Tarefa.inicio as inicio,
    Tarefa.periodo$valor as 'periodo.valor',
    Tarefa.periodo$unidade as 'periodo.unidade',
    Categoria.id as 'categoria.id',
    Categoria.nome as 'categoria.nome',
    Origem.id as 'origem.id',
    Origem.name as 'origem.name',
    Atendente.id as 'atendente.id',
    Atendente.name as 'atendente.name',
    Tarefa.nome as nome,
    Tarefa.descricao as descricao,
    Role.id as 'categoria.role.id',
    Role.name as 'categoria.role.name'
from
    Tarefa
        left join
    Categoria ON Tarefa.Categoria$id = Categoria.id
        left join
    gate.Role ON Categoria.Role$id = Role.id
        left join
    gate.Uzer as Atendente ON Tarefa.Atendente$id = Atendente.id
        left join
    gate.Role as Origem ON Tarefa.Origem$id = Origem.id
where
    Tarefa.inicio <= now()