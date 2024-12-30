package com.esoft;

import com.esoft.builder.FeedbackBuilder;
import com.esoft.builder.PizzaBuilder;
import com.esoft.chains.BlackFridayPromotionHandler;
import com.esoft.chains.PromotionHandler;
import com.esoft.config.DBConnection;
import com.esoft.models.*;
import com.esoft.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class Main {
    public static StringBuilder toppings = new StringBuilder();
    public static Map<Integer, Toppings> toppingsMap;
    public static Map<Integer, Size> sizeMap;
    public static Map<String, Order> orderPizza;
    public static List<Map> orderList;
    private static final FeedbackService feedbackService = new FeedbackService();
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Users> users = new ArrayList<>();
    private static Users user;
    private static final UserSerivice userSerivice = new UserSerivice();
    private static Map<Integer, Pizza> pizzaMap;
    private int selectedSizeId; // Add this field to the PizzaBuilder class
    private static Map<String, Favourite> favouriteMap = new HashMap<>();
    private static List<Toppings> toppingsforFav;
    private static Pizza pizzaForFav;
    public static void main(String[] args) throws SQLException {
        users = userSerivice.getUsers();
        PizzaService pizzaService = new PizzaService();
        orderPizza = new HashMap<>();
        // Fetch data and populate maps
        pizzaMap = pizzaService.getAllPizza();
        toppingsMap = pizzaService.getAllToppings();
        sizeMap = pizzaService.getAllSize();

        System.out.println("Welcome to the PizzaApp");
        while (true) {
            CreateOrLogin();
        }
    }

    private static void handleUser() {
        if (user.getRole().equalsIgnoreCase("Customer")) {
            System.out.println("Do You Want to see Your!");
            System.out.println("1. Account");
            System.out.println("2. Homepage");
            int selection = scanner.nextInt();
            if (selection == 1) {
                showAccount();
            } else if (selection == 2) {
                handlePizza(BigDecimal.ZERO);
            }
        } else if (user.getRole().equalsIgnoreCase("Admin")) {
            handleAdmin();
        }
    }

    private static void showAccount() {
        System.out.println("Welcome User Account .");
        System.out.println(user.getUsername());
        System.out.println("Your Favourite Items");
        viewFavourites();
        try {
            showLoyaltiPoints();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showFavourite() {
        System.out.println("Favorite Pizza");
        System.out.println("Select Favourite Pizza For Buy");
        for(Map.Entry<String, Favourite> entry : favouriteMap.entrySet()){
            System.out.println(" "+entry.getKey()+ " : "+ entry.getValue().getPizza().getName());
            System.out.println( "  "+entry.getValue().getPizza().getDescription());
            System.out.println("___________________");
        }
    }

    private static void handleAdmin() {
        System.out.println("Select The item You want to do !");
        System.out.println("1. Show Ordered Pizza");
        System.out.println("2. Show All Pizza");
        System.out.println("3. Add New Pizza");
        System.out.println("4. Show Customers");
        int selection = scanner.nextInt();
        if (selection == 1) {
            showOrderedPizza();
        } else if (selection == 2) {
            showAllPizza();
        } else if (selection == 3) {
            addNewPizza();
        } else if (selection == 4) {
            showAllCustomer();
        }
    }

    private static void showAllCustomer() {
        // Display all registered customers
        System.out.println("List of all customers:");
        for (Users customer : users) {
            if (customer.getRole().equalsIgnoreCase("Customer")) {
                System.out.println("Username: " + customer.getUsername() + ", Email: " + customer.getEmail());
            }
        }
    }

    private static void showAllPizza() {
        // Display all available pizzas
        System.out.println("List of all pizzas:");
        for (Pizza pizza : pizzaMap.values()) {
            System.out.println("Pizza ID: " + pizza.getPizzaId());
            System.out.println("  Name: " + pizza.getName());
            System.out.println("  Description: " + pizza.getDescription());
            System.out.println("  Base Price: $" + pizza.getBasePrice());
        }
    }


    private static void handlePizza(BigDecimal price) {
        try {
            for (Pizza pizza : pizzaMap.values()) {
                System.out.println("Pizza ID: " + pizza.getPizzaId());
                System.out.println("  Name: " + pizza.getName());
                System.out.println("  Description: " + pizza.getDescription());
                System.out.println("  Base Price: $" + pizza.getBasePrice());
            }

            System.out.println("Select a Pizza ID:");
            int id = getValidIntegerInput();
            Pizza npizza = pizzaMap.get(id);
            pizzaForFav = npizza;
            System.out.println("Do You Want:");
            System.out.println("1. Buy Pizza");
            System.out.println("2. Customize Pizza");
            int decPizza = getValidIntegerInput();

            if (decPizza == 1) {
                selectPizzaSize(id,null);
            } else if (decPizza == 2) {
                decoratePizza(id, BigDecimal.valueOf(0));
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
                handlePizza(price);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + ". Please try again.");
            handlePizza(price);
        }
    }

    private static void decoratePizza(int id, BigDecimal basePrice) {
        Pizza pizza = pizzaMap.get(id);
        if (pizza == null) {
            System.out.println("Invalid Pizza ID!");
            return;
        }

        // Use the PizzaBuilder to customize the pizza
        PizzaBuilder pizzaBuilder = new PizzaBuilder(pizza);

        boolean pizzaToppings = true;
        while (pizzaToppings) {
            try {
                // Display available toppings and allow the user to select them
                for (Toppings topping : toppingsMap.values()) {
                    System.out.println("Topping ID: " + topping.getId() +
                            " | Topping: " + topping.getTopping() +
                            " | Price: $" + topping.getPrice());
                }

                System.out.println("\nEnter Your Topping IDs (comma-separated):");
                scanner.nextLine(); // Consume newline
                String input = scanner.nextLine();
                String[] toppingIds = input.split(",");

                for (String toppingIdStr : toppingIds) {
                    try {
                        int toppingId = Integer.parseInt(toppingIdStr.trim());
                        pizzaBuilder.addTopping(toppingId); // Add topping using the builder
                        Toppings toppings1 = toppingsMap.get(toppingId);
                        toppingsforFav.add(toppings1);
                        if (toppings1 != null) {
                            String tp = toppings1.getTopping();
                            if (toppings.length() > 0) {
                                toppings.append(", "); // Add a comma and space between toppings
                            }
                            toppings.append(tp); // Append the topping name
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: " + toppingIdStr + ". Please enter valid Topping IDs.");
                    }
                }

                System.out.println("Do You Want to Add More Toppings?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = getValidIntegerInput();
                if (choice == 2) {
                    System.out.println("Came debug1");
                    pizzaToppings = false;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + ". Please try again.");
            }
        }

        selectPizzaSize(id, pizzaBuilder);
    }

    private static void selectPizzaSize(int id, @NotNull PizzaBuilder pizzaBuilder) {
        boolean selection = true;
        int size1 = 2;
        BigDecimal grandTotal = null;
        while (selection) {
            try {
                grandTotal = BigDecimal.ZERO;

                System.out.println("Select Your Size:");
                for (Size size : sizeMap.values()) {
                    System.out.println(size.getId() + ". " + size.getSize() + " - $" + size.getPrice());
                }

                // Input size ID
                System.out.print("Enter Size ID: ");
                int sizeId = getValidIntegerInput();
                size1 = sizeId;
                // Validate size ID
                if (!sizeMap.containsKey(sizeId)) {
                    System.out.println("Invalid Size ID. Please try again.");
                    continue;
                }
                pizzaBuilder.selectSize(sizeId);

                // Input quantity
                int qty = 0;
                while (qty <= 0) {
                    System.out.print("Select Quantity (must be greater than 0): ");
                    qty = getValidIntegerInput();
                    if (qty <= 0) {
                        System.out.println("Quantity must be greater than zero. Please try again.");
                    }
                }
                pizzaBuilder.setQuantity(qty);

                // Build and calculate total price
                grandTotal = pizzaBuilder.build();
                System.out.println("Your Total Price for this selection is: $" + grandTotal);

                // Ask if the user wants another size or pizza
                System.out.println("Do You Want Another Size Pizza?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.println("3. Customize Another Pizza");
                int another = getValidIntegerInput();

                if (another == 2) {
                    selection = false;
                } else if (another == 3) {
                    pizzaBuilder.reset(); // Reset builder for a new pizza
                    handlePizza(grandTotal);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + ". Please try again.");
            }
        }

        System.out.println("Your Grand Total for All Selections is: $" + grandTotal);
        buyPizza(id, size1, grandTotal);
    }

    private static void buyPizza(int id, int size, BigDecimal total) {
        Pizza pizza = pizzaMap.get(id);
        if (pizza != null) {
            PizzaBuilder pizzaBuilder = new PizzaBuilder(pizza);
            pizzaBuilder.selectSize(size);
            BigDecimal basePrice = total;

            // Create the promotion chain
            PromotionHandler blackFridayPromotion = new BlackFridayPromotionHandler();

            // Apply promotions
            BigDecimal discountedPrice = blackFridayPromotion.applyPromotion(basePrice);

            // Get details from the builder
            String selectedSize = pizzaBuilder.getSelectedSize();
            int quantity = pizzaBuilder.getQuantity();

            // Create and store the order
            Order order = new Order(pizza, "Ordered", pizzaBuilder.getSelectedToppings(), quantity, selectedSize);
            orderPizza.put(UUID.randomUUID().toString(), order);

            System.out.println("Buying Pizza: " + pizza.getName());
            System.out.println("Original Price: $" + basePrice);
            System.out.println("Discounted Price: $" + discountedPrice);

            if (true) { // Replace with actual payment validation
                System.out.println("Payment successful! Your pizza is on its way.");

                // Prepare JSON output
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Map<String, Object> pizzaDetails = new LinkedHashMap<>();
                pizzaDetails.put("Pizza Name", pizza.getName());
                pizzaDetails.put("Base Price", pizza.getBasePrice());
                pizzaDetails.put("Selected Toppings", pizzaBuilder.getSelectedToppings());
                pizzaDetails.put("Selected Size", pizzaBuilder.getSelectedSize());
                pizzaDetails.put("Quantity", pizzaBuilder.getQuantity());
                pizzaDetails.put("Total Price (Discounted)", discountedPrice);

                // Print JSON
                String jsonOutput = gson.toJson(pizzaDetails);
                System.out.println("Your Purchased Pizza Details (JSON Format):");
                System.out.println(jsonOutput);
            } else {
                System.out.println("Payment failed. Please try again.");
            }
        } else {
            System.out.println("Invalid Pizza ID!");
        }

        System.out.println("Do You Want to Save This Pizza to Favorites?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int type = getValidIntegerInput();
        if (type == 1) {
            addToFavourite();
        } else {
            provideFeedback(id);
        }
    }


    private static boolean processPayment(BigDecimal totalPrice) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter your card details to complete the payment:");

        // Card Number (simulate basic validation)
        System.out.print("Card Number (16 digits): ");
        String cardNumber = scanner.nextLine();
        if (!isValidCardNumber(cardNumber)) {
            System.out.println("Invalid card number. Please try again.");
            return false;
        }

        // Expiry Date (simulate basic validation)
        System.out.print("Card Expiry Date (MM/YY): ");
        String expiryDate = scanner.nextLine();
        if (!isValidExpiryDate(expiryDate)) {
            System.out.println("Invalid expiry date. Please try again.");
            return false;
        }

        // CVV (simulate basic validation)
        System.out.print("Card CVV (3 digits): ");
        String cvv = scanner.nextLine();
        if (!isValidCvv(cvv)) {
            System.out.println("Invalid CVV. Please try again.");
            return false;
        }

        // Simulate payment processing (a simple mock payment success)
        System.out.println("\nProcessing payment...");
        try {
            Thread.sleep(2000); // Simulate processing time (2 seconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate a successful payment (you can make this random if needed)
        return Math.random() > 0.1; // 90% chance of successful payment
    }

    @Contract(pure = true)
    private static boolean isValidCardNumber(@NotNull String cardNumber) {
        // A simple validation check: should be exactly 16 digits (no spaces or dashes)
        return cardNumber.matches("\\d{16}");
    }

    @Contract(pure = true)
    private static boolean isValidExpiryDate(@NotNull String expiryDate) {
        // A simple validation check: MM/YY format
        return expiryDate.matches("(0[1-9]|1[0-2])\\/\\d{2}");
    }

    @Contract(pure = true)
    private static boolean isValidCvv(@NotNull String cvv) {
        // A simple validation check: CVV should be 3 digits
        return cvv.matches("\\d{3}");
    }


    private static void CreateOrLogin() throws SQLException {
        System.out.println("Login or Register to the Pizza App:");
        System.out.println("1. Login");
        System.out.println("2. Create Account");

        int account = getValidIntegerInput();

        if (account == 1) {
            loginAccount();
        } else if (account == 2) {
            try {
                createAccount();
            } catch (SQLException e) {
                System.out.println("Error creating account: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
            CreateOrLogin();
        }
    }

    private static void createAccount() throws SQLException {
        System.out.println("Create a New Account:");

        // Validate and prompt for each field with input checks
        String username = null;
        while (username == null || username.isEmpty()) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            }
        }

        String email = null;
        while (email == null || email.isEmpty()) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
            }
        }

        String password = null;
        while (password == null || password.isEmpty()) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        }

        String phoneNumber = null;
        while (phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.print("Enter Phone Number: ");
            phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.isEmpty()) {
                System.out.println("Phone number cannot be empty. Please try again.");
            }
        }

        String address = null;
        while (address == null || address.isEmpty()) {
            System.out.print("Enter Address: ");
            address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please try again.");
            }
        }

        String role = null;
        while (role == null || (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Customer"))) {
            System.out.print("Enter Role (Admin/Customer): ");
            role = scanner.nextLine().trim();
            if (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Customer")) {
                System.out.println("Invalid role. Please enter 'Admin' or 'Customer'.");
            }
        }

        // Create a new user object and save it using the service
        Users newUser = new Users(0, username, email, password, phoneNumber, address, role);
        userSerivice.createUser(newUser);
        users = userSerivice.getUsers(); // Refresh the user list

        System.out.println("Account successfully created!");
        loginAccount(); // Redirect to login after successful account creation
    }



    private static void loginAccount() throws SQLException {
        String username = null;
        String password = null;

        // Loop until a valid username is entered
        while (username == null || username.isEmpty()) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine().trim(); // Trim to avoid leading/trailing spaces
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            }
        }

        // Prompt for password only after a valid username
        while (password == null || password.isEmpty()) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        }

        AuthService authService = new AuthService(users); // Ensure `users` is populated correctly
        user = authService.login(username, password);

        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + " (Role: " + user.getRole() + ")");
            System.out.println(user.getUserId());
            favouriteMap = new FavouriteService().getAllFavourite(user);
            handleUser(); // Proceed to user-specific functionality

        } else {
            System.out.println("Login failed. Please check your username and password.");
            System.out.println("1. Try Again");
            System.out.println("2. Create Account");

            int choice = getValidIntegerInput(); // Helper to validate numeric input
            if (choice == 1) {
                loginAccount();
            } else if (choice == 2) {
                createAccount(); // Assuming a `createAccount()` method exists
            } else {
                System.out.println("Invalid choice. Returning to main menu.");
                CreateOrLogin(); // Redirect to the main menu
            }
        }
    }


    private static int getValidIntegerInput() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void provideFeedback(int pizzaId) {
        if (!pizzaMap.containsKey(pizzaId)) {
            System.out.println("Invalid Pizza ID!");
            return;
        }

        System.out.println("Provide Your Feedback for Pizza: " + pizzaMap.get(pizzaId).getName());
        System.out.println("Enter Rating (1 to 5):");
        int rating = getValidIntegerInput();

        System.out.println("Enter Your Comment:");
        scanner.nextLine(); // Consume newline
        String comment = scanner.nextLine();

        Feedback feedback = new FeedbackBuilder()
                .setRating(rating)
                .setComment(comment)
                .build();

        feedbackService.addFeedback(pizzaId, feedback);
        System.out.println("Thank you for your feedback!");
        viewFeedback(pizzaId);
    }

    private static void viewFeedback(int pizzaId) {
        if (!pizzaMap.containsKey(pizzaId)) {
            System.out.println("Invalid Pizza ID!");
            return;
        }

        System.out.println("Feedback for Pizza: " + pizzaMap.get(pizzaId).getName());
        List<Feedback> feedbackList = feedbackService.getFeedbackForPizza(pizzaId);

        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available for this pizza.");
        } else {
            for (Feedback feedback : feedbackList) {
                System.out.println(feedback);
            }
        }
    }

    private static void addNewPizza() {
        System.out.println("Enter Pizza Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Description:");
        String description = scanner.nextLine();

        System.out.println("Enter Base Price:");
        BigDecimal basePrice = BigDecimal.valueOf(scanner.nextDouble());
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Image URL:");
        String imageUrl = scanner.nextLine();

        // Create a Pizza object using the Builder pattern
        Pizza newPizza = new Pizza.Builder()
                .setName(name)
                .setDescription(description)
                .setBasePrice(basePrice)
                .setImageUrl(imageUrl)
                .build();

        PizzaService pizzaService = new PizzaService();
        try {
            pizzaService.addPizza(newPizza);
            System.out.println("Pizza added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding pizza: " + e.getMessage());
        }
    }

    private static void showOrderedPizza() {
        System.out.println("List of Ordered Pizzas:");
        if (orderPizza.isEmpty()) {
            System.out.println("No orders yet.");
        } else {
            for (Map.Entry<String, Order> entry : orderPizza.entrySet()) {
                Order order = entry.getValue();
                System.out.println("Order ID: " + entry.getKey());
                System.out.println("Pizza: " + order.getPizza().getName());
                System.out.println("Status: " + order.getStatus());
                System.out.println("Toppings: " + order.getToppings());
                System.out.println("Size: " + order.getSize());
                System.out.println("Quantity: " + order.getCount());
            }
        }
    }

    //-------------------------------------------------------------------------------------------------//
    private static void addToFavourite() {
        System.out.println(
                pizzaForFav.getPizzaId()
        );
        Integer userId = user.getUserId();
        Favourite fav = new Favourite.Builder()
                .id(UUID.randomUUID().toString())
                .username(user.getUsername())
                .toppings(toppingsforFav)
                .pizza(pizzaForFav)
                .build();

        FavouriteService fvs = new FavouriteService();
        fvs.addFavourite(userId,fav);
    }

    private static void viewFavourites() {
        Pizzaabs pizza1 = new Pizzaabs(1, "Margherita", "Classic pizza with tomato sauce and cheese");
        Pizzaabs pizza2 = new Pizzaabs(2, "Pepperoni", "Pizza with spicy pepperoni slices");

        pizza1.displayPizza();
        System.out.println();
        pizza2.displayPizza();
        return;
    }

    private static void removeFavourite() {
        System.out.println("Enter the ID of the Favorite Item to remove:");
        String favouriteId = scanner.nextLine();

        user.removeFavourite(favouriteId);
        System.out.println("Favorite item removed.");
    }

    private static void showLoyaltiPoints() throws SQLException {
//        ResultSet rs = DBConnection.executeSearch("SELECT points, last_updated FROM loyalty_points WHERE user_id = "+user.getUsername());
//        if (rs.next()) {
            int points = 5;
            String lastUpdated = "2024-12-29 12:45:00";

            System.out.println("Loyalty Points for User: " + user.getUsername());
            System.out.println("Total Points: " + points);
            System.out.println("Last Updated: " + lastUpdated);
//        } else {
//            System.out.println("No loyalty points found for User ID: " + user.getUsername());
//        }
    }
}

class Pizzaabs {
    int id;
    String name;
    String description;

    // Constructor
    public Pizzaabs(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Method to display pizza details
    public void displayPizza() {
        System.out.println("Pizza ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
    }

}