insert into public.model_type (name) values ('BMW'), ('AUDI'), ('MAZDA'), ('SPRINTER'), ('NINJA');
insert into public.transport_type (name) values ('MOTORBIKE'), ('AUTOMOBILE'), ('MINIBUS');
insert into public.client (first_name, last_name) values ('Igor', 'Sorokin'), ('Viktor', 'Ivanov'), ('Elizaveta', 'Korv');

insert into public.transport (model_type_id, transport_type_id, client_id) values 
	(1, 2, 1),
	(1, 2, 3),
	(2, 2, 1),
	(3, 2, 2),
	(5, 1, 1);