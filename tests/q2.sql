
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

SELECT title_case('this IS a 10n9 senTEnce,  that''s capitalizeD!! ');
-- output: 'This Is A 10n9 Sentence,  that\'s Capitalized!! '



