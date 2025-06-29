package com.lusterz.football.Player;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping(value = {
            "/",
            "/search",
            "/data" // ← You MUST add this
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}

