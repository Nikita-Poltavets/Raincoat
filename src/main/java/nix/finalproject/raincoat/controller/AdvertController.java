package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.EventType;
import nix.finalproject.raincoat.dto.ObjectType;
import nix.finalproject.raincoat.repository.AdvertRepository;
import nix.finalproject.raincoat.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("advert")
public class AdvertController {
   private final AdvertRepository advertRepository;
   private final BiConsumer<EventType, Advert> wsSender;

    public AdvertController(AdvertRepository advertRepository, WsSender wsSender) {
        this.advertRepository = advertRepository;
        this.wsSender = wsSender.getSender(ObjectType.ADVERT, Views.IdTitleDetailsDescriptionJob.class);
    }

    @GetMapping
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    public List<Advert> list(){
        return advertRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullAdvert.class)
    public Advert getOne(@PathVariable("id") Advert advert) {
        return advert;
    }

    @PostMapping
    public Advert create(
            @RequestBody Advert advert,
            @AuthenticationPrincipal User user
    ) {
        advert.setCreationDate(LocalDateTime.now());
        advert.setAuthor(user);

        Advert createdAdvert = advertRepository.save(advert);
        wsSender.accept(EventType.CREATE, createdAdvert);

        return createdAdvert;
    }

    @PutMapping("{id}")
    public Advert update(
            @PathVariable("id") Advert advertFromDb,
            @RequestBody Advert advert
    ) {
        BeanUtils.copyProperties(advert, advertFromDb, "id");

        Advert updatedAdvert = advertRepository.save(advertFromDb);

        wsSender.accept(EventType.UPDATE, updatedAdvert);

        return updatedAdvert;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Advert advert) {
       advertRepository.delete(advert);
       wsSender.accept(EventType.REMOVE, advert);
    }
}
