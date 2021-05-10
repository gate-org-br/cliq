select
    SLA.id as id,
    SLA.precedencia as precedencia,
    SLA.nome as nome,
    Categoria.id as 'categoria.id',
    Categoria.nome as 'categoria.nome',
    Origem.id as 'origem.id',
    Origem.name as 'origem.name',
    Solicitante.id as 'solicitante.id',
    Solicitante.name as 'solicitante.name',
    SLA.projeto as projeto,
    SLA.prioridade as prioridade,
    SLA.complexidade as complexidade,
    SLA.ini as ini,
    SLA.fim as fim,
    SLA.uini as uini,
    SLA.ufim as ufim,
    SLA.urgente as urgente,
    Org.sun__min,
    Org.sun__max,
    Org.mon__min,
    Org.mon__max,
    Org.tue__min,
    Org.tue__max,
    Org.wed__min,
    Org.wed__max,
    Org.thu__min,
    Org.thu__max,
    Org.fri__min,
    Org.fri__max,
    Org.sat__min,
    Org.sat__max
from
    SLA
        left join
    Categoria ON SLA.Categoria$id = Categoria.id
        left join
    gate.Role as Origem ON SLA.Origem$id = Origem.id
        left join
    gate.Uzer as Solicitante ON SLA.Solicitante$id = Solicitante.id
        left join
    gate.Org ON true
order by precedencia