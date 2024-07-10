package sber.spring.Rest.repositories;

import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.entities.Product;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
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

    public Optional<Bin> search(long binId) {
        return binList.stream().filter(x -> x.getId() == binId).findAny();
    }

    public boolean isInBin(long binId, long productId){
        return !binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().isEmpty();
    }

    public void add(long binId, Product product) {
        product.setQuantity(1);
        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().add(product);
    }

    public void changeQuantity(long binId, long productId, int quantity) {
        binList.stream().filter(x -> x.getId() == binId).findAny().get().getProductList().stream().filter(x -> x.getId() == productId).findAny().get().setQuantity(quantity);
    }

    public void deleteFromBin(long binId, long productId) {
        binList.stream()
                .filter(x -> x.getId() == binId)
                .findAny()
                .get().getProductList().remove(binList.stream()
                        .filter(x -> x.getId() == binId)
                        .findAny()
                        .get().getProductList().stream()
                                .filter(x -> x.getId() == productId)
                                .findAny()
                                .get());
    }

    public void pay(long binId) {
        binList.stream().filter(x -> x.getId() == binId).findAny().get().setProductList(new LinkedList<Product>());
    }


    public int getBin(int binId) {
//        return binList.stream().filter(x -> x.getId() == binId).findAny().get();
        return 1;
    }
}
