
-- I assume an efficiency of O(n) if we consider that
--   there is a limited number of splits in each row.

-- Reads some_table and returns split rows in temporary table some_table_split
--
CREATE PROCEDURE split_rows(delimiter VARCHAR(10))
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE id1 INT;
  DECLARE len INT;
  DECLARE delim_len INT;
  DECLARE name1 VARCHAR(50);
  DECLARE part VARCHAR(50);
  DECLARE cur CURSOR FOR SELECT id, name FROM some_table;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  SET delim_len = CHAR_LENGTH(delimiter);

  -- output table
  DROP TABLE IF EXISTS some_table_split;
  CREATE TEMPORARY TABLE some_table_split ( ID INT, NAME VARCHAR(50) );

  OPEN cur;
  read_loop: LOOP

    -- get data from original table
    FETCH cur INTO id1, name1;
    IF done THEN
      LEAVE read_loop;
    END IF;

    REPEAT

      -- extract part of name
      SET part = SUBSTRING_INDEX(name1, delimiter, 1);
      SET len = CHAR_LENGTH(part) + delim_len + 1;

      -- remove extracted part
      SET name1 = SUBSTRING(name1, len);

      -- if the part is not empty, insert into result table
      IF CHAR_LENGTH(part) THEN
        INSERT INTO some_table_split (id, name) VALUES(id1, part);
      END IF;

      -- until there's no more to extract
    UNTIL CHAR_LENGTH(name1) = 0

    END REPEAT;

  END LOOP;
  CLOSE cur;

  SELECT * FROM some_table_split;

END;