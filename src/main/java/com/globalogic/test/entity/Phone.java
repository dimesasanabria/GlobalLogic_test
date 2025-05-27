package com.globalogic.test.entity;
/**
 * * Phone.java
 * * This class represents a Phone entity with fields for id, number, city code, and country code.
 * * It is annotated with JPA annotations to map it to a database table.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "phone")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Long id;
    @Column(name = "number")    
    private Long number;
    @Column(name = "citycode")
    private String citycode;
    @Column(name = "countrycode")
    private String countrycode;
  
}

