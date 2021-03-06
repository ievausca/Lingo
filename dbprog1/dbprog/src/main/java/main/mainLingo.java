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
    private static final String INSERT_ONLY_ONE_USER = "INSERT INTO USERS (USERNAME, NAME, AGE) VALUES ('lapsa', 'lapsa', 13);";
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
                    System.out.println("Tu esi sp??l??jis " + gamesPlayed + " sp??les" );
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(getGamesWon)) {
                statement.setString(1, String.valueOf(user_id));
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    int gamesWon = rs.getInt(1);
                    System.out.println("Tu esi uzvar??jis " + gamesWon + " sp??l??s" );
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


        for (int i = 0; i < 5; i++) {
            char activeChar1 = guessWord.charAt(i);
            char activeChar2 = enteredWord.charAt(i);
            char charToPrint = stringToPrint.charAt(i);

            if (activeChar1 == activeChar2) {

                stringToPrint.setCharAt(i, toUpperCase(activeChar2));
                guessWord = replaceChar(guessWord, '*', i);
                enteredWord = replaceChar(enteredWord, 'x', i);
            }

        }
        for (int i = 0; i < 5; i++) {
            char activeChar1 = guessWord.charAt(i);
            char activeChar2 = enteredWord.charAt(i);
            char charToPrint = stringToPrint.charAt(i);
            if (guessWord.indexOf(activeChar2) >= 0) {

                stringToPrint.setCharAt(i, toLowerCase(activeChar2));
                guessWord = replaceChar(guessWord, '*', guessWord.indexOf(activeChar2));
                enteredWord = replaceChar(enteredWord, 'x', i);
            }


        }
        return stringToPrint.toString();


    }
    public static int checkIfRegistered (String username) {
        int registered = 0;
        final String checkUsername = "SELECT COUNT (*) FROM USERS WHERE USERNAME=?;";

        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(checkUsername)) {
                statement.setString(1, username);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    registered = rs.getInt(1);

                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
        return registered; }

    public static int getUserId (String username) {
        int user_id=-1;
        try (Connection connection = getConnection()) {

        final String getUserId = "SELECT USER_ID FROM USERS WHERE USERNAME=?;";
        try (PreparedStatement statement = connection.prepareStatement(getUserId)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                user_id = rs.getInt(1);
            }
        }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }

        return user_id; }

    public static void register (String username, String name, int age) {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate("INSERT INTO USERS (USERNAME, NAME, AGE) VALUES ('"  + username + "','" + name+ "',"+ age+ ");");

            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }
    public static int login () {
        Scanner scanner = new Scanner(System.in);

        System.out.println("K??ds ir tavs lietot??jv??rds?");
        String username = scanner.nextLine();
        int registered = checkIfRegistered(username);
        int user_id=-1;
        if (registered>0) {
            user_id = getUserId(username);

        }
        else if (registered==0) {
            System.out.println("Tu v??l neesi re??istr??jies. Lai re??istr??tos, tev b??s j??atbild uz 3 jaut??jumiem.\n 1.K??ds b??s tavs lietot??jv??rds?");
            username = scanner.nextLine();
            System.out.println("2.K??ds ir tavs v??rds?");
            String name = scanner.nextLine();
            System.out.println("3.Cik tev gadu?");
            int age = scanner.nextInt();
            register(username, name, age);
            user_id = getUserId(username);

        }

        return user_id;

    }

    public static void whatNext (int user_id) {
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;
        while (userChoice!=4){
        System.out.println("\n --Ko v??lies dar??t t??l??k?-- \n" +
                "1.Sp??l??t Lingo - raksti: 1 \n" +
                "2.Skat??t savus rezult??tus - raksti: 2 \n" +
                "3.Las??t Lingo noteikumus - raksti: 3 \n" +
                "4.Beigt sp??li - raksti: 4 \n");
        userChoice = scanner.nextInt();


        if (userChoice == 1) {
            playOneGame(user_id);
        }
        if (userChoice == 2) {
            seeResults(user_id);
        }
        if (userChoice == 3) {
            System.out.println("--Kas ir Lingo?-- \n Dators iedom??jas k??du v??rdu, kas sast??v no pieciem burtiem. \n Pirmais burts ir zin??ms. \n Tev ??is v??rds ir j??uzmin ar pieciem m????in??jumiem. \n Raksti savu min??jumu un nospiediet tausti??u ENTER. \n Dators par atbildi dod: \n -tik LIELO BURTU, cik ir pareizi uzmin??tu burtu pareiz??s viet??s. \n -tik mazo burtu, cik ir pareizi uzmin??tu burtu nepareiz??s viet??s. \nLabu veiksmi!");
        }
        if (userChoice == 4){
            System.out.println("Paldies par sp??li! Tiekamies dr??z!");

           // break;
        }
        if (userChoice<1 || userChoice>4){
            System.out.println("Nesapratu tavu izv??li. M????ini v??lreiz.");
        }
    }
    }


    public static void playOneGame (int user_id) {
        Scanner scanner = new Scanner(System.in);
        String guessWord = getGuessWord().toUpperCase();
        StringBuilder hintWord = new StringBuilder(guessWord);
        hintWord.replace(1,5, "----");
        hintWord.toString();
        System.out.println(hintWord);
        for (int i = 0; i < 5; i++) {


            System.out.println("Mini v??rdu: ");
            String enteredWord = scanner.nextLine().toUpperCase();
            while (enteredWord.length()!=5){
                System.out.println("V??rdam nav 5 burti. M????ini v??lreiz!");
                System.out.println("Mini v??rdu: ");
                 enteredWord = scanner.nextLine().toUpperCase();
            }

            if (guessWord.equals(enteredWord)) {
                System.out.println(enteredWord.toUpperCase());
                System.out.println("Tas bija ??tri!");
                insertResults(user_id, i+1, 1 );
                break;

            } else  System.out.println(compareWords(guessWord, enteredWord));
            if (i==4){
                insertResults(user_id, i+1, 0 );
            System.out.println("Tu m????in??ji uzmin??t v??rdu:");
        System.out.println(guessWord);}
        }



    }

    public static void main(String[] args) {


        System.out.println("--Esi sveicin??ts LINGO!-- \n\nPirms sp??les tev j??ielogojas\n");


     int user_id = login();
     whatNext(user_id);



    }
}
