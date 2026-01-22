import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        String line = "_____________________________________________________________________________\n";
        Task[] tasks = new Task[100];
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
                System.out.println("Here's how your legacy list is going:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1)+ ". " + tasks[i]);
                }
                System.out.println(line);
            } else if (input.startsWith("mark ")) {
                // Mark task as done
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done for the King:\n");
                System.out.println(tasks[index]);
                System.out.println(line);
            } else if (input.startsWith("unmark ")) {
                // Mark task as undone
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsUndone();
                System.out.println("Ok, I've marked this task as not done yet. Keep grinding:\n");
                System.out.println(tasks[index]);
                System.out.println(line);
            } else {
                // Add new Task object to the list
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(line);
                System.out.println("That's what's up King! Added: " + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }
}
