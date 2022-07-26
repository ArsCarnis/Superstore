package com.ltp.globalsuperstore.repository;

import com.ltp.globalsuperstore.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SuperstoreRepository {

    private ArrayList<Item> itemsList = new ArrayList<>();

    public Item getItem(int index) {
        return itemsList.get(index);
    }

    public void addItem(Item item) {
        itemsList.add(item);
    }

    public void updateItem(int index, Item item) {
        itemsList.set(index, item);
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

}
