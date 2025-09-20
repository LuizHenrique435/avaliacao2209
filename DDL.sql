create table usr_usuario (
  usr_id bigint generated always as identity,
  usr_nome varchar(20) not null,
  usr_senha varchar(150) not null,
  primary key (usr_id),
  unique (usr_nome)
);

create table aut_autorizacao (
  aut_id bigint generated always as identity,
  aut_nome varchar(20) not null,
  primary key (aut_id),
  unique (aut_nome)
);

create table uau_usuario_autorizacao (
  usr_id bigint not null,
  aut_id bigint not null,
  primary key (usr_id, aut_id),
  foreign key (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  foreign key (aut_id) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

create table ant_anotacao (
  ant_id bigint generated always as identity,
  ant_texto varchar(256) not null,
  ant_data_hora timestamp not null,
  ant_usr_id bigint not null,
  primary key (ant_id),
  foreign key (ant_usr_id) references usr_usuario(usr_id)
);

create table tra_trabalho (
  tra_id bigint generated always as identity,
  tra_titulo varchar(100) not null unique,
  tra_data_hora_entrega timestamp not null,
  tra_descricao varchar(200),
  tra_usr_id bigint not null,
  tra_nota int,
  foreign key (tra_usr_id) references usr_usuario (usr_id) on delete restrict on update cascade
);

create table ace_acesso (
  ace_id bigint generated always as identity,
  ace_tipo varchar(100) not null,
  ace_origem varchar(100),
  ace_data_hora_inicio timestamp not null,
  ace_tempo_acesso int,
  ace_usuario bigint not null,
  foreign key (ace_usuario) references usr_usuario (usr_id) on delete restrict on update cascade
);

create table con_configuracao (
  con_id bigint generated always as identity,
  con_responsavel bigint not null,
  con_data_hora timestamp not null,
  con_data_hora_fim timestamp,
  con_parametro varchar(100) not null,
  con_valor float,
  foreign key (con_responsavel) references usr_usuario (usr_id) on delete restrict on update cascade
);

create table fun_funcionalidade (
  fun_id bigint generated always as identity,
  fun_nome varchar not null,
  fun_data_ativacao date not null,
  fun_autorizacao bigint not null,
  fun_url varchar(100),
  fun_limite_acessos int,
  unique(fun_nome),
  foreign key (fun_autorizacao) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

create table his_historico (
  his_id bigint generated always as identity,
  his_aut_nome_antigo varchar(20) not null,
  his_aut_nome_novo varchar(20) not null,
  his_data date not null,
  his_autorizacao bigint not null,
  his_alcance float,
  foreign key (his_autorizacao) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

insert into usr_usuario (usr_nome, usr_senha)
  values ('admin', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C');
insert into aut_autorizacao (aut_nome)
  values ('ROLE_ADMIN');
insert into uau_usuario_autorizacao (usr_id, aut_id) 
  values (1, 1);
insert into ant_anotacao(ant_texto, ant_data_hora, ant_usr_id)
  values('Meu novo projeto', '2023-08-01 19:10', 1);
insert into tra_trabalho (tra_titulo, tra_data_hora_entrega, tra_nota, tra_usr_id)
  values ('Teste 1', current_timestamp, 6, 1),
         ('Teste 2', current_timestamp, null, 1);
insert into ace_acesso (ace_tipo, ace_data_hora_inicio, ace_tempo_acesso, ace_usuario)
  values ('Setup', '2025-09-03 11:00:00', 1, 1),
         ('Config', current_timestamp, null, 1);
insert into con_configuracao (con_responsavel, con_data_hora, con_parametro, con_valor)
  values (1, '2025-09-03 11:30:00', 'taxa', 20.7),
         (1, current_timestamp, 'rotação', null);
insert into fun_funcionalidade (fun_nome, fun_data_ativacao, fun_limite_acessos, fun_autorizacao)
  values ('Clientes', '2025-09-03', null, 1),
         ('Permissões', current_date, 3, 1);
insert into his_historico (his_aut_nome_antigo, his_aut_nome_novo, his_data, his_autorizacao, his_alcance)
  values ('ROLE_ROOT', 'ROLE_ADMIN', '2025-09-03', 1, 0.73),
         ('ROLE_USER', 'ROLE_GERAL', current_date, 1, null);

create user spring with password 'pass123';

grant update, delete, insert, select on all tables in schema public to spring;
