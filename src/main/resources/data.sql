
/*
INSERT INTO school_year (id, start_date, end_date, school_year) VALUES (1, '2023-09-01', '2024-06-30', '2023-2024');
INSERT INTO school_year (id, start_date, end_date, school_year) VALUES (2, '2024-09-01', '2025-06-30', '2024-2025');
INSERT INTO school_year (id, start_date, end_date, school_year) VALUES (3, '2025-09-01', '2026-06-30', '2025-2026');

INSERT INTO term (id, start_date, end_date, term_name, school_year) VALUES (1, '2023-09-01', '2023-12-20', 'FIRST', 1);
INSERT INTO term (id, start_date, end_date, term_name, school_year) VALUES (2, '2024-01-10', '2024-06-30', 'SECOND', 1);

INSERT INTO user_role (id, role_name) VALUES (1, 'ADMIN');
INSERT INTO user_role (id, role_name) VALUES (2, 'TEACHER');
INSERT INTO user_role (id, role_name) VALUES (3, 'STUDENT');
INSERT INTO user_role (id, role_name) VALUES (4, 'GUARDIAN');

INSERT INTO grade (id, grade, grade_order) VALUES (1, 'I', 1);
INSERT INTO grade (id, grade, grade_order) VALUES (2, 'II', 2);
INSERT INTO grade (id, grade, grade_order) VALUES (3, 'III', 3);
INSERT INTO grade (id, grade, grade_order) VALUES (4, 'IV', 4);
INSERT INTO grade (id, grade, grade_order) VALUES (5, 'V', 5);
INSERT INTO grade (id, grade, grade_order) VALUES (6, 'VI', 6);
INSERT INTO grade (id, grade, grade_order) VALUES (7, 'VII', 7);
INSERT INTO grade (id, grade, grade_order) VALUES (8, 'VIII', 8);

INSERT INTO course (id, course_name, week_units, term, course_grade, version) VALUES (1, 'Course1', 3, 1, 1, 0);
INSERT INTO course (id, course_name, week_units, term, course_grade, version) VALUES (2, 'Course2', 4, 2, 2, 0);
INSERT INTO course (id, course_name, week_units, term, course_grade, version) VALUES (3, 'Course3', 2, 2, 3, 0);

commit;*/