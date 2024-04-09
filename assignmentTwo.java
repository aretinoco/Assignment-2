import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class assignmentTwo {
    private static Map<String, Set<String>> userPostsMap = new HashMap<>();
    private static Map<String, String> userLocationMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            // menu
            System.out.println("1. Load input data");
            System.out.println("2. Check visibility");
            System.out.println("3. Retrieve posts");
            System.out.println("4. Search users by location");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loadInputData();
                    break;
                case "2":
                    checkVisibility(scanner);
                    break;
                case "3":
                    retrievePosts(scanner);
                    break;
                case "4":
                    searchUsersByLocation(scanner);
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("5"));

        scanner.close();
    }

    private static void loadInputData() {
        try {
            Scanner userScanner = new Scanner(new File("src/user-info.txt"));
            Scanner postScanner = new Scanner(new File("src/post-info.txt"));

            while (userScanner.hasNextLine()) {
                String[] userData = userScanner.nextLine().split(";");
                if (userData.length >= 4) {
                    String username = userData[0];
                    String displayName = userData[1];
                    String location = userData[2];
                    // Extracting friend list
                    String[] friends = userData[3].substring(1, userData[3].length() - 1).split(",");
                    userLocationMap.put(username, location);
                } else {
                    System.err.println("Invalid data format in user-info.txt");
                }
            }

            while (postScanner.hasNextLine()) {
                String[] postData = postScanner.nextLine().split(";");
                if (postData.length >= 3) {
                    String postId = postData[0];
                    String username = postData[1];
                    String visibility = postData[2];
                    userPostsMap.putIfAbsent(username, new HashSet<>());
                    userPostsMap.get(username).add(postId);
                } else {
                    System.err.println("Invalid data format in post-info.txt");
                }
            }

            userScanner.close();
            postScanner.close();

            System.out.println("Data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        }
    }

    private static void checkVisibility(Scanner scanner) {
        System.out.print("Input post ID: ");
        String postId = scanner.nextLine();
        System.out.print("Input username: ");
        String username = scanner.nextLine();

        if (userPostsMap.containsKey(username) && userPostsMap.get(username).contains(postId)) {
            System.out.println("Output: Access Permitted");
        } else {
            System.out.println("Output: Access Denied");
        }
    }

    private static void retrievePosts(Scanner scanner) {
        System.out.print("Input username: ");
        String username = scanner.nextLine();

        if (userPostsMap.containsKey(username)) {
            System.out.println("Output: " + String.join(", ", userPostsMap.get(username)));
        } else {
            System.out.println("Output: User does not exist or has no posts.");
        }
    }

    private static void searchUsersByLocation(Scanner scanner) {
        System.out.print("Input state location: ");
        String state = scanner.nextLine();

        List<String> usersInState = new ArrayList<>();
        for (Map.Entry<String, String> entry : userLocationMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(state)) {
                usersInState.add(entry.getKey());
            }
        }

        if (!usersInState.isEmpty()) {
            System.out.println("Output: " + String.join(", ", usersInState));
        } else {
            System.out.println("Output: No users found in the specified location.");
        }
    }
}
