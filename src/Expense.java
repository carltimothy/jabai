public class Expense {
    private String description;
    private double amount;
    private String date;

    public Expense(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10.2f %-12s", description, amount, date);
    }
}
