use hospital;

DELIMITER //
CREATE PROCEDURE free_id(in javaStart varchar(30), in javaEnd varchar(30), in javaDoctorID int, in javaDept varchar(30), OUT id_num int )
BEGIN
		declare done, check_work, check_available bool default false;
        declare f_id int;
        declare cur1 cursor for select doctorID from doctors where department = javaDept;
        declare continue handler for not found set done = true;
         
        create temporary table t (id int); 
              
        open cur1;
					
        repeat 
			fetch cur1 into f_id;
            
            set check_work = doctor_work(javaStart, javaEnd, f_id);
            set check_available = doctor_available(javaStart, javaEnd, f_id);
            set check_pain = pain_doctor(javaStart, javaEnd, f_id);
            
            if check_work and check_available and check_pain then 
					insert into t(id) values (f_id);
            
			end if;
            
		until done end repeat;
        

		 set @id_num = (select distinct id from t where id != javaDoctorID order by rand() limit 1);
         
         drop temporary table if exists t;

END //
DELIMITER ;
