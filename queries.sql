
--DB1 Words
CREATE TABLE WORD_COLLECTION IF NOT EXISTS (
  WORD_ID INTEGER IDENTITY (1,1) PRIMARY KEY,   -- this looks strange. What does (1,1) mean? I don't see such a thing in docs. And then for IDENTITY type - the real type is LONG, not INT: http://www.h2database.com/html/datatypes.html#identity_type
  -- I'm not saying that it is wrong, just i couldn't find what it means :) 
  WORDS VARCHAR (5)   -- Does that mean you will have only words up to 5 characters long? I think there are a lot of words longer. Why to put such a limit?
  -- I would better have a name WORD - in singular. Otherwise a bit strange - you have WORD_ID - in singular, and then WORDS in plural. :)
  );

INSERT INTO WORD_COLLECTION (WORDS=?);

SELECT WORDS FROM WORD_COLLECTION  
ORDER BY RAND ( )  -- This is nice idea (googled it or figured out yourself?) I did not know about such an approach :)
LIMIT 1;  

-- Fun fact - there is actually database engine (such as H2) that is called DB2. So it gave me a bit of backthought when I saw your DB2 users :D
-- And, BTW, Just to make it clear: You have one database and in one database you have multiple tables. If you call them DB1, DB2 - it might be confusing.
--DB2 Users
CREATE TABLE USERS IF NOT EXISTS (
USER_ID Integer IDENTITY (1,1) PRIMARY KEY,
USERNAME text NOT NULL (20),
-- I think that NAME and AGE can also be NOT NULL. Or do you expect to allow users to ignore name and/or age?
NAME text,
AGE Integer
);

INSERT INTO USERS (USERNAME =?, NAME= ?, AGE= ?); -- does it work this way? I though it is necessary to use INSERT INTO (...) VALUES (...);

-- You will need one more query here - to check if particular user is already registered.

SELECT COUNT (USERNAME) FROM USERS;   -- When you COUNT something it is normal practice to use COUNT(*) instead of anything else. The result will be the same as it counts the rows anyway. Unleass you are using DISTINCT as well.
-- This is what you will see most of the time. So I think it is a good thing to get used to.



--DB3 Results
CREATE TABLE RESULTS IF NOT EXISTS (
GAME_ID INTEGER IDENTITY (1,1) PRIMARY KEY,
  -- Does it work this way? Don't you need to declare columns separately before you put in FOREIGN KEY constraint? I mean if the columns are created, then obviously it works :)
FOREIGN KEY (USER_ID)  REFERENCES USERS (USER_ID),
FOREIGN KEY (WORD_ID) REFERENCES WORD_COLLECTION (WORD_ID),
GUESSES Integer,
WON Integer --saves 0 or 1
  );
  
INSERT INTO RESULTS (USER_ID=?, GUESSES=?, WON=?);

SELECT COUNT (GAME_ID) FROM RESULTS WHERE USER_ID =?;
SELECT COUNT (WON) FROM RESULTS WHERE USER_ID=? AND WON>0;


-- I don't see a query for this requirement:
-- If player chooses "see your results", the program displays all (?) the results of current player.
