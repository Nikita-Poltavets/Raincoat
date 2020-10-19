package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.service.impl.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user){
        return user;
    }

    @GetMapping("adverts/{id}")
    @JsonView(Views.FullProfileAndAdvert.class)
    public List<Advert> getUserAdverts(@PathVariable("id") User user){
        return userService.findAdvertsForUser(user);
    }
}
