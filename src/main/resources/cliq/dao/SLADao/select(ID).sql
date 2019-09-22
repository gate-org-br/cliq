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
    Org.sun$time1 as 'sun:time1',
    Org.sun$time2 as 'sun:time2',
    Org.mon$time1 as 'mon:time1',
    Org.mon$time2 as 'mon:time2',
    Org.tue$time1 as 'tue:time1',
    Org.tue$time2 as 'tue:time2',
    Org.wed$time1 as 'wed:time1',
    Org.wed$time2 as 'wed:time2',
    Org.thu$time1 as 'thu:time1',
    Org.thu$time2 as 'thu:time2',
    Org.fri$time1 as 'fri:time1',
    Org.fri$time2 as 'fri:time2',
    Org.sat$time1 as 'sat:time1',
    Org.sat$time2 as 'sat:time2'
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
where
    SLA.id = ?
order by precedencia