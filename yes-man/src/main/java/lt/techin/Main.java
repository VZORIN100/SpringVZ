package lt.techin;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Yes or no?");

      String carryOnLoop = scanner.nextLine();

      if (carryOnLoop.equals("Yes")) {
        continue;
      }

      if (carryOnLoop.equals("No")) {
        break;
      }
    }

  }
}