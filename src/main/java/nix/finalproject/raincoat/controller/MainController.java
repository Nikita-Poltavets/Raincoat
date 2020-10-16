package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import nix.finalproject.raincoat.repository.AdvertRepository;
import nix.finalproject.raincoat.service.AdvertService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final AdvertService advertService;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter writer;

    public MainController(AdvertService advertService, ObjectMapper mapper) {
        this.advertService = advertService;

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

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, AdvertController.ADVERTS_PER_PAGE, sort);
            AdvertPageDto advertPageDto = advertService.findAll(pageRequest);

            String adverts = writer.writeValueAsString(advertPageDto.getAdverts());

            model.addAttribute("adverts", adverts);
            data.put("currentPage", advertPageDto.getCurrentPage());
            data.put("totalPages", advertPageDto.getTotalPages());
        } else {
            model.addAttribute("adverts", "[]");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
