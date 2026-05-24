import java.awt.*;
import javax.swing.*;

public class ExpenseTrackerUI extends JFrame {
    private JTextField descriptionField, amountField, dateField, budgetField, removeField;
    private JTextArea listArea;
    private JLabel totalLabel;
    private JLabel budgetLabel;
    private JLabel statusLabel;

    public ExpenseTrackerUI() {
        super("Expense Tracker");
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalLabel = new JLabel("Total: 0.00");
        budgetLabel = new JLabel("Budget: 0.00");
        statusLabel = new JLabel("Status: ");
        JButton refreshBtn = new JButton("Refresh");
        JButton chartBtn = new JButton("Show Chart");
        bottomPanel.add(totalLabel);
        bottomPanel.add(budgetLabel);
        bottomPanel.add(statusLabel);
        bottomPanel.add(refreshBtn);
        bottomPanel.add(chartBtn);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String desc = descriptionField.getText().trim();
            double amt;
            try {
                amt = Double.parseDouble(amountField.getText().trim());
            } catch (Exception ex) {
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

        setBudgetBtn.addActionListener(e -> {
            try {
                double b = Double.parseDouble(budgetField.getText().trim());
                ExpenseTracker.setBudget(b);
                refreshList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid budget");
            }
        });

        removeBtn.addActionListener(e -> {
            try {
                int index = Integer.parseInt(removeField.getText().trim()) - 1;
                if (ExpenseTracker.removeExpense(index)) {
                    removeField.setText("");
                    refreshList();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid expense number");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid expense number");
            }
        });

        refreshBtn.addActionListener(e -> refreshList());
        chartBtn.addActionListener(e -> ExpenseTracker.showChart());

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
        double budget = ExpenseTracker.getBudget();
        totalLabel.setText(String.format("Total: %.2f", total));
        if (budget > 0) {
            budgetLabel.setText(String.format("Budget: %.2f", budget));
            if (total <= budget) {
                statusLabel.setText("Status: Within Budget");
                statusLabel.setForeground(new Color(0, 128, 0));
            } else if (total > budget) {
                statusLabel.setText("Status: Over Budget");
                statusLabel.setForeground(Color.RED);
            }
        } else {
            budgetLabel.setText("Budget: 0.00");
            statusLabel.setText("Status: ");
            statusLabel.setForeground(Color.DARK_GRAY);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTrackerUI().setVisible(true));
    }
}
