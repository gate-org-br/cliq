select
    Compartilhamento.id as id,
    Compartilhamento.Chamado$id as 'chamado.id',
    Pessoa.id as 'pessoa.id',
    Pessoa.name as 'pessoa.name',
    Equipe.id as 'equipe.id',
    Equipe.name as 'equipe.name'
from
    Compartilhamento
        left Join
    gate.Uzer as Pessoa ON Compartilhamento.Pessoa$id = Pessoa.id
        left Join
    gate.Role as Equipe ON Compartilhamento.Equipe$id = Equipe.id
where
    Compartilhamento.id = ?