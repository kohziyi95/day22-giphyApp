package vttp2022.giphyapp.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.giphyapp.service.GiphyService;

@Controller
@RequestMapping("/")
public class GiphyController {
    
    @Autowired
    private GiphyService service;

    @GetMapping("/search")
    public String showResult(
            @RequestParam String search, 
            @RequestParam String rating, 
            @RequestParam Integer limit, Model model){
        
        List<String> imageList = service.getGifs(search, limit, rating);
        model.addAttribute("search", search);
        model.addAttribute("imageList", imageList);

        return "showResult";
    }
    
}
