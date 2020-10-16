package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import nix.finalproject.raincoat.service.AdvertService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/advert")
public class AdvertController {
    public static final int ADVERTS_PER_PAGE = 3;

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping
    @JsonView(Views.FullAdvert.class)
    public AdvertPageDto list(
            @PageableDefault(size = ADVERTS_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return advertService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @JsonView(Views.FullAdvert.class)
    public Advert getOne(@PathVariable("id") Advert advert) {
        return advert;
    }

    @GetMapping("/details/{id}")
    @JsonView(Views.FullAdvert.class)
    public Advert getById(@PathVariable("id") Long id) {
        return advertService.getById(id);
    }

    @PostMapping
    public Advert create(
            @RequestBody Advert advert,
            @AuthenticationPrincipal User user
    ) {
        return advertService.create(advert, user);
    }

    @PutMapping("/{id}")
    public Advert update(
            @PathVariable("id") Advert advertFromDb,
            @RequestBody Advert advert
    ) {
       return advertService.update(advertFromDb, advert);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Advert advert) {
        advertService.delete(advert);
    }


}
