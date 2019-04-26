use hospital;

DELIMITER //
CREATE PROCEDURE pain_doctor(in javaStart varchar(30), in javaEnd varchar(30), in javaPatinetID char(13), OUT check_pain bool)
BEGIN

			if (select patientID from checkups where timeStart <= javastart and timeEnd >= javaEnd and patientID = javaPatinetID) != null then
					set @check_pain = false;
			elseif (select patientID from checkups where timeStart <= javaStart and timeEnd > javaStart and patientID = javaPatinetID) != null then
					set @check_pain = false;
			elseif (select patientID from checkups where  timeStart = javaStart and timeEnd = javaStart and patientID = javaPatinetID) != null then
					set @check_pain = false; 
			elseif (select patientID from checkups where timeStart < javaEnd and timeEnd >= javaEnd and patientID = javaPatinetID) != null then
					set @check_pain = false;
			elseif (select patientID from checkups where timeStart >= javaStart and timeEnd <= javaEnd and patientID = javaPatinetID) != null then
					set @check_pain = false;

			elseif (select patientID from stays where timeStart <= javastart and timeEnd >= javaEnd and patientID = javaPatinetID) != null then
					set @check_pain = false;
			elseif (select patientID from stays where timeStart <= javaStart and timeEnd > javaStart and patientID = javaPatinetID) != null then
					set @check_pain = false;
			elseif (select patientID from stays where  timeStart = javaStart and timeEnd = javaStart and patientID = javaPatinetID) != null then
					set @check_pain = false; 
			elseif (select patientID from stays where timeStart < javaEnd and timeEnd >= javaEnd and patientID = javaPatinetID) != null then
					set @check_pain = false;
			elseif (select patientID from stays where timeStart >= javaStart and timeEnd <= javaEnd and patientID = javaPatinetID) != null then
					set @check_pain = false;
			else
					set @check_pain = true;
			end if;
                        
    
END //
DELIMITER ;
