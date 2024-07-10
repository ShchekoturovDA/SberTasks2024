package sber.spring.Rest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Bin;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BinRepository {
    private List<Bin> binList = new ArrayList<Bin>();

    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";

    public int createBin() {
        var insertSql = "INSERT INTO bins (promocode) VALUES(?);";

        try(var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
            prepareStatement.setString(1, "");

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

/*        Bin bin = new Bin();
        bin.setProductList(new ArrayList<Product>());
        binList.add(bin);
        return bin;*/
    }

    public Optional<Bin> search(int binId) {
        var selectSql = "SELECT * FROM bins where binId = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int returnId = resultSet.getInt("binId");
                String promocode = resultSet.getString("promocode");
                Bin bin = new Bin(returnId, promocode);

                return Optional.of(bin);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        return binList.stream().filter(x -> x.getId() == binId).findAny();
    }

    public boolean isInBin(int binId, int productId){
        var selectSql = "SELECT * FROM products_bins where (id_bin = ? AND id_product = ?)";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);
            prepareStatement.setInt(2, productId);

            var resultSet = prepareStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //        return !binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().isEmpty();
    }

    public int add(int binId, int productId) {

        var insertSql = "INSERT INTO products_bins (id_product, id_bin, count) VALUES(?, ?, ?);";

        try(var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
            prepareStatement.setInt(1, productId);
            prepareStatement.setInt(2, binId);
            prepareStatement.setInt(3, 1);

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


//        product.setQuantity(1);
//        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().add(product);
    }

    public void changeQuantity(int binId, int productId, int quantity) {
        var selectSql = """
                UPDATE products_bins
                SET
                count = ?
                where (id_product = ? AND id_bin = ?);
        """;
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, quantity);
            prepareStatement.setInt(2,productId);
            prepareStatement.setInt(3, binId);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().get().setQuantity(quantity);
    }

    public boolean deleteFromBin(int binId, int productId) {
        var selectSql = "DELETE FROM products_bins where (id_product = ? AND id_bin = ?)";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, productId);
            prepareStatement.setInt(2, binId);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


//        binList.stream()
//                .filter(x -> x.getId() == binId)
//                .findAny()
//                .get().getProductList().remove(binList.stream()
//                        .filter(x -> x.getId() == binId)
//                        .findAny()
//                        .get().getProductList().stream()
//                                .filter(x -> x.getId() == productId)
//                                .findAny()
//                                .get());
    }

    public boolean pay(int binId) {
        var selectSql = "DELETE FROM products_bins where id_bin = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        binList.stream().filter(x -> x.getId() == binId).findAny().get().setProductList(new LinkedList<Product>());
    }


    public int getBin(int binId) {
//        return binList.stream().filter(x -> x.getId() == binId).findAny().get();
        return 1;
    }

}
