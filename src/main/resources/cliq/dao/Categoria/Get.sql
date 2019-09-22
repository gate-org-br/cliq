select
    Role.id as id,
    Role.Role$id as role$id,
    Role.name as name,
    Categoria.avaliacao as 'categoria.avaliacao',
    Categoria.feedback as 'categoria.feedback',
    Categoria.id as 'categoria.id',
    Categoria.nivel as nivel,
    Categoria.Parent$id as 'categoria.parent.id',
    Categoria.nome as 'categoria.nome',
    Categoria.visibilidade as 'categoria.visibilidade',
    Categoria.triagem as 'categoria.triagem',
    Categoria.temporaria as 'categoria.temporaria',
    Categoria.sigilosa as 'categoria.sigilosa',
    Categoria.projeto as 'categoria.projeto',
    PessoaAprovadora.id as 'categoria.pessoaAprovadora.id',
    PessoaAprovadora.name as 'categoria.pessoaAprovadora.name',
    EquipeAprovadora.id as 'categoria.equipeAprovadora.id',
    EquipeAprovadora.name as 'categoria.equipeAprovadora.name',
    Categoria.duracao as 'categoria.duracao',
    Atribuir.id as 'categoria.atribuir.id',
    Atribuir.name as 'categoria.atribuir.name',
    Encaminhar.id as 'categoria.encaminhar.id',
    Encaminhar.name as 'categoria.encaminhar.name'
from
    gate.Role
        left join
    Categoria ON Role.id = Categoria.Role$id
        left join
    gate.Uzer as PessoaAprovadora ON Categoria.PessoaAprovadora$id = PessoaAprovadora.id
        left join
    gate.Role as EquipeAprovadora ON Categoria.EquipeAprovadora$id = EquipeAprovadora.id
        left join
    gate.Uzer as Atribuir ON Categoria.Atribuir$id = Atribuir.id
        left join
    gate.Role as Encaminhar ON Categoria.Encaminhar$id = Encaminhar.id