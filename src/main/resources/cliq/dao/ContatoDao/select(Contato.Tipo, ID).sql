select
    *
from
    (select
        Uzer.id as id,
            'USUARIO' as tipo,
            Role.id as role$id,
            Role.name as role$name,
            'PUBLICA' as visibilidade,
            Uzer.name as nome,
            Uzer.phone as telefone,
            Uzer.cellPhone as celular,
            Uzer.email as email,
            Uzer.details as comentarios
    from
        gate.Uzer
    join gate.Role ON Uzer.Role$id = Role.id union select
        Contato.id as id,
            Contato.tipo as tipo,
            Role.id as role$id,
            Role.name as role$name,
            Contato.visibilidade as visibilidade,
            Contato.nome as nome,
            Contato.telefone as telefone,
            Contato.celular as celular,
            Contato.email as email,
            Contato.comentarios as comentarios
    from
        Contato
    join gate.Role ON Contato.Role$id = Role.id) as Contatos
    where tipo = ? and id = ?