import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.ArrayList;

public class Transaction {
    private ArrayList<Expense> expenses;

    public Transaction() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    public boolean removeExpense(int index) {
        if (index < 0 || index >= expenses.size()) {
            return false;
        }
        expenses.remove(index);
        saveExpenses();
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

    private void saveExpenses() {
        try (FileWriter writer = new FileWriter("data.json")) {
            Gson gson = new Gson();
            gson.toJson(expenses, writer);
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        try (FileReader reader = new FileReader("data.json")) {
            Gson gson = new Gson();
            expenses = gson.fromJson(reader, new TypeToken<List<Expense>>() {}.getType());
            if (expenses == null) {
                expenses = new ArrayList<>();
            }
        } catch (IOException e) {
            expenses = new ArrayList<>();
        }
    }
}