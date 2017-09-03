
CREATE TABLE votes ( name CHAR(10), votes INT );
INSERT INTO votes VALUES
('Smith',10), ('Jones',15), ('White',20), ('Black',40), ('Green',50), ('Brown',20);

SELECT    v.*,
          @rank := @rank + 1 AS rank
FROM      votes v, (SELECT @rank := 0) r
ORDER BY  votes desc;

-- output:
--
-- name,    votes, rank
-- 'Green', '50', '1'
-- 'Black', '40', '2'
-- 'White', '20', '3'
-- 'Brown', '20', '4'
-- 'Jones', '15', '5'
-- 'Smith', '10', '6'
