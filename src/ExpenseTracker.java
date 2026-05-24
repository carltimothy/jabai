import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

public class ExpenseTracker {
    static Transaction transaction = new Transaction();
    static double budget = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    setBudget(scanner);
                    break;
                case 4:
                    showTotal();
                    break;
                case 5:
                    showWeeklySummary();
                    break;
                case 6:
                    showChart();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 7);
        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("\n==== EXPENSE TRACKER ====");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Set Budget");
        System.out.println("4. Show Total");
        System.out.println("5. Weekly Summary");
        System.out.println("6. Show Chart");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    public static void addExpense(Scanner scanner) {
        System.out.print("Enter expense description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        if (amount < 0) {
            System.out.println("Amount should be a positive number.");
            return;
        } else {
            scanner.nextLine();
        }

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        transaction.addExpense(new Expense(description, amount, date));
        System.out.println("Expense added successfully!");
    }

    public static void addExpense(String description, double amount, String date) {
        transaction.addExpense(new Expense(description, amount, date));
    }

    public static boolean removeExpense(int index) {
        return transaction.removeExpense(index);
    }

    public static void viewExpenses() {
        ArrayList<Expense> allExpenses = transaction.getExpenses();
        if (allExpenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        System.out.printf("%-4s %-20s %-10s %-12s\n", "No.", "Description", "Amount", "Date");
        int i = 1;
        for (Expense e : allExpenses) {
            System.out.printf("%-4d %s\n", i++, e.toString());
        }
        double b = budget;
        double total = calculateTotal();
        System.out.println("\nBudget: " + b);
        if (budget > 0) {
            if (total <= budget) {
                System.out.println("Status: Within Budget");
            } else if (total > budget) {
                System.out.println("Status: Over Budget!");
            }
        }
    }

    public static double calculateTotal() {
        return transaction.getTotal();
    }

    public static void showTotal() {
        double total = calculateTotal();
        System.out.println("\nTotal Expenses: " + total);
        if (budget > 0) {
            System.out.println("Budget: " + budget);
            if (total <= budget) {
                System.out.println("You are within your budget.");
            } else if (total > budget) {
                System.out.println("Warning: You have exceeded your budget!");
            }
        }
    }

    public static void setBudget(Scanner scanner) {
        System.out.print("Enter your budget: ");
        budget = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Budget set to: " + budget);
    }

    public static void setBudget(double b) {
        budget = b;
    }

    public static double getBudget() {
        return budget;
    }

    public static java.util.List<Expense> getExpensesList() {
        return transaction.getExpenses();
    }

    public static void showWeeklySummary() {
        ArrayList<Expense> allExpenses = transaction.getExpenses();
        if (allExpenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        java.util.Map<Integer, Double> weekTotals = new java.util.HashMap<>();
        for (Expense e : allExpenses) {
            try {
                LocalDate date = LocalDate.parse(e.getDate(), formatter);
                int week = date.get(weekFields.weekOfWeekBasedYear());
                weekTotals.put(week, weekTotals.getOrDefault(week, 0.0) + e.getAmount());
            } catch (Exception ex) {
                
            }
        }
        if (weekTotals.isEmpty()) {
            System.out.println("No valid dated expenses to summarize.");
            return;
        }
        System.out.println("\n--- Weekly Summary ---");
        for (Integer week : weekTotals.keySet()) {
            System.out.printf("Week %d: %.2f\n", week, weekTotals.get(week));
        }
    }

    public static void showChart() {
        if (transaction.getExpenses().isEmpty()) {
            System.out.println("No expenses to chart.");
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Expense e : transaction.getExpenses()) {
            dataset.addValue(e.getAmount(), "Expenses", e.getDescription() + " (" + e.getDate() + ")");
        }
        JFreeChart barChart = ChartFactory.createBarChart(
                "Expenses Chart",
                "Expense",
                "Amount",
                dataset
        );
        JFrame frame = new JFrame("Expenses Chart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ChartPanel(barChart));
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
