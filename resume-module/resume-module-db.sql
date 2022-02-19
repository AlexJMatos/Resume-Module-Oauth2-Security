-------------------------- RESUME MODULE DATABASE ----------------------------------

-------------------------- DROP TABLES IF EXISTS ------------------------------------
DROP TABLE IF EXISTS public.technologies;
DROP TABLE IF EXISTS public.skills;
DROP TABLE IF EXISTS public.education;
DROP TABLE IF EXISTS public.work_experience;
DROP TABLE IF EXISTS public.resumes;
DROP TABLE IF EXISTS public.address;

-------------------------- END OF DROP TABLES IF EXISTS -----------------------------

--------------------------- CREATE TABLES -------------------------------------------
-- Table: public.address
CREATE TABLE IF NOT EXISTS public.address
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
	street text COLLATE pg_catalog."default" NOT NULL,
    city text COLLATE pg_catalog."default" NOT NULL,
    state text COLLATE pg_catalog."default" NOT NULL,
    country text COLLATE pg_catalog."default" NOT NULL,
    zip_code text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

-- Table: public.resumes
CREATE TABLE IF NOT EXISTS public.resumes
(
    id bigint NOT NULL,
    first_name text COLLATE pg_catalog."default" NOT NULL,
    last_name text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    phone_number text COLLATE pg_catalog."default" NOT NULL,
    address_id bigint NOT NULL,
    CONSTRAINT resumes_pkey PRIMARY KEY (id),
    CONSTRAINT resumes_address_id_fkey FOREIGN KEY (address_id)
        REFERENCES public.address (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

-- Table: public.technologies
CREATE TABLE IF NOT EXISTS public.technologies
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    technology text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    technology_employee_id bigint NOT NULL,
    CONSTRAINT technologies_pkey PRIMARY KEY (id),
    CONSTRAINT technologies_employee_id_fkey FOREIGN KEY (technology_employee_id)
        REFERENCES public.resumes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: public.skills
CREATE TABLE IF NOT EXISTS public.skills
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    skill text COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
	skill_employee_id bigint NOT NULL,
    CONSTRAINT skills_pkey PRIMARY KEY (id),
	CONSTRAINT skills_employee_id_fkey FOREIGN KEY (skill_employee_id)
        REFERENCES public.resumes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: public.education

CREATE TABLE IF NOT EXISTS public.education
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    degree text COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    from_date date,
    until_date date,
	education_employee_id bigint NOT NULL,
    CONSTRAINT education_pkey PRIMARY KEY (id),
	CONSTRAINT education_employee_id_fkey FOREIGN KEY (education_employee_id)
        REFERENCES public.resumes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: public.work_experience
CREATE TABLE IF NOT EXISTS public.work_experience
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    "position" text COLLATE pg_catalog."default" NOT NULL,
    company text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    from_date date,
    until_date date,
	work_employee_id bigint NOT NULL,
    CONSTRAINT work_experience_pkey PRIMARY KEY (id),
	CONSTRAINT work_experience_employee_id_fkey FOREIGN KEY (work_employee_id)
        REFERENCES public.resumes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--------------------------- END OF CREATE TABLES -------------------------------------------

-------------------------- OWNER ---------------------------------------------
ALTER TABLE IF EXISTS public.resumes
    OWNER to resume_module_user;
	
ALTER TABLE IF EXISTS public.address
    OWNER to resume_module_user;
	
ALTER TABLE IF EXISTS public.technologies
    OWNER to resume_module_user;
	
ALTER TABLE IF EXISTS public.skills
    OWNER to resume_module_user;
	
ALTER TABLE IF EXISTS public.education
    OWNER to resume_module_user;
	
ALTER TABLE IF EXISTS public.work_experience
    OWNER to resume_module_user;
	
-------------------------- END OF OWNER --------------------------------------

-------------------------- INSERT DATA INTO TABLES ---------------------------
-- Table: public.address
INSERT INTO public.address(street, city, state, country, zip_code)
	VALUES ('Arroyo Flor Marina 7 Edificio 9, Esc. 223', 'Comitán de Dominguez', 'Baja California Norte', 'Mexico', '53632'),
		   ('Partida Miguel Ángel de Quevedo 8 Edificio 1', 'Ixtapaluca', 'Quintana Roo', 'Mexico', '27177'),
		   ('Extrarradio Flor Marina 7, Puerta 241', 'Salamanca', 'Tabasco', 'Mexico', '28245'),
		   ('Solar Amores, 1, Esc. 102', 'San Luis Río Colorado', 'Colima', 'Mexico', '88348'),
		   ('Mercado Eje Central, 93, Esc. 483', 'Ciudad Valles', 'Puebla', 'Mexico', '06080'),
		   ('Rua Naranjo, 9 Esc. 864, Puerta 059', 'Xalapa', 'Veracruz', 'Mexico', '79336'),
		   ('Extramuros Eje 5, 34 Esc. 913, Esc. 270', 'General Escobedo', 'Chiapas', 'Mexico', '83570'),
		   ('Quinta Zacatlán 3, Edificio 6', 'Oaxaca', 'Oaxaca', 'Mexico', '79330'),
		   ('Monte Cecilia Yago 788 Edificio 2, Puerta 408', 'El Salto', 'Durango', 'Mexico', '65881'),
		   ('Riera Raquel Feliciano, 4 Puerta 246', 'Villa Nicolás Romero', 'Hidalgo', 'Mexico', '40680');
		   
-- Table: public.resumes
INSERT INTO public.resumes(id, first_name, last_name, email, phone_number, address_id)
	VALUES (1, 'Edilberto', 'Becerra', 'edi.becerra@gmail.com', '735-353-5508', 8),
		   (2, 'Esthela', 'Camarena', 'esthela.camarena@outlook.com', '442-213-5258', 6),
		   (3, 'Rufino', 'Mancera', 'rufiman@gmail.com', '555-553-1928', 4),
		   (4, 'Amanda', 'Segundo', 'asegundo@outlook.com', '999-985-8111', 2),
		   (5, 'Norberta', 'Canul', 'canul.nor@gmail.com', '667-760-2480', 10),
		   (6, 'Erik', 'Cordero', 'e.cordero@outlook.com', '999-984-0008', 3),
		   (7, 'Idalia', 'Lagunes', 'ida.lagunes@gmail.com', '951-573-0350', 5),
		   (8, 'Deyanira', 'Meneses', 'meneses.deyanira@outlook.com', '656-612-1870', 7),
		   (9, 'Reynalda', 'Alvaro', 'alvaro.reyna@gmail.com', '999-944-1731', 1),
		   (10, 'Aurelio', 'Camargo', 'camargo.aurelio@outlook.com', '777-312-1424', 9);
		   
-- Table: public.technologies
INSERT INTO public.technologies(technology, description, technology_employee_id)
	VALUES ('Python', 'Knowledge of Python for Machine Learning', 1),
		   ('C#', 'Knowledge of C# for Web Enterprise Development', 2),
		   ('Java', 'Knowledge of Java for Web Enterprise Development', 3),
		   ('Javascript', 'Knowledge of Javascript for Front-end Development', 4),
		   ('C++', 'Knowledge of C++ for Game Development', 5),
		   ('C', 'Knowledge of C for Embedded Systems Development', 6),
		   ('PHP', 'Knowledge of PHP for Back-end Development', 7),
		   ('R', 'Knowledge of R for Data Science', 8),
		   ('Kotlin', 'Knowledge of Kotlin for Android Mobile Development', 9),
		   ('Swift', 'Knowledge of Swift for iOS Mobile Development', 10),
		   ('TensorFlow', 'Python library for high-performance numerical computations', 1),
		   ('.NET Core', 'C# framework used build software applications for Windows, Linux, and MacOS', 2),
		   ('Spring', 'Open Source framework used to develop Java applications with very ease and rapid pace', 3),
		   ('Node.js', 'Open source, cross-platform, JavaScript runtime environment', 4),
		   ('Unreal Engine', 'Unreal Engine is a C++ game development engine', 5),
		   ('STM32', 'STM32 is a brand of microcontrollers based on ARM architecture', 6),
		   ('Laravel', 'Laravel is a web application framework with expressive, elegant syntax', 7),
		   ('Dplyr', 'Dplyr is mainly used for data manipulation in R', 8),
		   ('Firebase', 'Platform developed by Google for creating mobile and web applications', 9),
		   ('Flutter', 'Open source framework by Google for building beautiful, natively compiled, multi-platform applications from a single codebase', 10);
	
-- Table: public.skills
INSERT INTO public.skills(skill, type, description, skill_employee_id)
	VALUES ('Creativity', 'SOFT_SKILL', 'Ability to find new solutions', 1),
		   ('Leadership', 'SOFT_SKILL', 'Ability to lead teams to achieve goals', 2),
		   ('Teamwork', 'SOFT_SKILL', 'Ability to colaborate and exchange ideas with team mates', 3),
		   ('Critical Thinking', 'SOFT_SKILL', 'Ability to analyze problems in a logical and critical way', 4),
		   ('Time management', 'SOFT_SKILL', 'Ability to manage time to achieve deliverables', 5),
		   ('Communication', 'SOFT_SKILL', 'Ability to communicate common problems, solutions and interactions', 6),
		   ('Stress management', 'SOFT_SKILL', 'Ability to handle stress and find mental clarity', 7),
		   ('Flexibility', 'SOFT_SKILL', 'Ability to adapt to new situations fast and efficient', 8),
		   ('Troubleshooting', 'SOFT_SKILL', 'Ability to solve errors and exceptions when presented', 9),
		   ('Innovation', 'SOFT_SKILL', 'Ability to find new ways to develop products', 10),
		   ('Project management', 'HARD_SKILL', 'Ability to manage project in a structural way', 1),
		   ('Cloud and distributed computing', 'HARD_SKILL', 'Ability to manage cloud and computing servers', 2),
		   ('User Interface Design', 'HARD_SKILL', 'Ability to design new and common user interfaces', 3),
		   ('Database management', 'HARD_SKILL', 'Ability to manage data and database for enterprise', 4),
		   ('English language', 'HARD_SKILL', 'Ability to communicate in english written and verbally', 5),
		   ('Graphic design', 'HARD_SKILL', 'Ability to design logos and comming ideas from clients', 6),
		   ('Video editing', 'HARD_SKILL', 'Ability to edit videos for enterprise presentations', 7),
		   ('Japanese Language', 'HARD_SKILL', 'Mid level ability to communicate in japanese verbally', 8),
		   ('Data analysis', 'HARD_SKILL', 'Ability to analyze data to find insights', 9),
		   ('Data Engineering', 'HARD_SKILL', 'Ability to analyze date to find patterns', 10);
	
-- Table: public.education
INSERT INTO public.education(degree, type, from_date, until_date, education_employee_id)
	VALUES ('Computer Science', 'Bachelor', '2015-08-10', '2019-12-15', 1),
		   ('Mechatronics Engineering', 'Bachelor', '2015-08-10', '2020-06-13', 2),
		   ('Software Engineering', 'Bachelor', '2016-08-10', '2020-12-15', 3),
		   ('Informatics', 'Bachelor', '2010-09-10', '2014-12-02', 4),
		   ('Mathematics', 'Bachelor', '2012-08-01', '2016-09-21', 5),
		   ('Artificial Intelligence', 'Master', '2014-01-06', '2016-05-29', 6),
		   ('Data Science and Machine Learning', 'Master', '2017-01-10', '2019-12-21', 7),
		   ('Infomation Technology Management', 'Doctoral', '2019-01-10', NULL, 8),
		   ('Quantum Computing', 'Doctoral', '2015-08-10', '2019-12-15', 9),
		   ('Electrical and Computer Engineering', 'Doctoral', '2016-01-05', '2020-07-20', 10);
	
-- Table: public.work-experience
INSERT INTO public.work_experience("position", company, description, from_date, until_date, work_employee_id)
	VALUES ('Data Scientist', 'Softek', 'Analysing data from the logistics of the city', '2018-10-10' ,'2021-09-10', 1),
		   ('.NET Developer', 'Accenture', 'Development of web apps for banks', '2018-03-04', '2020-12-15', 2),
		   ('Java Developer', 'Encora', 'Development of web apps for insurance', '2016-02-15', '2020-05-26', 3),
		   ('Front end developer', 'Ksquare Group', 'Development of user interfaces for manufacturing company', '2018-04-10', '2021-05-21', 4),
		   ('Game developer', 'Epic Games', 'Development of VR Games', '2012-04-22', '2016-10-14', 5),
		   ('Embedded Systems Developer', 'Intel', 'Development of embedded systems for automotive', '2013-11-12', '2018-10-25', 6),
		   ('Project manager', 'Google', 'Management of the software development projects', '2011-09-10', '2019-12-11', 7),
		   ('Business Analyst', 'Wizeline', 'Analyze future business decisions using data analysis techniques', '2012-10-10', '2016-11-21', 8),
		   ('Android Mid Level Developer', 'EPAM Systems', 'Create manufacturing based android apps for clients', '2017-07-15', '2019-11-21', 9),
		   ('iOS Senior Developer', 'Apple', 'Create iOS based apps for iPhone and Apple Watch', '2015-01-30', '2018-01-01', 10);
	
-------------------------- END INSERT DATA INTO TABLES --------------------