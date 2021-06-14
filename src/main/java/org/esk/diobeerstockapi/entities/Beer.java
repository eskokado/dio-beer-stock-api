package org.esk.diobeerstockapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esk.diobeerstockapi.enums.BeerType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int max;

    @Column(nullable = false)
    private int quantity;;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeerType type;

    @Column(nullable = false)
    private Double price;

//    @OneToMany(mappedBy = "id.beer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<OrderItem> items = new HashSet<>();
}
