# Lingo

Kas ir Lingo?
Dators iedomājas kādu vārdu, kas sastāv  no pieciem burtiem. 
Pirmais burts ir zināms. 
Jums šis vārds ir jāuzmin ar pieciem mēģinājumiem.
Ievadiet savu minējumu ar tastatūras palīdzību un nospiediet taustiņu ENTER. 
Dators par atbildi dod:
-tik LIELO BURTU, cik ir pareizi uzminētu burtu pareizās vietās 
-tik mazo burtu, cik ir pareizi uzminētu burtu nepareizās vietās. 
Labu veiksmi!


Requirements: 
> Vlad: In 1: There are 3 things about user: Username, Name and Age. Username is used to login and identify user (there are no duplicate usernames in a db). Name and age do not have any constraints.

1. User must log in. To do that user has to have a username that is already registered in our DB. If not, user has to provide username and age. 
2. User can choose between two options: "play" or "read game rules". If user chooses reading rules the above text is displayed. 
3. If user chooses to play, Lingo starts. User has five goes to guess the word of five letters. 
    1. Program shows first letter, user enters four letters. Program returns CapsLock letters that are correct and in correct places. Program returns lowerCase letters for letters that are correct but in incorrect places. 
    2. In second go user can enter five letters. Again program returns the above described. 
    3. If user guesses all letters in correct places, program displays the correct word in CapsLock and text "You won!" (or sth like that)
    4. When the word is guessed or user has ran out of goes, the program offers to "play once more", "see your results" or to "exit". 
4. If player chooses "see your results", the program displays all (?) the results of current player. Results show how many words user has guessed and how many words user hasn't guessed. 


> Vlad: in last let's also add information how much tries user have used for each word.
