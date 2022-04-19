import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Clear {

  static Random Ran;
  public static void main(String[] args) throws IOException {
    Ran = new Random();
    int ammount = 17;
    File[] file = new File[ammount];
    String prefix = "C:\\Users\\jimmy\\Desktop\\download\\ps9\\";
    for (int x = 1; x <= ammount; ++x) {
      String suff = String.valueOf(x);
      String filename = prefix + "birds" + suff + ".ppm";
      System.out.println("Opening "+filename);
      file[x-1] = new File(filename);
    }

    long start = System.currentTimeMillis();

    Scanner[] scan = new Scanner[ammount];

    int rows = 0, cols = 0, max = 0;
    for (int i = 0; i < ammount; ++i) {
      try {
        scan[i] = new Scanner(file[i]);
        String line = scan[i].nextLine();
        cols = Integer.parseInt(scan[i].next());
        rows = Integer.parseInt(scan[i].next());
        max = Integer.parseInt(scan[i].next());
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }


    BufferedWriter output = new BufferedWriter(new FileWriter("blurred.ppm"));
    output.write(String.format("%s\n", "P3"));
    output.write(String.format("%d  %d\n", cols, rows));
    output.write(String.format("%d\n", max));
    System.out.println(rows + ", " + cols + ", " + max);

    int [] Hihg_pixhld = new int[17];

    for (int i = 0; i < cols; ++i) {
      for (int j = 0; j < rows; ++j) {
        for (int c = 0; c < 3; ++c) {
          for (int k = 0; k < ammount; ++k){
            Hihg_pixhld[k] = Integer.parseInt(scan[k].next());
          }
          output.write(String.format("%d ", select(Hihg_pixhld, 0, Hihg_pixhld.length-1, 13)));
        }
      }
      output.write(String.format("\n"));
    }

    for (int i = 0; i < ammount; ++i) {
      scan[i].close();
    }
    output.close();
  }
  static int select(int[] A, int i, int j, int q) { 
    if (i == j)
      return A[i];
    int pivot_index = i + Ran.nextInt(j - i + 1); 
    swap(A, pivot_index, j); 

    int k = partition(A, i - 1, j, A[j]);
    swap(A, k, j); 

    int sz = k - i;
    if (q == sz + 1)
      return A[k];
    else if (q <= sz)
      return select(A, i, k - 1, q);
    else 
      return select(A, k+1, j, q - sz-1);
  }

  static int partition(int[] A, int l, int r, int pivot) {
    do { 
      while (A[++l] < pivot)
        ;
      while ((r != 0) && (A[--r] >= pivot))
        ;
      swap(A, l, r);
    } while (l < r); 
    swap(A, l, r); 
    return l; 
  }

  private static void swap(int[] X, int i, int j) {
    int temp = X[j];
    X[j] = X[i];
    X[i] = temp;
  }
}
