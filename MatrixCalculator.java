import java.util.Scanner;
public class MatrixCalculator {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    boolean valid = false;
    int Ar = 0;
    int Ac = 0;
    int Br = 0;
    int Bc = 0;
    while (!valid) {
      System.out.print("Enter the number of rows for the 1st matrix (Matrix A): ");
      Ar = sc.nextInt();
      System.out.print("Enter the number of columns for the 1st matrix (Matrix A): ");
      Ac = sc.nextInt();
      System.out.print("Enter the number of rows for the 2nd matrix (Matrix B): ");
      Br = sc.nextInt();
      System.out.print("Enter the number of columns for the 2nd matrix (Matrix B): ");
      Bc = sc.nextInt();
      if (Ar == Ac && Ac == Br && Br == Bc) {
        valid = true;
      } else {
        System.out.print("The dimensions of the matrices are invalid. The matrices must both be square and the same size.");
      }
    }
    sc.close();
    int[][] a = getMatrix(Ar,Ac,"A");
    int[][] b = getMatrix(Br,Bc,"B");
    System.out.println("A + B");
    displayMatrix(addMatrix(a,b));
    System.out.println("A - B");
    displayMatrix(subMatrix(a,b));
    System.out.println("A * B");
    displayMatrix(multMatrix(a,b));
  }
  public static int[][] getMatrix(int rows, int columns, String name) {
    int[][] matrix = new int[rows][columns];
    Scanner sc = new Scanner(System.in);
    for (int i=0;i<rows;i++) {
      for (int j=0;j<columns;j++) {
        System.out.print("Enter "+name+"["+i+"]["+j+"]: ");
        matrix[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return matrix;
  }
  public static int[][] addMatrix(int[][] a, int[][] b) {
    int[][] matrix = new int[a.length][a[0].length];
    for (int i=0;i<a.length;i++) {
      for (int j=0;j<a[0].length;j++) {
        matrix[i][j] = a[i][j] + b[i][j];
      }
    }
    return matrix;
  }
  public static int[][] subMatrix(int[][] a, int[][] b) {
    int[][] matrix = new int[a.length][a[0].length];
    for (int i=0;i<a.length;i++) {
      for (int j=0;j<a[0].length;j++) {
        matrix[i][j] = a[i][j] - b[i][j];
      }
    }
    return matrix;
  }
  public static int[][] multMatrix(int[][] a, int[][] b) {
    int[][] matrix = new int[a.length][a[0].length];
    for (int i=0;i<a.length;i++) {
      for (int j=0;j<a[0].length;j++) {
        for (int k=0;k<a.length;k++) {
          matrix[i][j] += (a[i][k] * b[k][j]);
        }
      }
    }
    return matrix;
  }
  public static void displayMatrix(int[][] matrix) {
    int maxLen = 0;
    for (int i=0;i<matrix.length;i++) {
      for (int j=0;j<matrix[0].length;j++) {
        maxLen = Math.max(maxLen,String.valueOf(matrix[i][j]).length());
      }
    }
    for (int i=0;i<matrix.length;i++) {
      for (int j=0;j<matrix[0].length;j++) {
        System.out.print(String.format("%-"+maxLen+"s", String.valueOf(matrix[i][j])));
        if (j<matrix[0].length-1) {
          System.out.print("|");
        }
      }
      System.out.println();
    }
  }
}