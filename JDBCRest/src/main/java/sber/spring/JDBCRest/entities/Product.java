package sber.spring.JDBCRest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private int value;
    private int quantity;
}
