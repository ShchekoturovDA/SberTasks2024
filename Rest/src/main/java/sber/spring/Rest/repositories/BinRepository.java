package sber.spring.Rest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sber.spring.Rest.entities.Bin;
import sber.spring.Rest.entities.Sold;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BinRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BinRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createBin() {
        String insertSql = "INSERT INTO bins (promocode) VALUES(?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "");

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("id_bin");
    }

    public Optional<Bin> search(int binId) {
        String selectSql = "SELECT * FROM bins where id_bin = ?";
        return jdbcTemplate.query(selectSql, getBinRowMapper(), binId).stream().findFirst();
    }


    private static RowMapper<Bin> getBinRowMapper() {
        return (resultSet, rowNum) -> {
            int binId = resultSet.getInt("id_bin");
            String promocode = resultSet.getString("promocode");
            return new Bin(binId, promocode);
        };
    }

    public boolean isInBin(int binId, int productId) {
        String selectSql = "SELECT * FROM products_bins where (id_bin = ? AND id_product = ?)";
        return jdbcTemplate.query(selectSql,
                (resultSet, rowNum) -> {
                    Bin bin = new Bin();
                    bin.setId(resultSet.getInt("id_bin"));
                    return bin;
                }, binId, productId).stream().findFirst().isPresent();
    }

    public int add(int binId, int productId) {

        String insertSql = "INSERT INTO products_bins (id_product, id_bin, count) VALUES(?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, binId);
            preparedStatement.setInt(3, 1);

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("products_binId");
    }

    public void changeQuantity(int binId, int productId, int quantity) {
        String selectSql = """
                        UPDATE products_bins
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

        jdbcTemplate.update(selectSql, quantity, productId, binId);
    }

    public boolean deleteFromBin(int binId, int productId) {
        String selectSql = "DELETE FROM products_bins where (id_product = ? AND id_bin = ?)";
        int rows = jdbcTemplate.update(selectSql, productId, binId);
        return rows > 0;
    }

    public List<Sold> selectAllFromBin(int binId) {
        String selectSql = "SELECT * FROM products_bins where id_bin = ?";
        return jdbcTemplate.query(selectSql, (resultSet, rowNum) ->
        {
            return new Sold(resultSet.getInt("id_product"), resultSet.getInt("count"));
        }, binId).stream().collect(Collectors.toList());
    }

    public void pay(int binId) {

        String deleteSql = "DELETE FROM products_bins where id_bin = ?";

        jdbcTemplate.update(deleteSql, binId);
    }
}
