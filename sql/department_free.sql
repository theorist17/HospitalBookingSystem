use hospital;

DELIMITER //
CREATE PROCEDURE department_free(in javaStart varchar(30), in javaEnd varchar(30), in javaDoctorID int, in javaDept varchar(30), OUT dept_free bool )
BEGIN
		declare done, check_work, check_available, check_return, check_pain bool default false;
        declare doctor_id int;
        declare cur1 cursor for select doctorID from doctors where department = javaDept;
        declare continue handler for not found set done = true;
         
        create temporary table dept (id int); 

        open cur1;
                
        repeat 
			fetch cur1 into doctor_id;
            set p_id = (select patinetID from patients where doctorID = doctor_id); 
            set check_work = doctor_work(javaStart, javaEnd, doctor_id);
            set check_available = doctor_available(javaStart, javaEnd, doctor_id);
			set check_pain = (javaStart, javaEnd, p_id);
            
            if check_work and check_available and check_pain then 
				insert into t(id) values (doctor_id);            
			end if;
            
		until done end repeat;

		if (select distinct count(id) from t) >= 2 then 
			set @dept_free = true;
		elseif (select distinct count(id) from t) = 1 then
				if (select distinct count(id) from t) = javaDoctorID then
					set @dept_free = false;
				else
					set @dept_free = true;
				end if;
		else
			set @dept_free = false;
		end if;

         drop temporary table if exists t;

END //
DELIMITER ;
