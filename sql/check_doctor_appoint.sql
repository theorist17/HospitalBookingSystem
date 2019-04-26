use hospital;

DELIMITER //
CREATE PROCEDURE check_doctor_appoint(IN javaPersonal char(30),IN javaDoctorID INT , IN javaStart varchar(30), IN javaEnd varchar(30), IN javaDept varchar(30), IN checkDept INT, OUT result BOOL )
BEGIN
	    DECLARE my_doctorID, D_start_day, D_end_day, D_start_time, D_end_time INT;
        DECLARE D_check, D_available, D_free, D_pain, dept_free bool default false;
        DECLARE my_dept varchar(30);
		DECLARE D_patinetID char(13);
        
        SET D_patinetID = (select distinct patinetID from patient where doctorID = javaDoctorID);
        
		SET	my_doctorID = (select distinct doctorID from patient where patientID = javaPersonal);
		SET my_dept = (select department from doctors where my_doctorID = doctorID); 	
        
		SET D_check = doctor_worktime(javaStart, javaEnd, javaDoctorID);
		SET D_available = doctor_available(javaStart, javaEnd, javaDoctorID);
		SET D_free = doctor_free(javaStart, javaEnd, my_doctorID, my_dept);
        SET D_pain = pain_doctor(javaStart, javaEnd, D_patientID); 
        SET dept_free = department_free(avaStart, javaEnd, my_doctorID, javaDept);

        IF checkDept = 0 then

            IF my_doctorID = javaDoctorID then 
					set @result = false; 
            
			ELSEIF !D_pain then 
					set @result = false; 
                    
			ELSEIF !D_check then 
					set @result = false; 
                    
			ELSEIF !D_available then 
					set @result = false;
                    
			ELSEIF !D_free then
					set @result = false;
                    
			ELSEif !(mylist(javaStart, javaEnd, my_doctorID, my_dept)) then
					set @result = false;
			else
					insert into appointments (patientID, doctorID, timeStart, timeEnd) values(javaPersonal, javaDoctorID, javaStart, javaEnd);
					set @result = true; 
			END IF;
		
        ELSE 
			IF !dept_free then
				SET @result = false;
                
			ELSEif !(mylist(javaStart, javaEnd, D_personalID, D_dept)) then
				SET @result = false;		
			ELSE
				insert into appointments (patientID, doctorID, timeStart, timeEnd) values(javaPersonal, free_id(javaStart, javaEnd, my_doctorID, javaDept), javaStart, javaEnd);
				set @result = true; 	 	
			END IF;
		END IF;

			
END //
DELIMITER ;
