insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$qbNvLoUOuH21iI8h59Cmtu7p2STXkExY0Aw1kY37V2QzaZsP4gk.W',
(select id from authorities where authority = 'ROLE_ADMIN'));