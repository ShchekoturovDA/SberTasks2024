package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private List<Product> productList = new ArrayList<Product>();
    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";
    private int ids = 0;

    public long addProduct(Product product) {
        var insertSql = "INSERT INTO products (productName, productValue, quantity) VALUES(?, ?, ?);";

        product.setId(generateId());
        try(var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
//            prepareStatement.setInt(1, product.getId());
            prepareStatement.setString(1, product.getName());
            prepareStatement.setInt(2, product.getValue());
            prepareStatement.setInt(3, product.getQuantity());

            prepareStatement.executeUpdate();
            ResultSet rs = prepareStatement.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
//        productList.add(product);
    }

    private int generateId() {
        ids += 1;
        return ids;
    }

    public void update(Product product) {
        for(Product productFromList : productList){
            if(productFromList.getId() == product.getId()){
                productFromList.setName(product.getName());
                productFromList.setQuantity(product.getQuantity());
                productFromList.setValue(product.getValue());
            }
        }
    }

    public Optional<Product> search(int id) {
        var selectSql = "SELECT * FROM products where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("id");
                String name = resultSet.getString("productName");
                int value = resultSet.getInt("value");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(returnId, name, value, quantity);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
 /*       return productList.stream()
                .filter(x -> x.getId() == id)
                .findAny();*/
    }

    public boolean delete(int id) {
        var selectSql = "DELETE FROM products where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
/*        Product delete = productList.stream()
                .filter(x -> x.getId() == id)
                .findAny()
                .orElse(null);
        if (delete == null){
            return false;
        } else {
            productList.remove(delete);
            return true;
        }*/
    }

    public List<Product> searchByName(String name) {
        var selectSql = "SELECT * FROM products where productName = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, name);

            var resultSet = prepareStatement.executeQuery();

            List <Product> products = new ArrayList<Product>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("productName");
                int value = resultSet.getInt("value");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(id, productName, value, quantity);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
/*        List<Product> list = new ArrayList<Product>();
        for (Product product : productList){
            System.out.println(product.getName());
            if (product.getName() == name){
                list.add(product);
            }
        }
        return list;//productList.stream().filter(x -> x.getName() == name).collect(Collectors.toList());*/
    }

    public void sell(int id, int quantity) {
        Product product = search(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        update(product);
    }
}
