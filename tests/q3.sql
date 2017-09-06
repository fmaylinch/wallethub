
CREATE TABLE some_table ( ID INT, NAME VARCHAR(50) );
INSERT INTO some_table VALUES
  (1, 'Smith'), (2, 'Julio|Jones|Falcons'),
  (3, '|one||two|');

call split_rows();

-- output:
--
-- ID,  NAME
-- '1', 'Smith'
-- '2', 'Julio'
-- '2', 'Jones'
-- '2', 'Falcons'
-- '3', 'one'
-- '3', 'two'
