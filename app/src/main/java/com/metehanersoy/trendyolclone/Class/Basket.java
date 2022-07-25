package com.metehanersoy.trendyolclone.Class;

import java.util.ArrayList;

public class Basket {
    //shared preften listeyi oku
    public static ArrayList<BasketParentItem> basketList = new ArrayList<>();

    //Uygulama açılırken bestseleri sunucudan çekip indirip buraya yaz
    public static ArrayList<String> bestSellerStringList = new ArrayList<>();
    public static ArrayList<MyProduct> bestSeller = new ArrayList<>();
}
