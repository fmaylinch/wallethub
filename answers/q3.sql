
DELIMITER $$


-- Helper function to split a string
CREATE FUNCTION split_string(str VARCHAR(255), delimiter VARCHAR(10), pos INT)
RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
  DECLARE output VARCHAR(255);
  SET output = REPLACE(SUBSTRING(SUBSTRING_INDEX(str, delimiter, pos)
                 , LENGTH(SUBSTRING_INDEX(str, delimiter, pos - 1)) + 1)
                 , delimiter
                 , '');
  IF output = '' THEN SET output = null; END IF;
  RETURN output;
END $$


-- Reads sometbl and returns split rows in temporary table sometbl_split
CREATE PROCEDURE split_rows()
BEGIN
  DECLARE i INT;

  SET i = 1;
  DROP TABLE IF EXISTS sometbl_split;
  CREATE TEMPORARY TABLE sometbl_split ( ID INT, NAME VARCHAR(50) );
  REPEAT
    INSERT INTO sometbl_split (id, name)
      SELECT id, split_string(name, '|', i) FROM sometbl
      WHERE split_string(name, '|', i) IS NOT NULL;
    SET i = i + 1;
    UNTIL ROW_COUNT() = 0
  END REPEAT;
  SELECT * FROM sometbl_split order by id;
END $$


DELIMITER ;
