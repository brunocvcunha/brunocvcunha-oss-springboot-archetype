--BCryptPasswordEncoder
INSERT INTO user(login_name, pwd, enabled, email, role) VALUES ('admin', '$2a$10$X32whEAXkb5TdWNkxql8NuXSE56DopSOxc.DEwYFOFj5etsz76mla', TRUE, 'brunocvcunha@gmail.com', 'ADMIN');
INSERT INTO user(login_name, pwd, enabled, email, role) VALUES ('user', '$2a$10$hNNhaPY2wz8UaNwb5gmmnenvdGQEBvoEc062qIg7mJf/RdCHiNpfG', TRUE, 'brunocvcunha+user@gmail.com', 'USER');

