
-- This would be much simpler if we had the SQL Server function ROW_NUMBER()

-- Efficiency of O(n) if we have an index in votes.

SELECT    v.*,
          @rank := @rank + 1 AS rank
FROM      votes v, (SELECT @rank := 0) r
ORDER BY  votes desc;