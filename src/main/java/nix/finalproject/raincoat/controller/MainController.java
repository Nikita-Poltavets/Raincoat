package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import nix.finalproject.raincoat.repository.UserGoogleDetailsRepository;
import nix.finalproject.raincoat.service.impl.AdvertService;
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
    private final UserGoogleDetailsRepository userGoogleDetailsRepository;

    @Value("${spring.profiles.active:prod}")
    private String profile;
    private final ObjectWriter advertWriter;
    private final ObjectWriter profileWriter;

    public MainController(AdvertService advertService, UserGoogleDetailsRepository userGoogleDetailsRepository, ObjectMapper mapper) {
        this.advertService = advertService;
        this.userGoogleDetailsRepository = userGoogleDetailsRepository;

        ObjectMapper objectMapper = mapper
                .setConfig(mapper.getSerializationConfig());

        // Здесь устанавливаем JsonView, который мы хотим использовать
        this.advertWriter = objectMapper
                .writerWithView(Views.FullAdvert.class);

        this.profileWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {

        HashMap<Object, Object> data = new HashMap<>();

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(0, AdvertController.ADVERTS_PER_PAGE, sort);
        AdvertPageDto advertPageDto = advertService.findAll(pageRequest);

        String adverts = advertWriter.writeValueAsString(advertPageDto.getAdverts());

        if(user != null) {
            User userFromDb = userGoogleDetailsRepository.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);
        } else {
            model.addAttribute("profile", "null");
        }
        data.put("currentPage", advertPageDto.getCurrentPage());
        data.put("totalPages", advertPageDto.getTotalPages());
        model.addAttribute("adverts", adverts);

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
