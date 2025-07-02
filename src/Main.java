import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        String username = null;
        OuterLoop:
        while (true) {
            InnerLoop:
            while (true) {
                System.out.println("1. Register\n2. Login\n3. Exit");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    System.out.print("Enter a username: ");
                    String user = sc.nextLine();
                    if (auth.register(user)) {
                        System.out.println("Registered. Use your login code to log in.");
                    } else {
                        System.out.println("Username already exists.");
                    }
                } else if (choice == 2) {
                    System.out.print("Username: ");
                    String user = sc.nextLine();
                    System.out.print("Login Code: ");
                    String code = sc.nextLine();
                    if (auth.login(user, code)) {
                        username = user;
                        break;
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                } else {
                    break OuterLoop;
                }
            }

            ExpenseService service = new ExpenseService(username);
            while (true) {
                System.out.println("\n1. Add Expense\n2. Show Total Expense\n3. Show Total Expense by category\n4. Show expense trend\n5. Show Highest and Lowest Spend Category\n6. Sign out");
                int option = Integer.parseInt(sc.nextLine());
                if (option == 1) {
                    String cat = "";
                    while (true) {
                        System.out.print("Category: ");
                        cat = sc.nextLine().trim();
                        if (!cat.isEmpty()) break;
                        System.out.println("Category cannot be empty.");
                    }

                    double amt = 0.0;
                    while (true) {
                        System.out.print("Amount: ");
                        String amtInput = sc.nextLine().trim();
                        try {
                            amt = Double.parseDouble(amtInput);
                            if (amt <= 0) {
                                System.out.println("Amount must be greater than zero.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Please enter a valid amount.");
                        }
                    }

                    String date = "";
                    String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$"; // YYYY-MM-DD
                    while (true) {
                        System.out.print("Date (YYYY-MM-DD): ");
                        date = sc.nextLine().trim();
                        if (date.matches(dateRegex)) break;
                        System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    }

                    service.addExpense(new Expense(cat, amt, date));
                } else if (option == 2) {
                    double total = service.getTotalExpense();
                    System.out.println("Total: " + total);
                } else if (option == 3) {
                    Map<String, Double> byCategory = service.getExpenseByCategory();
                    System.out.println("\nCategory:\tExpense");
                    for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
                        System.out.println("\n" + entry.getKey() + "\t" + entry.getValue().toString());
                    }
                } else if (option == 4) {
                    service.getExpenseTrend();
                } else if (option == 5) {
                    service.getHighLowCategory();
                } else {
                    break;
                }
            }
        }
    }
}
