void constants() {
    try(Scanner scanner = new Scanner(System.in)) {
        System.out.print("Enter product1 name: ");
        String product1 = scanner.nextLine();
        System.out.print("Enter product1 price: ");
        double price1 = scanner.nextDouble();
        System.out.print("");
        System.out.print("Enter product2 name: ");
        String product2 = scanner.nextLine();
        System.out.print("Enter product2 price: ");
        double price2 = scanner.nextDouble();
        System.out.print("Enter product3 name: ");
        String product3 = scanner.nextLine();
        System.out.print("Enter product3 price: ");
        double price3 = scanner.nextDouble();
        double total = price1 + price2 + price3;
        System.out.println("Total: " + total);
        final double discount = 0.15;
        double calculate = total - discount * total;
        System.out.println("Total (after discount): " + calculate);
        }
    }
