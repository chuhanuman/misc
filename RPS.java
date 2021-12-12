import java.util.Scanner;
public class RPS
{
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter your choice: ");
    char player = sc.next().charAt(0);
    sc.close();
    int pnum = 100;
    switch (player) {
      case 'R':
        pnum = 0;
        break;
      case 'P':
        pnum = 1;
        break;
      case 'S':
        pnum = 3;
        break;
    }
    int compnum = (int) (Math.random() * 3);
    String computer = "?";
    switch (compnum) {
      case 0:
        computer = "rock";
        break;
      case 1:
        computer = "paper";
        break;
      case 2:
        computer = "scissors";
        break;
    }
    System.out.println("Computer played "+computer+".");
    if (pnum == compnum) {
      System.out.println("It's a tie.");
    } else if ((pnum - compnum + 3)%3 == 1) {
      System.out.println("You win.");
    } else {
      System.out.println("The computer wins.");
    }
  }
}