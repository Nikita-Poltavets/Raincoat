package nix.finalproject.raincoat.service;

import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import nix.finalproject.raincoat.dto.EventType;
import nix.finalproject.raincoat.dto.ObjectType;
import nix.finalproject.raincoat.repository.AdvertRepository;
import nix.finalproject.raincoat.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final BiConsumer<EventType, Advert> wsSender;

    public AdvertService(AdvertRepository advertRepository, WsSender wsSender) {
        this.advertRepository = advertRepository;
        this.wsSender = wsSender.getSender(ObjectType.ADVERT, Views.IdTitleDetailsDescriptionJob.class);
    }

    public Advert getById(Long id){
        return advertRepository.getOne(id);
    }

    public void delete(Advert advert) {
        advertRepository.delete(advert);
        wsSender.accept(EventType.REMOVE, advert);
    }

    public Advert update(Advert advertFromDb, Advert advert) {
        BeanUtils.copyProperties(advert, advertFromDb, "id");

        Advert updatedAdvert = advertRepository.save(advertFromDb);

        wsSender.accept(EventType.UPDATE, updatedAdvert);

        return updatedAdvert;
    }

    public Advert create(Advert advert, User user) {
        advert.setCreationDate(LocalDateTime.now());
        advert.setAuthor(user);

        Advert createdAdvert = advertRepository.save(advert);
        wsSender.accept(EventType.CREATE, createdAdvert);

        return createdAdvert;
    }

    public AdvertPageDto findAll(Pageable pageable) {
        Page<Advert> page = advertRepository.findAll(pageable);
        return new AdvertPageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }
}
