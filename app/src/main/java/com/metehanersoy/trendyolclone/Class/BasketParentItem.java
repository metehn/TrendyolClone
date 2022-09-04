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

    boolean checked=true;

    public void addToList(MyProduct myProduct){
        childItemList.add(myProduct);
    }
    public boolean removeFromList(MyProduct myProduct){ return childItemList.remove(myProduct); }


}
