select
    id, nome, prioridade, complexidade, nivel, checklist
from
    Categoria
where
    Role$id = ? and nome = ?