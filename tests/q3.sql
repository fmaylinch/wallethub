
CREATE TABLE some_table ( ID INT, NAME VARCHAR(50) );
INSERT INTO some_table VALUES
  (1, 'Smith'), (2, 'Julio|Jones|Falcons'),
  (3, 'White|Snow'), (4, 'Paint|It|Red'),
  (5, 'Green|Lantern'), (6, 'Brown|bag');

call split_rows();

-- output:
--
-- ID,  NAME
-- '1', 'Smith'
-- '2', 'Julio'
-- '2', 'Jones'
-- '2', 'Falcons'
-- '3', 'White'
-- '3', 'Snow'
-- '4', 'Paint'
-- '4', 'It'
-- '4', 'Red'
-- '5', 'Green'
-- '5', 'Lantern'
-- '6', 'Brown'
-- '6', 'bag'
