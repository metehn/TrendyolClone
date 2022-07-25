package com.metehanersoy.trendyolclone.Class;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyProduct {

    @NonNull
    String id; // *
    @NonNull
    String name; //*
    @NonNull
    String description; //*
    @NonNull
    String imageURL; //*
    @NonNull
    String type; //* giysi, kişisel bakım
    @NonNull
    ArrayList<String> seller; //* Listenin ilk elemanını al

    int stock; // seller tablosundan çek
    double price; // seller tablosundan çek
    Boolean fastDelivery; //seller tablosundan çek
    int amount; //otomatik olarak ayarlandı. Baskete eklendiğinde artırılabilir
    String sellerName;// seller tablosundan çek
    double sellerRate;// seller tablosundan çek

    boolean checked=true;

    //Returns selled Id
    public String getSellerId() {
        return seller.get(0);
    }
}
