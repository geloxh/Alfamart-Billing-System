import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// Class representing a product
class Product {
    private String name;
    private double price;
    private int quantity;

    // Constructor for the Product class
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters for the product name
    public String getName() {
        return name;
    }

    // Getters for the product price
    public double getPrice() {
        return price;
    }

    // Getters for the product quantity
    public int getQuantity() {
        return quantity;
    }
}

// Class representing a shopping cart
class ShoppingCart {
    private List<Product> items;
    private double discount;

    // Constructor for the ShoppingCart class
    public ShoppingCart() {
        items = new ArrayList<>();
        discount = 0.0;
    }

    // Add a product to the cart
    public void addItem(Product product) {
        items.add(product);
    }

    // Remove a product from the cart based on the index
    public void removeItem(int index) {
        items.remove(index);
    }

    // Calculate the total cost of all Items in the cart after applying the discount
    public double calculateTotal() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        total -= discount;
        return total;
    }

    // Set the discount amount
    public void setDiscount (double discount) {
        this.discount = discount;
    }

    // Print the invoice with details of all items, the total cost, and the applied discount
    public void printInvoice() {
        System.out.println("------------- INVOICE -------------");
        for (int i = 0; i < items.size(); i++) {
            Product item = items.get(i);
            System.out.println((i + 1) + ". " + item.getName() + "\tPhp" + item.getPrice() + "\tQty: " + item.getQuantity());
        }
        System.out.println("-----------------------------------");
        System.out.println("Total: Php" + calculateTotal());
        System.out.println("Discount: Php" + discount);
        System.out.println("-----------------------------------"); 
    }

    public int getCartSize() {
        return items.size();
    }

    public Product getItem(int i) {
        return items.get(i);
    }

    // Save the invoice to a file
    public void saveInvoiceToFile() {
        try (FileWriter writer = new FileWriter("invoice.txt")) {
            writer.write("------------- INVOICE -------------\n");
            for (int i = 0; i < items.size(); i++) {
                Product item = items.get(i);
                writer.write((i + 1) + ". " + item.getName() + "\tPhp" + item.getPrice() + "\tQty: " + item.getQuantity() + "\n");
            }
            writer.write("-----------------------------------\n");
            writer.write("Total: Php" + calculateTotal() + "\n");
            writer.write("Discount: Php" + discount + "\n");
            System.out.println("Invoice save to file.");
        } catch (IOException e) {
            System.out.println("Failed to save invoice to file: " + e.getMessage());
        }
    }
}

// Main class representing the Billing System
public class BillingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static ShoppingCart cart = new ShoppingCart();

    // Entry point of the program
    public static void main(String[] args) {
        showMenu();
    }

    // Display the main menu and handle user input
    private static void showMenu() {
        int choice;
        do {
            System.out.println("------------- Alfamart Billing System Menu -------------");
            System.out.println("1. Add Item to cart");
            System.out.println("2. Remove Item from cart");
            System.out.println("3. View Cart");
            System.out.println("4. Apply discount");
            System.out.println("5. Generate Invoice");
            System.out.println("6. Download Invoice");
            System.out.println("7. Exit");
            System.out.println("-----------------------------------------------");
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItemToCart();
                    break;
                case 2:
                    removeItemFromCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    applyDiscount();
                    break;
                case 5:
                    generateInvoice();
                    break;
                case 6:
                    downloadInvoice();
                    break;
                case 7:
                    System.out.println("Thank you for using the Alfamart Billing System :-)");
                    break;
            }
        } while (choice != 7);
    }

    // Add an item to the cart based on user input
    private static void addItemToCart() {
        scanner.nextLine(); // clear the newline left after previous input

        System.out.print("Enter product name: ");
        String name = scanner.nextLine(); // Can Input names with spcaes
        
        double price = 0.0;
        while (true) {
            System.out.print("Enter product price: ");
            String priceInput = scanner.nextLine();
            try {
                price = Double.parseDouble(priceInput);
                break; // Exit the loop if parsing is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value for price"); 
            }
        }

        int quantity = 0;
        while (true) {
            System.out.print("Enter Product Quantity: ");
            String quantityInput = scanner.nextLine();
            try {
                quantity = Integer.parseInt(quantityInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number for quantity.");
            }
        }

        Product product = new Product(name, price, quantity);
        cart.addItem(product);
        System.out.println("Item successfully added to cart!");
    }
                

    // Remove an item from the cart based on user input
    private static void removeItemFromCart() {
        System.out.print("Enter the item number to remove: ");
        int index = scanner.nextInt() - 1;

        if (index >= 1 && index <= cart.getCartSize()) {
            cart.removeItem(index);
            System.out.println("Item removed from cart!");
        } else {
            System.out.println("Invalid item number.");
        }
    }

    // View the current items in the cart along with the total cost
    private static void viewCart() {
        if (cart.getCartSize() > 0) {
            System.out.println("------------- CART ITEMS -------------");
            for (int i = 0; i < cart.getCartSize(); i++) {
                Product item = cart.getItem(i);
                System.out.println((i + 1) + ". " + item.getName() + "\tPhp" + item.getPrice() + "\tQty: " + item.getQuantity());
            }
            System.out.println("--------------------------------------");
            System.out.println("Total: Php" + cart.calculateTotal());
            System.out.println("--------------------------------------");
        } else {
            System.out.println("Cart is empty!");
        }
    }
    
    // Apply a discount to the total cost based on user input
    private static void applyDiscount() {
        System.out.print("Enter discount amount: ");
        double discount = scanner.nextDouble();
        cart.setDiscount(discount);
        System.out.println("Disount successfully applied!");
    }

    // Generate an invoice for the current cart and clear the cart
    private static void generateInvoice() {
        if (cart.getCartSize() > 0) {
            cart.printInvoice();
            cart = new ShoppingCart(); // Clear the cart after generating invoice
        } else {
            System.out.println("Cart is empty! Cannot generate invoice.");
        }
    }

    // Download the invoice as a file
    private static void downloadInvoice() {
        if (cart.getCartSize() > 0) {
            cart.saveInvoiceToFile();
            cart = new ShoppingCart(); // Clear the cart after downloading invoice
        } else {
            System.out.println("Cart is empty! Cannot download invoice.");
        }
    }
}