package sber.spring.Rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Basket {
    private int id;
    private LinkedList<Product> productList;
    private String promoCode;
}
