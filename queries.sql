
--Tb1 Words
CREATE TABLE if not exists WORD_COLLECTION  (
  WORD_ID bigint auto_increment,    
  WORD TEXT (10),
  PRIMARY KEY (WORD_ID)
  );

INSERT INTO WORD_COLLECTION (WORD) VALUES ('?');

SELECT WORD FROM WORD_COLLECTION  
ORDER BY RAND ( ) 
LIMIT 1;  

--Tb2 Users
CREATE TABLE IF NOT EXISTS USERS  (
USER_ID BIGINT AUTO_INCREMENT, 
USERNAME text NOT NULL,
NAME text  NOT NULL,
AGE Integer NOT NULL,
PRIMARY KEY (USER_ID)
);
SELECT COUNT (*) FROM USERS WHERE USERNAME=?;  --jāatceras, ka pirms pārbauda, viss jāpārtaisa CAPSLOCK, savādāk neatradīs

INSERT INTO USERS (USERNAME, NAME, AGE); VALUES ('?', '?', ?); 





--Tb3 Results
CREATE TABLE IF NOT EXISTS RESULTS  (
GAME_ID BIGINT AUTO_INCREMENT,
USER_ID BIGINT,
WORD_ID BIGINT,
PRIMARY KEY (GAME_ID),
FOREIGN KEY (USER_ID)  REFERENCES USERS (USER_ID),
FOREIGN KEY (WORD_ID) REFERENCES WORD_COLLECTION (WORD_ID),
GUESSES Integer,
WON Integer --saves 0 or 1
  );
  
 
  
INSERT INTO RESULTS (USER_ID, GUESSES, WON) VALUES (?, ?, ?);

SELECT COUNT (GAME_ID) FROM RESULTS WHERE USER_ID =?; --cik spēles ir spēlētas
SELECT COUNT (WON) FROM RESULTS WHERE USER_ID=? AND WON>0; --cik spēles ir uzvarētas


-- I don't see a query for this requirement:
-- If player chooses "see your results", the program displays all (?) the results of current player.
