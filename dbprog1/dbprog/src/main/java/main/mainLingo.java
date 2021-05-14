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
    private static final String CREATE_TABLE = " CREATE TABLE if not exists WORD_COLLECTION  " +
            "(WORD_ID bigint auto_increment,WORD TEXT (10), PRIMARY KEY (WORD_ID));";
    private static final String INSERTCSV = "INSERT INTO WORD_COLLECTION (WORD) SELECT * FROM CSVREAD('C:\\Users\\Dell\\Desktop\\java\\Lingo\\dbprog1\\dbprog\\src\\main\\java\\main\\5bvardi1.csv')";

    public static void createTable () {

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate(CREATE_TABLE);
                statement.executeUpdate(DROP_TABLE);
                statement.executeUpdate(INSERTCSV);
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
    public static String generateGuessWord (){
        return "lapsa";
    }


    public static void playOneGame () {
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
                break;
            } else  System.out.println(compareWords(guessWord, enteredWord));
            if (i==4){
            System.out.println("Tu mēģināji uzminēt vārdu:");
        System.out.println(guessWord);}
        }

    }

    public static void main(String[] args) {
     playOneGame();
        //createTable();



    }
}
