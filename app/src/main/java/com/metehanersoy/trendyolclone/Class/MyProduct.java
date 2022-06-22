package com.metehanersoy.trendyolclone.Class;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class MyProduct {

    String id;
    int amount = 1;

    String name;
    String description;
    double price;
    String imageURL;
    String type; //giysi, kişisel bakım
    Boolean fastDelivery;

    public MyProduct(int amount, String name, String description, double price, String imageURL, String type, Boolean fastDelivery) {
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.type = type;
        this.fastDelivery = fastDelivery;
    }
}
