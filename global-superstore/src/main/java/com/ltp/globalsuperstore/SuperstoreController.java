package com.ltp.globalsuperstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
public class SuperstoreController {

    ArrayList<Item> itemsList = new ArrayList<>();

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        int index = getItemIndex(id);
        model.addAttribute("item", index == Constants.NOT_FOUND ? new Item() : itemsList.get(index));
        return "form";
    }


    @PostMapping("/submitItem")
    public String submitForm(@Valid Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        String status = Constants.STATUS_SUCCESS;
        if (item.getPrice()<item.getDiscount()) result.rejectValue("price","","Price cannot be less than discount");
        if (result.hasErrors()) return "form";

        int index = getItemIndex(item.getId());
        if (index == Constants.NOT_FOUND) {
            itemsList.add(item);
        } else {
            if (within5Days(itemsList.get(index).getDate(),item.getDate())) {
                itemsList.set(index, item);
            } else {
                status = Constants.STATUS_FAILED;
            }
        }
        redirectAttributes.addFlashAttribute("status", status);
        return "redirect:/inventory";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("itemsList", itemsList);
        return "inventory";
    }

    public Integer getItemIndex(String id) {
        for (int i = 0; i<itemsList.size(); i++) {
            if (itemsList.get(i).getId().equals(id)) {
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
