package org.sber;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DataContainer {
    private String name;
    private int age;
    private String address;
    private Double height;
    private int passLevel;
}
