package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import nix.finalproject.raincoat.service.AdvertCRUD;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("advert")
public class AdvertController {
    public static final int ADVERTS_PER_PAGE = 6;

    private final AdvertCRUD advertCRUD;

    public AdvertController(AdvertCRUD advertCRUD) {
        this.advertCRUD = advertCRUD;
    }

    @GetMapping
    @JsonView(Views.FullAdvert.class)
    public AdvertPageDto list(
            @PageableDefault(size = ADVERTS_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return advertCRUD.findAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullAdvert.class)
    public Advert getOne(@PathVariable("id") Advert advert) {
        return advert;
    }

    @GetMapping("details/{id}")
    @JsonView(Views.FullProfileAndAdvert.class)
    public Advert getById(@PathVariable("id") Advert advert) {
        return advert;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @JsonView(Views.FullAdvert.class)
    public Advert create(
            @Valid @RequestBody Advert advert,
            @AuthenticationPrincipal User user
    ) {
        return advertCRUD.create(advert, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    @JsonView(Views.FullAdvert.class)
    public Advert update(
            @PathVariable("id") Advert advertFromDb,
            @Valid @RequestBody Advert advert
    ) {
       return advertCRUD.update(advertFromDb, advert);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Advert advert) {
        advertCRUD.delete(advert);
    }
}
