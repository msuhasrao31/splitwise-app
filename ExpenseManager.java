import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    private Map<String, User> users;
    private Map<String, Map<String, Double>> balances;
    private List<Expense> expenses;

    public ExpenseManager() {
        users = new HashMap<>();
        balances = new HashMap<>();
        expenses = new ArrayList<>();
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void addExpense(Expense expense) {
        List<User> users = expense.getUsers();
        int numUsers = users.size();
        double totalAmount = expense.getAmount();
        double share;

        if (expense.getExpenseType() == ExpenseType.EQUAL) {
            share = totalAmount / numUsers;
            for (User user : users) {
                if (!user.getUserId().equals(expense.getPaidByUserId())) {
                    addBalance(expense.getPaidByUserId(), user.getUserId(), share);
                }
            }
        } else if (expense.getExpenseType() == ExpenseType.EXACT) {
            List<Double> shares = expense.getExpenseShares();
            double totalShares = 0;

            for (double amount : shares) {
                totalShares += amount;
            }

            if (Math.abs(totalShares - totalAmount) <= 0.001) {
                for (int i = 0; i < numUsers; i++) {
                    User user = users.get(i);
                    if (!user.getUserId().equals(expense.getPaidByUserId())) {
                        addBalance(expense.getPaidByUserId(), user.getUserId(), shares.get(i));
                    }
                }
            } else {
                System.out.println("Total shares do not match the total amount.");
            }
        } else if (expense.getExpenseType() == ExpenseType.PERCENT) {
            List<Double> percentages = expense.getExpenseShares();
            double totalPercentages = 0;

            for (double percentage : percentages) {
                totalPercentages += percentage;
            }

            if (Math.abs(totalPercentages - 100) <= 0.001) {
                for (int i = 0; i < numUsers; i++) {
                    User user = users.get(i);
                    if (!user.getUserId().equals(expense.getPaidByUserId())) {
                        share = (totalAmount * percentages.get(i)) / 100;
                        addBalance(expense.getPaidByUserId(), user.getUserId(), share);
                    }
                }
            } else {
                System.out.println("Total percentages do not add up to 100.");
            }
        } else if (expense.getExpenseType() == ExpenseType.SHARE) {
            List<Double> shares = expense.getExpenseShares();
            double totalShares = 0;

            for (double shareValue : shares) {
                totalShares += shareValue;
            }

            if (Math.abs(totalShares - numUsers) <= 0.001) {
                for (int i = 0; i < numUsers; i++) {
                    User user = users.get(i);
                    if (!user.getUserId().equals(expense.getPaidByUserId())) {
                        share = totalAmount * (shares.get(i) / totalShares);
                        addBalance(expense.getPaidByUserId(), user.getUserId(), share);
                    }
                }
            } else {
                System.out.println("Total shares do not match the number of users.");
            }
        }

        expenses.add(expense);
    }

    private void addBalance(String paidByUserId, String userId, double amount) {
        balances.putIfAbsent(userId, new HashMap<>());
        balances.get(userId).put(paidByUserId, balances.get(userId).getOrDefault(paidByUserId, 0.0) + amount);
    }

    public void showBalance(String userId) {
        if (!balances.containsKey(userId)) {
            System.out.println("No balances");
            return;
        }

        for (Map.Entry<String, Double> entry : balances.get(userId).entrySet()) {
            String lenderId = entry.getKey();
            double amount = entry.getValue();
            String message = amount >= 0 ? userId + " owes " + lenderId + ": " + amount :
                    lenderId + " owes " + userId + ": " + (-amount);
            System.out.println(message);
        }
    }

    public void showAllBalances() {
        for (String userId : users.keySet()) {
            showBalance(userId);
        }
    }

    public void showPassbook(String userId) {
        System.out.println("Passbook for User: " + userId);
        for (Expense expense : expenses) {
            if (expense.getUsers().contains(users.get(userId))) {
                String message = "Expense Name: " + expense.getExpenseName() +
                        ", Paid By: " + users.get(expense.getPaidByUserId()).getName() +
                        ", Amount: " + expense.getAmount() +
                        ", Notes: " + expense.getNotes() +
                        ", Images: " + expense.getImages();
                System.out.println(message);
            }
        }
    }

    public void simplifyExpenses() {
        for (String userId : users.keySet()) {
            Map<String, Double> simplifiedBalances = new HashMap<>();

            for (Map.Entry<String, Double> entry : balances.get(userId).entrySet()) {
                String lenderId = entry.getKey();
                double amount = entry.getValue();

                // Check if the lender also owes money to other users
                if (balances.containsKey(lenderId) && balances.get(lenderId).containsKey(userId)) {
                    double lenderOwedAmount = balances.get(lenderId).get(userId);
                    double simplifiedAmount = amount - lenderOwedAmount;
                    simplifiedBalances.put(lenderId, simplifiedAmount);
                } else {
                    simplifiedBalances.put(lenderId, amount);
                }
            }

            balances.put(userId, simplifiedBalances);
        }
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}
