use hospital;

DELIMITER //
CREATE PROCEDURE my_list(in javaStart varchar(30), in javaEnd varchar(30), in myID int, in myDept varchar(30), OUT yes_list bool )
BEGIN
		declare done, check_posible bool default false;
        declare p_id char(13);
        declare check_dept, change_id int;
        declare p_startTime, p_endTime varchar(30);
        declare cur1 cursor for select patientID, timeStart, timeEnd, onDpet from appointments where doctorID = myID;
        declare continue handler for not found set done = true;
         
        create temporary table inculdeTable (include_id int, include_startTime varchar(30), include_EndTime varchar(30), free_num int); 
              
        open cur1;
                
        repeat 
			fetch cur1 into p_id, p_startTime, p_endTime, check_dept;
            
			if p_startTime >= javaStart and p_endTime <= javaEnd then
					if check_dept = 0 then 
						set check_posible = false;
						set done = true;
					else
						if (doctor_free(p_startTime, p_endTime, p_id, p_dept)) then
							set change_id = free_id(p_startTime, p_endTime, p_id, p_dept);
							insert into includeTable(include_id, include_startTime, include_EndTime, free_num) values (p_id, p_startTime, p_endTime, change_id);
							set check_posible = true;
						else 
							set check_posible = false;
							set done = true;
						end if;
					end if;
		
			end if;
            
		until done end repeat;
        
        if check_posible then
			update appointments a join includeTable i set a.doctorID = i.change_id where a.startTime = i.include_startTime and a.endTime = i.includeTime;
        else
			set @yes_list = false;
		end if;
        
END //
DELIMITER ;
