create table if not exists tb_drone (
	id int(11) auto_increment,
    serial_number varchar(10),
    latitude double,
    longitude double,
    operando bit(1),
    primary key (id)
);

create table if not exists tb_entrega (
	id int(11) auto_increment,
    bairro varchar(255),
    cep varchar(9),
    endereco varchar(255),
    num int(11),
    destinatario varchar(255),
    data varchar(21),
    status bit(1),
    drone_id int(11),
    primary key (id),
    foreign key (drone_id) references tb_drone(id)
);

--INSERT INTO tb_drone (id, serial_number, latitude, longitude, operando)
--VALUES (1, "A100", -10.12312, -20.23423, false);
--
--INSERT INTO tb_drone (id, serial_number, latitude, longitude, operando)
--VALUES (2, "A200", -10.12312, -20.23423, false);
