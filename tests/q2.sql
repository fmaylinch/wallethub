
SELECT title_case('a');
-- output: 'A'

SELECT title_case('A');
-- output: 'A'

SELECT title_case('cAt Dog hen');
-- output: 'Cat Dog Hen'

SELECT title_case('1+1=2');
-- output: '1+1=2'

SELECT title_case('');
-- output: ''

SELECT title_case(NULL);
-- output: NULL

SELECT title_case('this IS a complEX senTEnce, just 1,   that''s capitalizeD!!');
-- output: 'This Is A Complex Sentence, Just 1,   That\'s Capitalized!!'


