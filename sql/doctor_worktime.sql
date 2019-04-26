use hospital;

DELIMITER //
CREATE PROCEDURE doctor_worktime(in javaStart varchar(30), in javaEnd varchar(30), in javaDoctorID int, OUT yes_work bool )
BEGIN
		declare done bool default false;
	    declare w_doctorID, w_start_day, w_end_day, js_startDay, js_endDay int;
        declare w_start_time, w_end_time, js_startTime, js_endTime varchar(30);
        declare cur1 cursor for select doctorID, startDay, startTime, endDay, endTime from charts where doctorID = javaDoctorID;
        declare continue handler for not found set done = true;
        
        set js_startDay = dayofweek(javastart);
        set js_endDay = dayofweek(javastart);
        set js_startTime = Time(javastart);
        set js_endTime = Time(javastart);
        
        open cur1;
        
        create temporary table if not exists t (id int, stDay int, stTime varchar(30), edDay int, edTime varchar(30));
        
        repeat 
			fetch cur1 into w_doctorID, w_start_day, w_start_time, w_end_day, w_end_time;
            
            if w_start_day > w_end_day then
				insert into t(id, stDay, stTime, edDay, edTime) value (w_doctorID, w_start_day-7, w_start_time, w_end_day, w_end_time);
			else
            	insert into t(id, stDay, stTime, edDay, edTime) value (w_doctorID, w_start_day, w_start_time, w_end_day, w_end_time);
			end if;
            
		until done end repeat;
        
		if (select id from t where stDay < js_startDay and edDay > js_endDay and id = javaDoctorID ) != null then
			set @yes_work = true;
		elseif (select id from t where stDay = js_startDay and edDay > js_endDay and stTime <= js_startTime and id = javaDoctorID) != null then
			set @yes_work = true;
		elseif (select id from t where stDay < js_startDay and edDay = js_endDay and edTime >= js_endTime and id = javaDoctorID) != null then
			set @yes_work = true;
        elseif (select id from t where stDay = js_startDay and edDay = js_endDay and stTime <= js_startTime and edTime >= js_endTime and id = javaDoctorID) != null then
			set @yes_work = true;
		else
			set @yes_work = false;
		end if;
        
        drop temporary table if exists t;
        
END //
DELIMITER ;
