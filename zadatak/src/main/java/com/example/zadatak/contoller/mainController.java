package com.example.zadatak.contoller;


import com.example.zadatak.model.Flight;
import com.example.zadatak.model.FlightReq;
import com.example.zadatak.service.flightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class mainController {

    @Autowired
    private  flightService flService;

    List<Flight> flights;


    @GetMapping(path="/home")
    public String formPage(Model model) {
        model.addAttribute("flightReq", new FlightReq());
        model.addAttribute("flights", flights);
        return "homePage";
    }

    @PostMapping(path="/form")
    public String processForm(@ModelAttribute("FlightReq") FlightReq flightReq, Model model) {
        flights=flService.getFlights(flightReq);

        return "redirect:/app/home";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

    }


}
