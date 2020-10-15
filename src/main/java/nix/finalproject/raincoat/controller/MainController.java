package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final AdvertRepository advertRepository;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter writer;

    public MainController(AdvertRepository advertRepository, ObjectMapper mapper) {
        this.advertRepository = advertRepository;

        // Здесь устанавливаем JsonView, который мы хотим использовать
        this.writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullAdvert.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {

        HashMap<Object, Object> data = new HashMap<>();


        if(user != null) {
            data.put("profile", user);

            String adverts = writer.writeValueAsString(advertRepository.findAll());
            model.addAttribute("adverts", adverts);
        } else {
            model.addAttribute("adverts", "[]");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
