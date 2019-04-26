use hospital;

DELIMITER //
CREATE PROCEDURE doctor_available(in javaStart varchar(30), in javaEnd varchar(30), in javaDoctorID int, OUT yes_available bool )
BEGIN
		declare done bool default false;
        declare  a_doctorID int; 
        declare a_start_time, a_end_time varchar(30);
        declare cur1 cursor for select doctorID, timeStart, timeEnd from appointments where doctorID = javaDoctorID;
        declare continue handler for not found set done = true;
                
        open cur1;
                
        repeat 
			fetch cur1 into a_doctorID, a_start_time, a_end_time;
            
			if a_start_time <= javastart and a_end_time >= javaEnd then
					set @yes_available = false;
                    set done = true;
			elseif  a_start_time <= javaStart and a_end_time > javaStart then
					set @yes_available = false;
                    set done = true;
			elseif  a_start_time = javaStart and a_end_time = javaStart then
					set @yes_available = false;
                    set done = true;            
			elseif  a_start_time < javaEnd and a_end_time >= javaEnd then
					set @yes_available = false;
                    set done = true;
			elseif a_start_time >= javaStart and a_end_time <= javaEnd then
					set @yes_available = false;
                    set done = true;
			end if;
            
		until done end repeat;
        
		set @yes_available = false;        

END //
DELIMITER ;
