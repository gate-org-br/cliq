SELECT 
    Categoria.id AS id,
    Role.id AS 'role.id',
    Role.name AS 'role.name',
    Role.email AS 'role.email',
    Parent.id AS 'parent.id',
    Parent.nome AS 'parent.nome',
    PessoaAprovadora.id AS 'pessoaAprovadora.id',
    PessoaAprovadora.name AS 'pessoaAprovadora.name',
    EquipeAprovadora.id AS 'equipeAprovadora.id',
    EquipeAprovadora.name AS 'equipeAprovadora.name',
    PessoaHomologadora.id AS 'pessoaHomologadora.id',
    PessoaHomologadora.name AS 'pessoaHomologadora.name',
    EquipeHomologadora.id AS 'equipeHomologadora.id',
    EquipeHomologadora.name AS 'equipeHomologadora.name',
    Categoria.Anexo$id AS 'anexo.id',
    Categoria.visibilidade AS visibilidade,
    Categoria.triagem AS triagem,
    Categoria.temporaria AS 'temporaria',
    Categoria.sigilosa AS sigilosa,
    Categoria.nome AS nome,
    Categoria.campos AS campos,
    Categoria.formulario AS formulario,
    Categoria.descricao AS descricao,
    Categoria.prioridade AS prioridade,
    Categoria.complexidade AS complexidade,
    Categoria.checklist AS checklist,
    Categoria.nivel AS nivel,
    Categoria.projeto AS projeto,
    Categoria.avaliacao AS avaliacao,
    Categoria.feedback AS feedback,
    Categoria.duracao AS duracao,
    Categoria.conclusoes AS conclusoes,
    Categoria.atalho AS atalho,
    Atribuir.id AS 'atribuir.id',
    Atribuir.name AS 'atribuir.name',
    Atribuir.Role$id AS 'atribuir.role.id',
    Encaminhar.id AS 'encaminhar.id',
    Encaminhar.name AS 'encaminhar.name'
FROM
    Categoria
        LEFT JOIN
    Role ON Categoria.Role$id = Role.id
        LEFT JOIN
    Categoria AS Parent ON Categoria.Parent$id = Parent.id
        LEFT JOIN
    gate.Uzer AS PessoaAprovadora ON Categoria.PessoaAprovadora$id = PessoaAprovadora.id
        LEFT JOIN
    gate.Role AS EquipeAprovadora ON Categoria.EquipeAprovadora$id = EquipeAprovadora.id
        LEFT JOIN
    gate.Uzer AS PessoaHomologadora ON Categoria.PessoaHomologadora$id = PessoaHomologadora.id
        LEFT JOIN
    gate.Role AS EquipeHomologadora ON Categoria.EquipeHomologadora$id = EquipeHomologadora.id
        LEFT JOIN
    gate.Uzer AS Atribuir ON Categoria.Atribuir$id = Atribuir.id
        LEFT JOIN
    gate.Role AS Encaminhar ON Categoria.Encaminhar$id = Encaminhar.id
where
    Role.id = ?