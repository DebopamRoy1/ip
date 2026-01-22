import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        String line = "_____________________________________________________________________________\n";

        //Greeting
        System.out.println(line);
        System.out.println("Yo yo yo, what's up it's your boy LeBron James haha. ");
        System.out.println("Just a kid from Akron, Ohio here to help you. What's on your mind?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            // Check if user wants to exit
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line);
                System.out.println("That's all from your boy from Akron, Ohio");
                System.out.println("#striveforgreatness #appreciategreatness");
                System.out.println(line);
                break;
            }

            // Echo the user input
            System.out.println(line);
            System.out.println("That's what's up King! You said: " + input);
            System.out.println(line);
        }

        scanner.close();
    }
}
