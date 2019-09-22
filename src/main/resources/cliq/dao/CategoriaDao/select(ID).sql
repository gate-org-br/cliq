select
    Categoria.id as id,
    Role.id as 'role.id',
    Role.name as 'role.name',
    Role.email as 'role.email',
    Parent.id as 'parent.id',
    Parent.nome as 'parent.nome',
    PessoaAprovadora.id as 'pessoaAprovadora.id',
    PessoaAprovadora.name as 'pessoaAprovadora.name',
    EquipeAprovadora.id as 'equipeAprovadora.id',
    EquipeAprovadora.name as 'equipeAprovadora.name',
    PessoaHomologadora.id as 'pessoaHomologadora.id',
    PessoaHomologadora.name as 'pessoaHomologadora.name',
    EquipeHomologadora.id as 'equipeHomologadora.id',
    EquipeHomologadora.name as 'equipeHomologadora.name',
    Categoria.Anexo$id as 'anexo.id',
    Categoria.visibilidade as visibilidade,
    Categoria.triagem as triagem,
    Categoria.temporaria as 'temporaria',
    Categoria.sigilosa as sigilosa,
    Categoria.nome as nome,
    Categoria.campos as campos,
    Categoria.formulario as formulario,
    Categoria.descricao as descricao,
    Categoria.prioridade as prioridade,
    Categoria.complexidade as complexidade,
    Categoria.checklist as checklist,
    Categoria.nivel as nivel,
    Categoria.projeto as projeto,
    Categoria.avaliacao as avaliacao,
    Categoria.feedback as feedback,
    Categoria.duracao as duracao,
    Categoria.conclusoes as conclusoes,
    Categoria.atalho as atalho,
    Atribuir.id as 'atribuir.id',
    Atribuir.name as 'atribuir.name',
    Atribuir.Role$id as 'atribuir.role.id',
    Encaminhar.id as 'encaminhar.id',
    Encaminhar.name as 'encaminhar.name'
from
    Categoria
        left join
    Role ON Categoria.Role$id = Role.id
        left join
    Categoria as Parent ON Categoria.Parent$id = Parent.id
        left join
    gate.Uzer as PessoaAprovadora ON Categoria.PessoaAprovadora$id = PessoaAprovadora.id
        left join
    gate.Role as EquipeAprovadora ON Categoria.EquipeAprovadora$id = EquipeAprovadora.id
        left join
    gate.Uzer as PessoaHomologadora ON Categoria.PessoaHomologadora$id = PessoaHomologadora.id
        left join
    gate.Role as EquipeHomologadora ON Categoria.EquipeHomologadora$id = EquipeHomologadora.id
        left join
    gate.Uzer as Atribuir ON Categoria.Atribuir$id = Atribuir.id
        left join
    gate.Role as Encaminhar ON Categoria.Encaminhar$id = Encaminhar.id