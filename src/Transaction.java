import java.util.ArrayList;

public class Transaction {
    private ArrayList<Expense> expenses;

    public Transaction() {
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public boolean removeExpense(int index) {
        if (index < 0 || index >= expenses.size()) {
            return false;
        }
        expenses.remove(index);
        return true;
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
