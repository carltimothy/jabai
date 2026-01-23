import java.util.Scanner;

class Main {
public static void main(String[] args) {

//ASCII Art by Carl Timothy
    System.out.println("             .-\"\"-.");
    System.out.println("          _.->    <-._");
    System.out.println("       .-\"   '-__-'   \"-.");
    System.out.println("     ,\"                  \",");
    System.out.println("   .'                      ',");
    System.out.println("  /    ___...------...___    \\");
    System.out.println(" /_.-*\"__...--------...__\"*-._\\");
    System.out.println(":_.-*\"'  .*\"*-.  .-*\"*.  '\"*-._;");
    System.out.println(";      ;    *  !!  *    :      :");
    System.out.println(":      :     .'  '.     ;      ;");
    System.out.println(" \\      '-.-'      '-.-'      /");
    System.out.println("  \\                          /");
    System.out.println("   '.        ______        .'");
    System.out.println("     *,      '-__-'      ,*");
    System.out.println("     /.'-_            _-'.'\\");
    System.out.println("    /  \"-_\"*-.____.-*\"_-\"   \\");
    System.out.println("   /      '\"*-.___.-*'       \\");
    System.out.println("  :    :        |        ;    ;");
    System.out.println("  |.--.;       *|        :.--.|");
    System.out.println("  (   ()        |        ()   )");
    System.out.println("   '--^_       *|        _^--'");
    System.out.println("      | \"'*--.._I_..--*'\" |");
    System.out.println("      | __..._  | _..._   |");
    System.out.println("     .'\"      `\"'\"     ''\"'.");
    System.out.println("     \"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"");
    System.out.println("                          -Carl Timothy");

//User Input Section
        //String
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner1.nextLine();
        System.out.println("Hello, " + name + "!");
        //Integer
        Scanner scanner2 = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = scanner2.nextInt();
        System.out.println("You are " + age + " years old.");
        //Float
        Scanner scanner3 = new Scanner(System.in);
        System.out.print("Enter your height: ");
        double height = scanner3.nextDouble();
        System.out.println("Height: " + height + " meters.");

//Variables
        int a = 10;
        int b = 20;
        int sum = a + b;
        System.out.println("The sum of " + a + " and " + b + " is: " + sum);
    }

//If Statements
        if (number > 0) {
            System.out.println(number + " is positive.");
        } else if (number < 0) {
            System.out.println(number + " is negative.");
        } else {
            System.out.println("The number is zero.");
        }

//For Statements
    void printNumbers() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Number: " + i);
        }
    }
}