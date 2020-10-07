package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.repository.AdvertRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("advert")
public class AdvertController {
   private final AdvertRepository advertRepository;

    public AdvertController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @GetMapping
    @JsonView(Views.IdTitleDetailsDescription.class)
    public List<Advert> list(){
        return advertRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullAdvert.class)
    public Advert getOne(@PathVariable("id") Advert advert) {
        return advert;
    }

    @PostMapping
    public Advert create(@RequestBody Advert advert) {
        advert.setCreationDate(LocalDateTime.now());
        return advertRepository.save(advert);
    }

    @PutMapping("{id}")
    public Advert update(
            @PathVariable("id") Advert advertFromDb,
            @RequestBody Advert advert
    ) {
        BeanUtils.copyProperties(advert, advertFromDb, "id");

        return advertRepository.save(advertFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Advert advert) {
       advertRepository.delete(advert);
    }
}
