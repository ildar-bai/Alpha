package com.baishev.alpha.controller;

import com.baishev.alpha.domain.Item;
import com.baishev.alpha.domain.User;
import com.baishev.alpha.repos.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Item> items = itemRepo.findAll();
        if (filter != null && !filter.isEmpty()) {
            items = itemRepo.findByName(filter);
        } else {
            items = itemRepo.findAll();
        }
        model.addAttribute("items", items);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addItem(@AuthenticationPrincipal User user,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam Integer price,
                          @RequestParam("file") MultipartFile file,
                          Map<String,Object> model) throws IOException {
        Item item = new Item(name, description, price, user);
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            item.setFilename(resultFileName);
        }
        itemRepo.save(item);
        Iterable<Item> items = itemRepo.findAll();
        model.put("items", items);
        return "main";
    }
}
