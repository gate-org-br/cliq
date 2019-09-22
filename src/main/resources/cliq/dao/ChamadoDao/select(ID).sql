select
	Chamado.id as id,
	Chamado.nivel as nivel,
        Chamado.alteracao as alteracao,
	Chamado.atendimento as atendimento,
	Chamado.nota as nota,
	Chamado.formulario as formulario,
	Chamado.situacao as situacao,
	Chamado.sigiloso as sigiloso,
	Chamado.projeto as projeto,
	Chamado.retomada as retomada,
	Chamado.documentacao as documentacao,
	Chamado.data as data,
	Chamado.prazoResposta as prazoResposta,
	Chamado.prazoSolucao as prazoSolucao,
	Chamado.titulo as titulo,
	Chamado.descricao as descricao,
	Chamado.resposta,
	Chamado.solucao,
	Chamado.notificados as notificados,
	Chamado.prioridade as prioridade,
	Chamado.complexidade as complexidade,
	Chamado.checklist as checklist,
        Chamado.pendencia as pendencia,
	Chamado.Anexo$id as 'anexo.id',
	Tipo.id as 'tipo.id',
	Tipo.nome as 'tipo.nome',
	Categoria.id as 'categoria.id',
	Categoria.nome as 'categoria.nome',
	Categoria.feedback as 'categoria.feedback',
	Categoria.temporaria as 'categoria.temporaria',
	Categoria.conclusoes as 'categoria.conclusoes',
        Categoria.Anexo$id as 'categoria.anexo.id',
	Categoria.avaliacao as 'categoria.avaliacao',
	Chamado.Chamado$id as 'chamado.id',
	Chamado.Evento$id as 'ultimoEvento.id',
	Contato.id as 'contato.id',
	Contato.nome as 'contato.nome',
	Contato.telefone as 'contato.telefone',
	Contato.celular as 'contato.celular',
	Contato.email as 'contato.email',
	Origem.id as 'origem.id',
	Origem.name as 'origem.name',
	Solicitante.id as 'solicitante.id',
	Solicitante.userID as 'solicitante.userId',
	Solicitante.name as 'solicitante.name',
	Solicitante.email as 'solicitante.email',
	Localizacao.id as 'localizacao.id',
	Localizacao.name as 'localizacao.name',
	Localizacao.email as 'localizacao.email',
	Atendente.id as 'atendente.id',
	Atendente.name as 'atendente.name',
	Atendente.email as 'atendente.email',
	Evento.id as 'evento.id',
	Evento.data as 'evento.data',
	Evento.tipo as 'evento.tipo',
        Evento.alteracao as 'evento.alteracao',
	Evento.status as 'evento.status',
	Evento$Uzer.id as 'evento.user.id',
	Evento$Uzer.name as 'evento.user.name',
	Evento$Uzer.email as 'evento.user.email',
	Evento.descricao as 'evento.descricao',
	Evento.observacoes as 'evento.observacoes',
	PessoaAprovadora.id as 'pessoaAprovadora.id',
	PessoaAprovadora.name as 'pessoaAprovadora.name',
	PessoaAprovadora.email as 'pessoaAprovadora.email',
	EquipeAprovadora.id as 'equipeAprovadora.id',
	EquipeAprovadora.name as 'equipeAprovadora.name',
        EquipeAprovadora.email as 'equipeAprovadora.email',
	PessoaHomologadora.id as 'pessoaHomologadora.id',
	PessoaHomologadora.name as 'pessoaHomologadora.name',
	PessoaHomologadora.email as 'pessoaHomologadora.email',
	EquipeHomologadora.id as 'equipeHomologadora.id',
	EquipeHomologadora.name as 'equipeHomologadora.name',
        EquipeHomologadora.email as 'equipeHomologadora.email',
	Evento.Anexo$id as 'evento.anexo.id'
from
	Chamado
left join Categoria on
	Chamado.Categoria$id = Categoria.id
left join Categoria as Tipo on
	Chamado.Tipo$id = Tipo.id
left join gate.Uzer as Solicitante on
	Chamado.Solicitante$id = Solicitante.id
left join gate.Role as Origem on
	Chamado.Origem$id = Origem.id
left join gate.Role as Localizacao on
	Chamado.Localizacao$id = Localizacao.id
left join Evento on
	Chamado.id = Evento.Chamado$id
left join gate.Uzer as Evento$Uzer on
	Evento.Uzer$id = Evento$Uzer.id
left join gate.Uzer as Atendente on
	Chamado.Atendente$id = Atendente.id
left join Contato on
	Chamado.Contato$id = Contato.id
left join gate.Uzer as PessoaAprovadora on
	Chamado.PessoaAprovadora$id = PessoaAprovadora.id
left join gate.Role as EquipeAprovadora on
	Chamado.EquipeAprovadora$id = EquipeAprovadora.id
left join gate.Uzer as PessoaHomologadora on
	Chamado.PessoaHomologadora$id = PessoaHomologadora.id
left join gate.Role as EquipeHomologadora on
	Chamado.EquipeHomologadora$id = EquipeHomologadora.id
where
	(not sigiloso
	or Chamado.Solicitante$id = ?
	or Chamado.PessoaAprovadora$id = ?
	or Chamado.Localizacao$id = ?
	or Chamado.EquipeAprovadora$id = ?)
	and Chamado.id = ?
order by
	Evento.data desc