package main;

import java.sql.*;
import java.util.Scanner;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class mainLingo {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:C:\\Users\\Dell\\Desktop\\java\\db\\testdb";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    private static final String DROP_TABLE = "DELETE FROM WORD_COLLECTION";
    private static final String CREATE_TABLE_WORDS = " CREATE TABLE if not exists WORD_COLLECTION  " +
            "(WORD_ID bigint auto_increment,WORD TEXT (10), PRIMARY KEY (WORD_ID));";
    private static final String INSERTCSV = "INSERT INTO WORD_COLLECTION (WORD) SELECT * FROM CSVREAD('C:\\Users\\Dell\\Desktop\\java\\Lingo\\dbprog1\\dbprog\\src\\main\\java\\main\\5bvardi1.csv')";
    private static final String CREATE_TABLE_RESULTS = "CREATE TABLE IF NOT EXISTS RESULTS  (GAME_ID BIGINT AUTO_INCREMENT,USER_ID BIGINT,WORD_ID BIGINT,PRIMARY KEY (GAME_ID),FOREIGN KEY (USER_ID)  REFERENCES USERS (USER_ID),FOREIGN KEY (WORD_ID) REFERENCES WORD_COLLECTION (WORD_ID),GUESSES Integer,WON Integer);";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS USERS  (USER_ID BIGINT AUTO_INCREMENT,USERNAME text NOT NULL,NAME text  NOT NULL,AGE Integer NOT NULL,PRIMARY KEY (USER_ID));";
    private static final String INSERT_ONLY_ONE_USER = "INSERT INTO USERS (USERNAME, NAME, AGE) VALUES ('ķirbis', 'ķirbis', 115);";
    public static void createTable () {

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate(CREATE_TABLE_WORDS);
                statement.executeUpdate(DROP_TABLE);
                statement.executeUpdate(INSERTCSV);
                statement.executeUpdate(CREATE_TABLE_USERS);
                statement.executeUpdate(CREATE_TABLE_RESULTS);
                statement.executeUpdate(INSERT_ONLY_ONE_USER);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public static String getGuessWord() {
        String guessWord = "";
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT WORD FROM WORD_COLLECTION ORDER BY RAND ( ) LIMIT 1;" )) {
                    rs.next();
                    guessWord = rs.getString(1);
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
        return guessWord;
    }

    public static void insertResults (int user_id, int guesses, int won) {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate("INSERT INTO RESULTS (USER_ID, GUESSES, WON) VALUES (" + user_id + "," + guesses+ ","+ won+ ");");
                {

                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public static void seeResults (int user_id) {
        final String getNumberOfGames = "SELECT COUNT (GAME_ID) FROM RESULTS WHERE USER_ID =?;";
        final String getGamesWon = "SELECT COUNT (*) FROM RESULTS WHERE USER_ID=? AND WON=1;";

        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getNumberOfGames)) {
                statement.setString(1, String.valueOf(user_id));
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    int gamesPlayed = rs.getInt(1);
                    System.out.println("Tu esi spēlējis " + gamesPlayed + " spēles" );
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(getGamesWon)) {
                statement.setString(1, String.valueOf(user_id));
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    int gamesWon = rs.getInt(1);
                    System.out.println("Tu esi uzvarējis " + gamesWon + " spēlēs" );
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }




    public static String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    public static String compareWords (String guessWord, String enteredWord) {

        StringBuilder stringToPrint = new StringBuilder("-----");
        StringBuilder guessWordBuilder = new StringBuilder(guessWord);



        for (int i = 0; i<5; i++) {
            char activeChar1 = guessWord.charAt(i);
            char activeChar2 = enteredWord.charAt(i);
            char charToPrint = stringToPrint.charAt(i);

            if (activeChar1==activeChar2) {

                stringToPrint.setCharAt(i, toUpperCase(activeChar2));
                guessWord=replaceChar(guessWord,'*', i);
                enteredWord=replaceChar(enteredWord,'x', i);
            }

        }
        for (int i = 0; i<5; i++) {
            char activeChar1 = guessWord.charAt(i);
            char activeChar2 = enteredWord.charAt(i);
            char charToPrint = stringToPrint.charAt(i);
            if (guessWord.indexOf(activeChar2)>=0) {

                stringToPrint.setCharAt(i, toLowerCase(activeChar2));
                guessWord=replaceChar(guessWord,'*', guessWord.indexOf(activeChar2));
                enteredWord=replaceChar(enteredWord,'x', i);
            }


        }
    return stringToPrint.toString();
    }


    public static void playOneGame (int user_id) {
        Scanner scanner = new Scanner(System.in);
        String guessWord = getGuessWord().toUpperCase();
        StringBuilder hintWord = new StringBuilder(guessWord);
        hintWord.replace(1,5, "----");
        hintWord.toString();
        System.out.println(hintWord);
        for (int i = 0; i < 5; i++) {


            System.out.println("Mini vārdu: ");
            String enteredWord = scanner.nextLine().toUpperCase();
            while (enteredWord.length()!=5){
                System.out.println("Vārdam nav 5 burti. Mēģini vēlreiz!");
                System.out.println("Mini vārdu: ");
                 enteredWord = scanner.nextLine().toUpperCase();
            }

            if (guessWord.equals(enteredWord)) {
                System.out.println(enteredWord.toUpperCase());
                System.out.println("Tas bija ātri!");
                insertResults(user_id, i+1, 1 );
                break;

            } else  System.out.println(compareWords(guessWord, enteredWord));
            if (i==4){
                insertResults(user_id, i+1, 0 );
            System.out.println("Tu mēģināji uzminēt vārdu:");
        System.out.println(guessWord);}
        }


    }

    public static void main(String[] args) {

      //  createTable();

        playOneGame(1);
        seeResults(1);

    }
}
