

-- I assume an efficiency of O(n) if we have an index on open_date and close_date.
-- Otherwise it would be O(n^2) since we are counting the bugs for each day.

-- returns number of open bugs for each day in the given range
CREATE PROCEDURE bugs_in_dates(start_date DATE, end_date DATE)
BEGIN

	DECLARE cur_date DATE; -- current date
  DECLARE num_current_bugs INT; -- bugs opened at the current date

	DROP TABLE IF EXISTS open_bugs;
	CREATE TEMPORARY TABLE open_bugs ( `date` DATE, open_bugs INT );

    SET cur_date = start_date;

    -- current number of bugs (at start_date)
    SET num_current_bugs = (
		SELECT COUNT(*) FROM bugs
        WHERE open_date <= cur_date AND (close_date IS NULL OR close_date > cur_date)
	);

    INSERT INTO open_bugs VALUES (cur_date, num_current_bugs);

    WHILE cur_date < end_date DO

		SET cur_date = DATE_ADD(cur_date, interval 1 day);

        -- calculate the change in number of current bugs for current date

        -- add bugs opened at current date
        SET num_current_bugs = num_current_bugs + (SELECT COUNT(*) FROM bugs WHERE open_date = cur_date);
        -- remove bugs closed at current date
        SET num_current_bugs = num_current_bugs - (SELECT COUNT(*) FROM bugs WHERE close_date = cur_date);

        INSERT INTO open_bugs VALUES (cur_date, num_current_bugs);

    END WHILE;

    SELECT * FROM open_bugs;

END