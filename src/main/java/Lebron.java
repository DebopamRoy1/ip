import java.util.Scanner;
import java.util.ArrayList;

public class Lebron {
    private static final String line = "_______________________________________________________________________________";
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();

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
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark")) {
                    int index = Parser.parseIndex(input, "mark", tasks.size());
                    tasks.get(index).markAsDone();
                    System.out.println(line);
                    System.out.println("Nice! I've marked this as done for the king:");
                    System.out.println(tasks.get(index));
                    System.out.println(line);
                } else if (input.startsWith("unmark")) {
                    int index = Parser.parseIndex(input, "unmark", tasks.size());
                    tasks.get(index).markAsUndone();
                    System.out.println(line);
                    System.out.println("Ok, I've marked this as not done yet. Keep grinding:");
                    System.out.println(tasks.get(index));
                    System.out.println(line);
                } else if (input.startsWith("delete")) {
                    int index = Parser.parseDeleteIndex(input, tasks.size());
                    Task removedTask = tasks.remove(index);
                    System.out.println(line);
                    System.out.println("Noted. I've benched this task and removed it from the rotation:");
                    System.out.println(removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list. #StayReady");
                    System.out.println(line);
                } else if (input.startsWith("todo")) {
                    tasks.add(Parser.parseTodo(input));
                    printAddedMessage(tasks.get(tasks.size() - 1), tasks.size());
                } else if (input.startsWith("deadline")) {
                    tasks.add(Parser.parseDeadline(input));
                    printAddedMessage(tasks.get(tasks.size() - 1), tasks.size());
                } else if (input.startsWith("event")) {
                    tasks.add(Parser.parseEvent(input));
                    printAddedMessage(tasks.get(tasks.size() - 1), tasks.size());
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
