package com.metehanersoy.trendyolclone.Class;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BasketParentItem {
    ArrayList<MyProduct> childItemList = new ArrayList<>();

    public void addToList(MyProduct myProduct){
        childItemList.add(myProduct);
    }
}
