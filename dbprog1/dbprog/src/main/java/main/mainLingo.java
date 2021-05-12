package main;

import java.util.Scanner;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class mainLingo {

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
        String guessWord = generateGuessWord();
        String guessWord1= guessWord.toUpperCase();
        StringBuilder hintWord = new StringBuilder(guessWord1);
        hintWord.replace(1,5, "----");
        hintWord.toString();
        System.out.println(hintWord);
        for (int i = 0; i < 5; i++) {


            System.out.println("Mini vārdu: ");
            String enteredWord = scanner.nextLine();
            while (enteredWord.length()!=5){
                System.out.println("Vārdam nav 5 burti. Mēģini vēlreiz!");
                System.out.println("Mini vārdu: ");
                 enteredWord = scanner.nextLine();
            }

            if (guessWord.equals(enteredWord)) {
                System.out.println(enteredWord.toUpperCase());
                System.out.println("Tas bija ātri!");
                break;
            } else  System.out.println(compareWords(guessWord, enteredWord));
            if (i==4){
            System.out.println("Tu mēģināji uzminēt vārdu:");
        System.out.println(guessWord1);}
        }

    }










    public static void main(String[] args) {
     playOneGame();



    }
}
