package sber.spring.Rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private int value;
    private int quantity;
}
