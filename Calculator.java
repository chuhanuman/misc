import java.util.Scanner;
public class Calculator
{
  public static void main(String[] args)
  {
    Scanner sc = new Scanner(System.in);
    System.out.print("Please enter the operation in int: ");
    //add, subtract, multiply, divide, convert base
    int op = sc.nextInt();
    String optxt = "";
    if (op == 5) {
      System.out.print("Please enter the number in string(use capital letters): ");
      sc.nextLine();
      String num = sc.nextLine();
      System.out.print("Please enter the base in int: ");
      int base = sc.nextInt();
      System.out.print("Please enter the base to convert to in int: ");
      int convertedBase = sc.nextInt();
      int numBase10 = 0;
      int index = num.length() - 1;
      int basemultiplier = 1;
      int digit = 0;
      while (index >= 0)
      {
        digit = Integer.valueOf(num.charAt(index));
        if (digit >= 48 && digit <= 57) {
          numBase10 += basemultiplier * (digit-48);
        } else if (digit >= 65 && digit <= 90) {
          numBase10 += basemultiplier * (digit-55);
        } else {
          System.out.println("Error: The number to convert isn't in the right format");
          return;
        }
        index --;
        basemultiplier *= base;
      }
      String ans = "";
      while (numBase10 != 0)
      {
        digit = numBase10 % convertedBase;
        if (digit < 10) {
          ans = digit + ans;
        } else {
          ans = (char)(digit+55) + ans;
        }
        numBase10 /= convertedBase;
      }
      System.out.print("Converted base: ");
      if (ans == "") {
        System.out.println(0);
      } else {
        System.out.println(ans);
      }
    }
    sc.close();
    if (op != 5)
    {
      double ans = 0;
      double a = GetDouble(); 
      double b = GetDouble();
      switch (op) {
        case 1:
          optxt = "+";
          ans = a + b;
          break;
        case 2:
          optxt = "-";
          ans = a - b;
          break;
        case 3:
          optxt = "*";
          ans = a * b;
          break;
        case 4:
          optxt = "/";
          ans = a / b;
          break;
      }
      System.out.print(a + " " + optxt + " " + b + " = " + ans);
    }
  }
  public static double GetDouble()
  {
    Scanner sc = new Scanner(System.in);
    System.out.print("Please enter a number in double: ");
    double num = sc.nextDouble();
    sc.close();
    return num;
  }
}
