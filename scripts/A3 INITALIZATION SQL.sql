create table students 
	(student_id	SERIAL PRIMARY KEY,
	 first_name		varchar(255) NOT NULL, 
	 last_name		varchar(255) NOT NULL, 
	 email		varchar(255) UNIQUE NOT NULL, 
	 enrollment_date DATE
	);

