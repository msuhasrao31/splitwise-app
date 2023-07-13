import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        // Dynamic creation and adding of users
        System.out.print("Enter the number of users: ");
        int numUsers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numUsers; i++) {
            System.out.println("Enter details for User " + (i + 1) + ":");
            System.out.print("User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Mobile Number: ");
            String mobileNumber = scanner.nextLine();

            User user = new User(userId, name, email, mobileNumber);
            expenseManager.addUser(user);
        }

        // Add expenses
        System.out.print("Enter the number of expenses: ");
        int numExpenses = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numExpenses; i++) {
            System.out.println("Enter details for Expense " + (i + 1) + ":");
            System.out.print("Paid By User ID: ");
            String paidByUserId = scanner.nextLine();

            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter the number of users involved in the expense: ");
            int numInvolvedUsers = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            List<User> involvedUsers = new ArrayList<>();
            for (int j = 0; j < numInvolvedUsers; j++) {
                System.out.print("User ID of Involved User " + (j + 1) + ": ");
                String userId = scanner.nextLine();
                User user = expenseManager.getUser(userId);
                if (user != null) {
                    involvedUsers.add(user);
                } else {
                    System.out.println("User with ID " + userId + " does not exist. Skipping...");
                }
            }

            System.out.print("Expense Type (EQUAL/EXACT/PERCENT/SHARE): ");
            String expenseTypeStr = scanner.nextLine().toUpperCase();

            ExpenseType expenseType;
            if (expenseTypeStr.equals("EQUAL")) {
                expenseType = ExpenseType.EQUAL;
            } else if (expenseTypeStr.equals("EXACT")) {
                expenseType = ExpenseType.EXACT;
            } else if (expenseTypeStr.equals("PERCENT")) {
                expenseType = ExpenseType.PERCENT;
            } else if (expenseTypeStr.equals("SHARE")) {
                expenseType = ExpenseType.SHARE;
            } else {
                System.out.println("Invalid expense type. Skipping...");
                continue;
            }

            List<Double> expenseShares = null;
            if (expenseType == ExpenseType.EXACT) {
                expenseShares = new ArrayList<>();
                for (int j = 0; j < numInvolvedUsers; j++) {
                    System.out.print("Exact amount for Involved User " + (j + 1) + ": ");
                    double share = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    expenseShares.add(share);
                }
            } else if (expenseType == ExpenseType.PERCENT) {
                expenseShares = new ArrayList<>();
                double totalPercentage = 0.0;
                for (int j = 0; j < numInvolvedUsers; j++) {
                    System.out.print("Percentage for Involved User " + (j + 1) + ": ");
                    double percentage = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    expenseShares.add(percentage);
                    totalPercentage += percentage;
                }
                if (Math.abs(totalPercentage - 100.0) > 0.001) {
                    System.out.println("Total percentage does not add up to 100. Skipping...");
                    continue;
                }
            } else if (expenseType == ExpenseType.SHARE) {
                expenseShares = new ArrayList<>();
                double totalShares = 0.0;
                for (int j = 0; j < numInvolvedUsers; j++) {
                    System.out.print("Share for Involved User " + (j + 1) + ": ");
                    double share = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    expenseShares.add(share);
                    totalShares += share;
                }
                if (Math.abs(totalShares - numInvolvedUsers) > 0.001) {
                    System.out.println("Total shares do not match the number of users. Skipping...");
                    continue;
                }
            }

            System.out.print("Expense Name: ");
            String expenseName = scanner.nextLine();

            System.out.print("Notes: ");
            String notes = scanner.nextLine();

            System.out.print("Enter the number of images: ");
            int numImages = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            List<String> images = new ArrayList<>();
            for (int j = 0; j < numImages; j++) {
                System.out.print("Image URL " + (j + 1) + ": ");
                String imageUrl = scanner.nextLine();
                images.add(imageUrl);
            }

            Expense expense = new Expense(paidByUserId, amount, involvedUsers, expenseType,
                    expenseShares, expenseName, notes, images);
            expenseManager.addExpense(expense);
        }

        while (true) {
            System.out.print("Enter a command (SHOW or SHOW <user-id>): ");
            String command = scanner.nextLine();
            if (command.equals("SHOW")) {
                expenseManager.showAllBalances();
            } else if (command.startsWith("SHOW ")) {
                String[] tokens = command.split(" ");
                if (tokens.length == 2) {
                    String userId = tokens[1];
                    expenseManager.showBalance(userId);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            } else {
                break;
            }
        }

        scanner.close();
    }
}
