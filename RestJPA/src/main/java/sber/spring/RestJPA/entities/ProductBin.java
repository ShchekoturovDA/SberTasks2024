package sber.spring.RestJPA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products_bins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @JoinColumn(name = "id_bin", nullable = false)
    private Bin binId;

    @Column(nullable = false)
    private int quantity;
}
