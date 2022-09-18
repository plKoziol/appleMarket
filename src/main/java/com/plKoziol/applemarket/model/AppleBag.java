package com.plKoziol.applemarket.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;


@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "apple_bag")
@Entity
public class AppleBag {

    @Id
    private String id;

    @Column(name = "number_of_apples")
    private int numberOfApples;

    @Column(name = "supplier")
    private Supplier supplier;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "time_bag_packed")
    private LocalDateTime timeBagPacked;

    @Column(name = "price")
    private BigDecimal price;

    public AppleBag(int numberOfApples, Supplier supplier, LocalDateTime timeBagPacked, BigDecimal price) {
        id = supplier.toString() +"/"+numberOfApples+"/"+timeBagPacked+"/"+new Random().nextInt(999);
        this.numberOfApples = numberOfApples;
        this.supplier = supplier;
        this.timeBagPacked = timeBagPacked;
        this.price = price;
    }
    public boolean appleBagValidationPrice (){
        return price.longValue()>=1&& price.longValue()<=50;
    }
    public boolean appleBagValidationNumberOfApples (){
        return numberOfApples>=1&& numberOfApples<=100;
    }

    public enum Supplier{
       ROYAL_GALA, PINK_LADY, KANZI_APPLE, ELSTAR_APPLES
    }
}
