# Sistema-Gerenciador-de-Tarefas
Sistema para incluir,atualizar e excluir tarefas em uma tabela.

O programa implementa uma pagina dinâmica web, um sistema para incluir, 
atualizar e excluir tarefas, além de buscar por filtros,
tarefas que forem cadastradas.

Foram implementados seguinte projeto, conforme as orientações,
os seguintes itens do documento:
a) e b)

Para execução, necessita-se da criação de um database no PostgreSQL
nomeado "gerenciador_tarefas" com uma tabela "tarefa" (script no final deste arquivo)
Atenção as configurações para sincronia com o banco de dados
presentes no arquivo "context.xml" localizado na pasta META-INF

Na IDE, dentro da pasta do projeto, click-direito no arquivo index.html > run as > Run on server. (Apache Tomcat) 

Programas auxliares
Eclipse IDE 2020-06
apache-tomcat-9.0.52
jdk-14.0.2
postgresql-13.4-1

Bibliotecas:
javax.faces-2.2.8
postgresql-42.2.23 (conector do banco de dados)

-------------------------------------------------
-- Table: public.tarefa

-- DROP TABLE public.tarefa;

CREATE TABLE IF NOT EXISTS public.tarefa
(
    deadline text COLLATE pg_catalog."default",
    descricao text COLLATE pg_catalog."default",
    id integer NOT NULL DEFAULT nextval('tarefa_id_seq'::regclass),
    prioridade text COLLATE pg_catalog."default",
    responsavel text COLLATE pg_catalog."default",
    titulo text COLLATE pg_catalog."default",
    situacao text COLLATE pg_catalog."default",
    CONSTRAINT tarefa_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tarefa
    OWNER to postgres;
