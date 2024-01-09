package lb7;
import java.util.*;

class Product implements Comparable<Product> {
    private Integer id;
    private String name;
    private double price;
    private int stock;

    public Product(Integer id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", stock=" + stock + '}';
    }

    @Override
    public int compareTo(Product otherProduct) {
        return Double.compare(this.price, otherProduct.price);
    }

    public static class NameComparator implements Comparator<Product> {
        @Override
        public int compare(Product product1, Product product2) {
            return product1.getName().compareTo(product2.getName());
        }
    }

    public static class StockComparator implements Comparator<Product> {
        @Override
        public int compare(Product product1, Product product2) {
            return Integer.compare(product1.getStock(), product2.getStock());
        }
    }
}

class User {
    private Integer id;
    private String username;
    private Map<Product, Integer> cart;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        this.cart = new HashMap<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void addToCart(Product product, int quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
    }

    public void removeFromCart(Product product, int quantity) {
        cart.put(product, Math.max(cart.getOrDefault(product, 0) - quantity, 0));
    }

    public void modifyCart(Product product, int quantity) {
        cart.put(product, Math.max(quantity, 0));
    }
}

class Order {
    private Integer id;
    private Integer userId;
    private Map<Product, Integer> orderDetails;
    private double totalPrice;

    public Order(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
        this.orderDetails = new HashMap<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getOrderDetails() {
        return orderDetails;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void addToOrder(Product product, int quantity) {
        orderDetails.put(product, orderDetails.getOrDefault(product, 0) + quantity);
    }

    public void calculateTotalPrice() {
        totalPrice = orderDetails.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}

class ECommercePlatform {
    private Map<Integer, User> users;
    private Map<Integer, Product> products;
    private Map<Integer, Order> orders;

    public ECommercePlatform() {
        this.users = new HashMap<>();
        this.products = new HashMap<>();
        this.orders = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void createOrder(Order order) {
        orders.put(order.getId(), order);
    }

    public Map<Integer, Product> getAvailableProducts() {
        return products;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void displaySortedProducts(Comparator<Product> comparator) {
        List<Product> productList = new ArrayList<>(products.values());
        productList.sort(comparator);
        System.out.println("Sorted Products: " + productList);
    }

    public void displayAvailableProducts() {
        List<Product> availableProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getStock() > 0) {
                availableProducts.add(product);
            }
        }
        System.out.println("Available Products: " + availableProducts);
    }

    public void updateProductStock(Product product, int newStock) {
        if (products.containsKey(product.getId())) {
            product.setStock(newStock);
        }
    }

    public void recommendProducts(User user) {
        Map<Product, Integer> userOrderHistory = new HashMap<>();

        for (Order order : orders.values()) {
            if (order.getUserId().equals(user.getId())) {
                userOrderHistory.putAll(order.getOrderDetails());
            }
        }

        Set<Product> recommendedProducts = new HashSet<>(userOrderHistory.keySet());

        for (Product product : user.getCart().keySet()) {
            recommendedProducts.removeAll(user.getCart().keySet());
        }

        System.out.println("Recommended Products: " + recommendedProducts);
    }
}

public class ECommerceDemo {
    public static void main(String[] args) {
        ECommercePlatform platform = new ECommercePlatform();

        User user1 = new User(1, "User1");
        User user2 = new User(2, "User2");
        platform.addUser(user1);
        platform.addUser(user2);

        Product product1 = new Product(1, "Product1", 20.0, 50);
        Product product2 = new Product(2, "Product2", 15.0, 30);
        platform.addProduct(product1);
        platform.addProduct(product2);

        user1.addToCart(product1, 3);
        user2.addToCart(product2, 2);

        System.out.println("Cart of User1: " + user1.getCart());
        System.out.println("Cart of User2: " + user2.getCart());

        Order order1 = new Order(1, user1.getId());
        order1.addToOrder(product1, 3);
        platform.createOrder(order1);

        Order order2 = new Order(2, user2.getId());
        order2.addToOrder(product2, 2);
        platform.createOrder(order2);

        platform.updateProductStock(product1, 47);

        System.out.println("Available products: " + platform.getAvailableProducts());
        System.out.println("Users: " + platform.getUsers());
        System.out.println("Orders: " + platform.getOrders());

        platform.recommendProducts(user1);

        System.out.println("Final state of the platform:");
        System.out.println("Available products: " + platform.getAvailableProducts());
        System.out.println("Users: " + platform.getUsers());
        System.out.println("Orders: " + platform.getOrders());
    }
}
