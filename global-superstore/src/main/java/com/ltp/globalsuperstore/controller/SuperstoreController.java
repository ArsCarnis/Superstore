package com.ltp.globalsuperstore.controller;

import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.service.SuperstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SuperstoreController {

    @Autowired
    SuperstoreService superstoreService;

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        model.addAttribute("item", superstoreService.getItemById(id));
        return "form";
    }


    @PostMapping("/submitItem")
    public String submitForm(@Valid Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if (item.getPrice()<item.getDiscount()) result.rejectValue("price","","Price cannot be less than discount");
        if (result.hasErrors()) return "form";
        String status = superstoreService.submitItem(item);
        redirectAttributes.addFlashAttribute("status", status);
        return "redirect:/inventory";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("itemsList", superstoreService.getItemsList());
        return "inventory";
    }


}
