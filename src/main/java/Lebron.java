import java.util.Scanner;

public class Lebron {
    private static final String line = "_______________________________________________________________________________";
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

        //Greeting
        System.out.println(line);
        System.out.println("Yo yo yo, what's up it's your boy LeBron James haha.");
        System.out.println("Building a legacy, one task at a time. What we workin' on today GOAT?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            try{
                // Check if the user wants to exit
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
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark")) {
                    int index = Parser.parseIndex(input, "mark", taskCount);
                    tasks[index].markAsDone();
                    System.out.println(line);
                    System.out.println("Nice! I've marked this as done for the king:");
                    System.out.println(tasks[index]);
                    System.out.println(line);
                } else if (input.startsWith("unmark")) {
                    int index = Parser.parseIndex(input, "unmark", taskCount);
                    tasks[index].markAsUndone();
                    System.out.println(line);
                    System.out.println("Ok, I've marked this as not done yet. Keep grinding:");
                    System.out.println(tasks[index]);
                    System.out.println(line);
                } else if (input.startsWith("todo")) {
                    tasks[taskCount] = Parser.parseTodo(input);
                    taskCount++;
                    printAddedMessage(tasks[taskCount - 1], taskCount);
                } else if (input.startsWith("deadline")) {
                    tasks[taskCount] = Parser.parseDeadline(input);
                    taskCount++;
                    printAddedMessage(tasks[taskCount - 1], taskCount);
                } else if (input.startsWith("event")) {
                    tasks[taskCount] = Parser.parseEvent(input);
                    taskCount++;
                    printAddedMessage(tasks[taskCount - 1], taskCount);
                } else {
                    throw new LebronException("Hol' up... I don't know what '" + input + "' means. Check the playbook!");
                }
            } catch (LebronException e){
                System.out.println(line);
                System.out.println("ANDDDD ONEE: " + e.getMessage());
                System.out.println(line);
            }
        }
        scanner.close();
    }

    private static void printAddedMessage(Task task, int count) {
        System.out.println(line);
        System.out.println("That's what's up king! I've added this to the legacy:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list. #StayReady");
        System.out.println(line);
    }
}
