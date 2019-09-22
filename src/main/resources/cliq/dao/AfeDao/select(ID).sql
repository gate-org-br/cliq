select 
    Afe.id as 'id',
    Afe.nome as 'nome',
    Afe.Categoria$id as 'categoria.id',
    Afe.inicio as 'inicio',
    Afe.periodo as 'periodo',
    Afe.Origem$id as 'origem.id',
    Afe.Atendente$id as 'atendente.id',
    Afe.descricao as 'descricao'
from
    Afe
where
    Afe.id = ?