package com.wmu.thatsmyspot.controller;

import com.wmu.thatsmyspot.entity.Room;
import com.wmu.thatsmyspot.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final RoomService roomService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "rooms";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
} 