package ua.opnu;

import ua.opnu.util.Customer;
import ua.opnu.util.DataProvider;
import ua.opnu.util.Order;
import ua.opnu.util.Product;

import java.util.*;
import java.util.stream.Collectors;

public class HardTasks {

    private final List<Customer> customers = DataProvider.customers;
    private final List<Order> orders = DataProvider.orders;
    private final List<Product> products = DataProvider.products;

    public static void main(String[] args) {
        HardTasks tasks = new HardTasks();
    }

    public List<Product> getBooksWithPrice() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .filter(p -> p.getPrice() > 100)
                .collect(Collectors.toList());
    }

    public List<Order> getOrdersWithBabyProducts() {
        return orders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> "Baby".equals(product.getCategory())))
                .collect(Collectors.toList());
    }

    public List<Product> applyDiscountToToys() {
        return products.stream()
                .filter(p -> "Toys".equals(p.getCategory()))
                .map(p -> {
                    p.setPrice(p.getPrice() * 0.5);
                    return p;
                })
                .collect(Collectors.toList());
    }

    public Optional<Product> getCheapestBook() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .min(Comparator.comparingDouble(Product::getPrice));
    }

    public List<Order> getRecentOrders() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public DoubleSummaryStatistics getBooksStats() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    public Map<Integer, Integer> getOrdersProductsMap() {
        return orders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts().size()
                ));
    }

    public Map<String, List<Integer>> getProductsByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getId, Collectors.toList())
                ));
    }
}