alter table pacientes add activo tinyint;
update pacientes set activo = 1;
alter table pacientes modify activo tinyint not null;