import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String paidByUserId;
    private double amount;
    private List<User> users;
    private ExpenseType expenseType;
    private List<Double> expenseShares;
    private String expenseName;
    private String notes;
    private List<String> images;

    public Expense(String paidByUserId, double amount, List<User> users, ExpenseType expenseType,
                   List<Double> expenseShares, String expenseName, String notes, List<String> images) {
        this.paidByUserId = paidByUserId;
        this.amount = amount;
        this.users = users;
        this.expenseType = expenseType;
        this.expenseShares = expenseShares;
        this.expenseName = expenseName;
        this.notes = notes;
        this.images = images;
    }

    public String getPaidByUserId() {
        return paidByUserId;
    }

    public double getAmount() {
        return amount;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public List<Double> getExpenseShares() {
        return new ArrayList<>(expenseShares);
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getNotes() {
        return notes;
    }

    public List<String> getImages() {
        return new ArrayList<>(images);
    }
}
