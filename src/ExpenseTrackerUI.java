import java.awt.*;
import javax.swing.*;

public class ExpenseTrackerUI extends JFrame {
    private JTextField descriptionField, amountField, dateField, budgetField, removeField;
    private JTextArea listArea;
    private JLabel totalLabel;
    private JLabel budgetLabel;
    private JLabel statusLabel;

    public ExpenseTrackerUI() {
        super("CentSible: Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        descriptionField = new JTextField();
        amountField = new JTextField();
        dateField = new JTextField();
        JButton addBtn = new JButton("Add Expense");

        inputPanel.add(new JLabel("Expense Name:"));
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(new JLabel(""));

        inputPanel.add(descriptionField);
        inputPanel.add(amountField);
        inputPanel.add(dateField);
        inputPanel.add(addBtn);

        JPanel budgetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        budgetField = new JTextField(10);
        JButton setBudgetBtn = new JButton("Set Budget");
        budgetPanel.add(new JLabel("Budget:"));
        budgetPanel.add(budgetField);
        budgetPanel.add(setBudgetBtn);

        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removeField = new JTextField(10);
        JButton removeBtn = new JButton("Remove Expense");
        removePanel.add(new JLabel("Remove #:"));
        removePanel.add(removeField);
        removePanel.add(removeBtn);

        listArea = new JTextArea();
        listArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(listArea);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(budgetPanel, BorderLayout.SOUTH);
        topPanel.add(removePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(12, 0));
        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: ₱0.00");
        budgetLabel = new JLabel("Budget: ₱0.00");
        statusLabel = new JLabel("Status: ");
        JButton refreshBtn = new JButton("Refresh");
        JButton chartBtn = new JButton("Show Chart");
        JButton resetBtn = new JButton("Reset");
        bottomLeftPanel.add(totalLabel);
        bottomLeftPanel.add(budgetLabel);
        bottomLeftPanel.add(refreshBtn);
        bottomLeftPanel.add(chartBtn);
        bottomLeftPanel.add(resetBtn);
        bottomRightPanel.add(statusLabel);
        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String desc = descriptionField.getText().trim();
            double amt;
            try {
                amt = Double.parseDouble(amountField.getText().trim());
                statusLabel.setText("Status: Add expense");
                statusLabel.setForeground(Color.DARK_GRAY);
            } catch (Exception ex) {
                statusLabel.setText("Status: " + ex);
                statusLabel.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Invalid amount");
                return;
            }
            String date = dateField.getText().trim();
            if (amt < 0) {
            } else {
                ExpenseTracker.addExpense(desc, amt, date);
                refreshList();
                descriptionField.setText("");
                amountField.setText("");
                dateField.setText("");
            }
        });

        resetBtn.addActionListener(e -> {
            descriptionField.setText("");
            amountField.setText("");
            dateField.setText("");
            budgetField.setText("");
            removeField.setText("");
            ExpenseTracker.resetAll();
            refreshList();
            statusLabel.setText("Status: Reset");
            statusLabel.setForeground(Color.DARK_GRAY);
        });

        chartBtn.addActionListener(e -> {
            if (ExpenseTracker.getExpensesList().isEmpty()) {
                try {
                    statusLabel.setText("Status: Failed to show chart. Expenses list is empty!");
                    statusLabel.setForeground(Color.RED);
                } catch (Exception ex) {
                    statusLabel.setText("Status: " + ex);
                }
            } else {
                statusLabel.setText("Status: Showing Chart.");
                statusLabel.setForeground(Color.DARK_GRAY);
                ExpenseTracker.showChart();
            }
        });

        setBudgetBtn.addActionListener(e -> {
            try {
                double b = Double.parseDouble(budgetField.getText().trim());
                if (Double.compare(b, 0.0) >= 0 && Double.doubleToRawLongBits(b) != Double.doubleToRawLongBits(-0.0)) {
                    ExpenseTracker.setBudget(b);
                    statusLabel.setText("Status: Budget set");
                    statusLabel.setForeground(Color.DARK_GRAY);
                    refreshList();
                } else {
                    statusLabel.setText("Status: Enter a positive number");
                    statusLabel.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(this, "Enter a positive number");
                }
            } catch (Exception ex) {
                statusLabel.setText("Status: " + ex);
                statusLabel.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Invalid budget");
            }
        });

        removeBtn.addActionListener(e -> {
            try {
                int index = Integer.parseInt(removeField.getText().trim()) - 1;
                if (ExpenseTracker.removeExpense(index)) {
                    removeField.setText("");
                    statusLabel.setText("Status: Removed successfully");
                    statusLabel.setForeground(Color.DARK_GRAY);
                    refreshList();
                } else {
                    statusLabel.setText("Status: Please enter a valid expense number");
                    statusLabel.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(this, "Please enter a valid expense number");
                }
            } catch (Exception ex) {
                statusLabel.setText("Status: " + ex);
                statusLabel.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Please enter a valid expense number");
            }
        });

        refreshBtn.addActionListener(e -> {
            statusLabel.setText("Status: Refreshed Successfully");
            statusLabel.setForeground(Color.DARK_GRAY);
            refreshList();
        });
        refreshList();
    }

    private void refreshList() {
        StringBuilder sb = new StringBuilder();
        java.util.List<Expense> list = ExpenseTracker.getExpensesList();
        int i = 1;
        for (Expense exp : list) {
            sb.append(String.format("%-4d %s\n", i++, exp.toString()));
        }
        listArea.setText(sb.toString());
        double total = ExpenseTracker.calculateTotal();
        totalLabel.setText(String.format("Total: ₱%,.2f", total));
        if (ExpenseTracker.getBudget() > 0) {
            budgetStatus();
        } else {
            budgetStatus();
        }
    }

    public String budgetStatus() {
        double total = ExpenseTracker.calculateTotal();
        double budget = ExpenseTracker.getBudget();
        if (budget >= 0) {
            String formatBudget = String.format("Budget: ₱%,.2f", budget);
            if (total <= budget) {
                String b = "Within Budget";
                budgetLabel.setText(formatBudget + "|" + b);
                budgetLabel.setForeground(new Color(0, 128, 0));
                return b;
            } else if (total > budget) {
                String b = "Over Budget!";
                budgetLabel.setText(formatBudget + "|" + b);
                budgetLabel.setForeground(Color.RED);
                return b;
            }
        }
        String empty = "";
        return empty;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTrackerUI().setVisible(true));
    }
}
