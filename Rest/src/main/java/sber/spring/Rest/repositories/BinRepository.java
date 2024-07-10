package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Bin;

import java.sql.*;
import java.util.Optional;

@Repository
public class BinRepository {
//    public static final String JDBC = "jdbc:postgresql://localhost:8079/postgres?currentSchema=my_sch&user=postgres&password=Rattlehead85";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BinRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createBin() {
        var insertSql = "INSERT INTO my_sch.bins (promocode) VALUES(?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "");

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("id_bin");

        /*
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
        String selectSql = "SELECT * FROM my_sch.bins where id_bin = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setInt(1, binId);
            return preparedStatement;
        };
        return jdbcTemplate.query(preparedStatementCreator, getBinRowMapper()).stream().findFirst();

        /*
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
        }*/

//        return binList.stream().filter(x -> x.getId() == binId).findAny();
    }


    private static RowMapper<Bin> getBinRowMapper(){
        return (resultSet, rowNum) -> {
            int binId = resultSet.getInt("id_bin");
            String promocode = resultSet.getString("promocode");
            return new Bin(binId, promocode);
        };
    }

    public boolean isInBin(int binId, int productId){
        String selectSql = "SELECT * FROM my_sch.products_bins where (id_bin = ? AND id_product = ?)";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setInt(1, binId);
            preparedStatement.setInt(2, productId);

            return preparedStatement;
        };

        return jdbcTemplate.query(preparedStatementCreator,
                (resultSet, rowNum)->{
                    Bin bin = new Bin();
                    bin.setId(resultSet.getInt("id_bin"));
                    return bin;
                }).stream().findFirst().isPresent();

        /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);
            prepareStatement.setInt(2, productId);

            var resultSet = prepareStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */
        //        return !binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().isEmpty();
    }

    public int add(int binId, int productId) {

        String insertSql = "INSERT INTO my_sch.products_bins (id_product, id_bin, count) VALUES(?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, binId);
            preparedStatement.setInt(3, 1);

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int)keyHolder.getKeys().get("products_binId");


        /*
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
        */

//        product.setQuantity(1);
//        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().add(product);
    }

    public void changeQuantity(int binId, int productId, int quantity) {
        var selectSql = """
                UPDATE my_sch.products_bins
                SET
                count = ?
                where (id_product = ? AND id_bin = ?);
        """;

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, binId);

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);

        /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, quantity);
            prepareStatement.setInt(2,productId);
            prepareStatement.setInt(3, binId);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
//        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().get().setQuantity(quantity);
    }

    public boolean deleteFromBin(int binId, int productId) {
        var selectSql = "DELETE FROM my_sch.products_bins where (id_product = ? AND id_bin = ?)";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, binId);

            return  preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;

        /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, productId);
            prepareStatement.setInt(2, binId);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/


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
        var deleteSql = "DELETE FROM my_sch.products_bins where id_bin = ?";

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, binId);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;

    /*
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setInt(1, binId);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    */
//        binList.stream().filter(x -> x.getId() == binId).findAny().get().setProductList(new LinkedList<Product>());
    }


    public int getBin(int binId) {
//        return binList.stream().filter(x -> x.getId() == binId).findAny().get();
        return 1;
    }

}
