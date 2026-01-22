import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        String line = "_____________________________________________________________________________\n";
        String[] list = new String[100];
        int taskCount = 0;

        //Greeting
        System.out.println(line);
        System.out.println("Yo yo yo, what's up it's your boy LeBron James haha. ");
        System.out.println("Building a legacy, one task at a time. What we workin' on today GOAT? ");
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
            } else if (input.equalsIgnoreCase("list")) {
                // Show the stored list
                System.out.println(line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1)+ ". " + list[i]);
                }
                System.out.println(line);
            } else {
                // Add task to list
                list[taskCount] = input;
                taskCount++;
                System.out.println(line);
                System.out.println("That's what's up King! Added: " + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }
}
