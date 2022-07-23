package com.ltp.globalsuperstore.service;

import com.ltp.globalsuperstore.Constants;
import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.repository.SuperstoreRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SuperstoreService {

    SuperstoreRepository superstoreRepository = new SuperstoreRepository();

    public Item getItem(int index) {
        return superstoreRepository.getItem(index);
    }

    public void addItem(Item item) {
        superstoreRepository.addItem(item);
    }

    public void updateItem(int index, Item item) {
        superstoreRepository.updateItem(index, item);
    }

    public List<Item> getItemsList() {
        return superstoreRepository.getItemsList();
    }

    public Item getItemById(String id) {
        int index = getItemIndex(id);
        return index == Constants.NOT_FOUND ? new Item() : getItem(index);
    }

    public String submitItem(Item item) {
        String status = Constants.STATUS_SUCCESS;
        int index = getItemIndex(item.getId());
        if (index == Constants.NOT_FOUND) {
            addItem(item);
        } else if (within5Days(getItem(index).getDate(),item.getDate())) {
                updateItem(index, item);
        } else {
            status = Constants.STATUS_FAILED;
        }
        return status;
    }
    public Integer getItemIndex(String id) {
        for (int i = 0; i<getItemsList().size(); i++) {
            if (getItemsList().get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    public boolean within5Days(Date newDate, Date oldDate) {
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        return (int) (TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }
}
