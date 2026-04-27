import java.util.ArrayList;

public class Transaction {
    private ArrayList<Expense> expenses;

    public Transaction() {
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public double getTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }
}
