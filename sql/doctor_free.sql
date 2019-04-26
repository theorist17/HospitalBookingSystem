use hospital;

DELIMITER //
CREATE PROCEDURE doctor_free(in javaStart varchar(30), in javaEnd varchar(30), in javaDoctorID int, in javaDept varchar(30), OUT yes_free bool )
BEGIN
		declare done, check_work, check_available, check_pain bool default false;
        declare f_id int;
        declare p_id char(13);
        declare cur1 cursor for select doctorID from doctors where department = javaDept;
        declare continue handler for not found set done = true;
         
        create temporary table t (id int); 

        open cur1;
                
        repeat 
			fetch cur1 into f_id;
            set p_id = (select patinetID from patients where doctorID = f_id); 
            set check_work = doctor_work(javaStart, javaEnd, f_id);
            set check_available = doctor_available(javaStart, javaEnd, f_id);
			set check_pain = pain_doctor(javaStart, javaEnd, p_id);
            
            if check_work and check_available and check_pain then 
				insert into t(id) values (f_id);

			end if;
            
		until done end repeat;
        
		if (select distinct count(id) from t) >= 2 then 
			set @yes_free = true;
		elseif (select distinct count(id) from t) = 1 then
				if (select distinct id from t) = javaDoctorID then
					set @yes_free = false;
				else
					set @yes_free = true;
				end if;
		else
			set @yes_free = false;
		end if;
		
         drop temporary table if exists t;

END //
DELIMITER ;
