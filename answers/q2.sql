
-- I guess this has efficiency of O(n^2) because I suppose the string handling
--  needs to be traversed in each iteration of my WHILE loop.

-- Returns input in 'Title Case'
CREATE FUNCTION title_case (input VARCHAR(255))

RETURNS VARCHAR(255)

DETERMINISTIC

BEGIN
  DECLARE len INT;
  DECLARE i INT;
  
  DECLARE output VARCHAR(255);

  SET len = CHAR_LENGTH(input);

  -- don't do anything with empty string
  IF len = 0 THEN
    RETURN input;
  END IF;

  SET input = LOWER(input);
  SET output = UPPER(LEFT(input,1)); -- first upper char
  SET i = 2; -- skip first char, already added

  WHILE (i <= len) DO

    -- add char (already in lower case)
    SET output = CONCAT(output, MID(input,i,1));

    -- add upper char after space
    IF (MID(input,i,1) = ' ' AND i < len) THEN
      SET i = i + 1;
      SET output = CONCAT(output, UPPER(MID(input,i,1)));
    END IF;

    SET i = i + 1;

  END WHILE;

  RETURN output;
END;