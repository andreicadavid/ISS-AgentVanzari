package com.example.program_agentvanzari.Service;

import com.example.program_agentvanzari.Domain.Order;
import com.example.program_agentvanzari.Domain.Product;
import com.example.program_agentvanzari.Repository.OrderDatabaseRepo;
import com.example.program_agentvanzari.Repository.ProductDatabaseRepo;
import com.example.program_agentvanzari.Repository.UserDatabaseRepo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private ProductDatabaseRepo productRepo;
    private OrderDatabaseRepo orderRepo;
    private UserDatabaseRepo userRepo;

    public Service(ProductDatabaseRepo productRepo, OrderDatabaseRepo orderRepo, UserDatabaseRepo userRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepo.getData();
        Collections.sort(products, Comparator.comparing(Product::getId));
        return products;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepo.getData();
        Collections.sort(orders, Comparator.comparing(Order::getId));
        return orders;
    }

    public void add(String name, double price, int nr) {
        List<Product> products = productRepo.getData();
        if (products.isEmpty()) {
            int id = 100;
            Product item = new Product(id, name, price, nr);
            productRepo.add(item);
        } else {
            List<Product> sortedProducts = getAllProducts();
            int id = sortedProducts.get(sortedProducts.size() - 1).getId() + 1;
            productRepo.add(new Product(id, name, price, nr));
        }
    }

    public void update(int id, String name, double price, int nr) {
        Product existingProduct = (Product) productRepo.getById(id);
        if (existingProduct != null) {
            existingProduct.setName(name);
            existingProduct.setPrice(price);
            existingProduct.setNr(nr);
            productRepo.update(existingProduct);
        } else {
            System.out.println("Product with ID " + id + " does not exist.");
        }
    }

    public void delete(int id) {
        Product existingProduct = (Product) productRepo.getById(id);
        if (existingProduct != null) {
            productRepo.delete(existingProduct);
        } else {
            System.out.println("Product with ID " + id + " does not exist.");
        }
    }

//    public List<Product> filter(int id, String name, double price, int nr) {
//        List<Product> products = productRepo.getData();
//        String lowercaseName = name.toLowerCase();
//        return products.stream()
//                .filter(product ->
//                        product.getName().toLowerCase().contains(lowercaseName) &&
//                                product.getPrice() <= price &&
//                                product.getNr() <= nr)
//                .collect(Collectors.toList());
//    }

    public List<Product> filter(Integer id, String name, Double price, Integer nr) {
        List<Product> products = productRepo.getData();
        String lowercaseName = name == null ? "" : name.toLowerCase(); // Tratează cazul în care numele este null
        return products.stream()
                .filter(product ->
                        (id == null || product.getId() == id) && // Verifică dacă id-ul trebuie luat în considerare
                                (name == null || product.getName().toLowerCase().contains(lowercaseName)) && // Verifică dacă numele trebuie luat în considerare
                                (price == null || product.getPrice() <= price) && // Verifică dacă prețul trebuie luat în considerare
                                (nr == null || product.getNr() <= nr)) // Verifică dacă cantitatea trebuie luată în considerare
                .collect(Collectors.toList());
    }


    public void addOrder(String customerName, String customerSurname, String customerAddress, Double postalCode, String customerEmail, String customerPhone, Double orderAmount, int productId) {
        List<Order> orders = orderRepo.getData();
        if (orders.isEmpty()) {
            int id = 100; // Exemplu: atribuie un ID inițial
            Order order = new Order(id, customerName, customerSurname, customerAddress, postalCode, customerEmail, customerPhone, orderAmount, productId);
            orderRepo.add(order);
        } else {
            List<Order> sortedOrders = getAllOrders(); // Implementează metoda pentru a obține toate comenzile sortate
            int id = sortedOrders.get(sortedOrders.size() - 1).getId() + 1;
            orderRepo.add(new Order(id, customerName, customerSurname, customerAddress, postalCode, customerEmail, customerPhone, orderAmount, productId));
        }
    }
}
