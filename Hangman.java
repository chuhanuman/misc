import java.util.Scanner;
public class Hangman {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    boolean play = true;
    while (play) {
      playGame();
      System.out.print("Play again? (y/n)");
      play = sc.next().charAt(0) == 'y';
    }
    sc.close();
  }
  private static String editString(String str, int index, char letter) {
    return str.substring(0,index)+letter+str.substring(index+1);
  }
  private static void playGame() {
    String[] words = {"red","blue","green","yellow","magenta"};
    String word = words[(int)(Math.random()*5)];
    String secret = "";
    for (int i=0;i<word.length();i++) {
      secret += "_";
    }
    int show1 = (int) (Math.random()*word.length());
    secret = editString(secret,show1,word.charAt(show1));
    if (word.length() > 4) {
      int show2 = (int) (Math.random()*(word.length()-1));
      if (show2 >= show1) {
        show2++;
      }
      secret = editString(secret,show2,word.charAt(show2));
    }
    System.out.println(secret);
    int guesses = 3;
    Scanner sc = new Scanner(System.in);
    char guess;
    boolean correct;
    while (guesses > 0 && !secret.equals(word)) {
      System.out.print("Guess: ");
      guess = sc.next().charAt(0);
      correct = false;
      for (int i=0;i<word.length();i++) {
        if (word.charAt(i) == guess && secret.charAt(i) == '_') {
          secret = editString(secret,i,word.charAt(i));
          correct = true;
        }
      }
      if (correct) {
        System.out.println(secret);
      } else {
        if (guesses == 3) {
          System.out.print("Hint: The word is a color.");
        }
        guesses--;
        System.out.println(guesses + " guesses left");
      }
    }
    sc.close();
    if (guesses > 0) {
      System.out.println("You won!");
    } else {
      System.out.println("You lost.");
    }
  }
}