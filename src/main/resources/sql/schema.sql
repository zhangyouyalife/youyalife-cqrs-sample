CREATE TABLE command_instance (
	id varchar(50) primary key,
	type varchar(50) not null,
	status varchar(10) not null,
	parameter varchar(4000),
	result varchar(4000),
	create_at timestamp,
	finish_at timestamp
);
