import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class User {
    String username;
    String displayName;
    String state;
    List<String> friends;

    public User(String username, String displayName, String state, List<String> friends) {
        this.username = username;
        this.displayName = displayName;
        this.state = state;
        this.friends = friends;
    }
}

class Post {
    String postId;
    String userId;
    String visibility;

    public Post(String postId, String userId, String visibility) {
        this.postId = postId;
        this.userId = userId;
        this.visibility = visibility;
    }
}

public class SocialMediaApp {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Post> posts = new HashMap<>();

    public static void loadData(String userFilePath, String postFilePath) {
        try {
            BufferedReader userReader = new BufferedReader(new FileReader(userFilePath));
            String userLine;
            while ((userLine = userReader.readLine()) != null) {
                String[] userInfo = userLine.split(";");
                String username = userInfo[0];
                String displayName = userInfo[1];
                String state = userInfo[2];
                List<String> friends = Arrays.asList(userInfo[3].replaceAll("[\\[\\]]", "").split(","));
                users.put(username, new User(username, displayName, state, friends));
            }
            userReader.close();

            BufferedReader postReader = new BufferedReader(new FileReader(postFilePath));
            String postLine;
            while ((postLine = postReader.readLine()) != null) {
                String[] postInfo = postLine.split(";");
                String postId = postInfo[0];
                String userId = postInfo[1];
                String visibility = postInfo[2];
                posts.put(postId, new Post(postId, userId, visibility));
            }
            postReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean canViewPost(String postId, String username) {
        Post post = posts.get(postId);
        if (post == null) {
            return false;
        }

        User user = users.get(username);
        if (user == null) {
            return false;
        }

        if (post.visibility.equals("public")) {
            return true;
        } else if (post.visibility.equals("friend")) {
            return user.friends.contains(post.userId);
        }

        return false;
    }

    public static List<String> retrievePosts(String username) {
        List<String> accessiblePosts = new ArrayList<>();
        User user = users.get(username);
        if (user != null) {
            for (Post post : posts.values()) {
                if (!post.userId.equals(username) && canViewPost(post.postId, username)) {
                    accessiblePosts.add(post.postId);
                }
            }
        }
        return accessiblePosts;
    }

    public static List<String> searchUsersByLocation(String state) {
        List<String> usersByLocation = new ArrayList<>();
        for (User user : users.values()) {
            if (user.state.equals(state)) {
                usersByLocation.add(user.displayName);
            }
        }
        return usersByLocation;
    }

    public static void main(String[] args) {
        SocialMediaApp app = new SocialMediaApp();
        app.loadData("C:\\Users\\hp\\IdeaProjects\\seassignment2\\data\\src\\user-info.txt", "C:\\Users\\hp\\IdeaProjects\\seassignment2\\data\\src\\post-info.txt");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Load input data");
            System.out.println("2. Check visibility");
            System.out.println("3. Retrieve posts");
            System.out.println("4. Search users by location");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the user information file path: ");
                    String userFilePath = scanner.nextLine();
                    System.out.print("Enter the post information file path: ");
                    String postFilePath = scanner.nextLine();
                    loadData(userFilePath, postFilePath);
                    break;
                case 2:
                    System.out.print("Enter the post ID: ");
                    String postId = scanner.nextLine();
                    System.out.print("Enter the username: ");
                    String username = scanner.nextLine();
                    if (canViewPost(postId, username)) {
                        System.out.println("Access Permitted");
                    } else {
                        System.out.println("Access Denied");
                    }
                    break;
                case 3:
                    System.out.print("Enter the username: ");
                    username = scanner.nextLine();
                    List<String> accessiblePosts = retrievePosts(username);
                    if (accessiblePosts.isEmpty()) {
                        System.out.println("No accessible posts found.");
                    } else {
                        System.out.println("Accessible posts: " + String.join(", ", accessiblePosts));
                    }
                    break;
                case 4:
                    System.out.print("Enter the state location: ");
                    String state = scanner.nextLine();
                    List<String> usersByLocation = searchUsersByLocation(state);
                    if (usersByLocation.isEmpty()) {
                        System.out.println("No users found in the specified location.");
                    } else {
                        System.out.println("Users in the specified location: " + String.join(", ", usersByLocation));
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }
}