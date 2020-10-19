package nix.finalproject.raincoat.service.impl;

import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import nix.finalproject.raincoat.dto.EventType;
import nix.finalproject.raincoat.dto.ObjectType;
import nix.finalproject.raincoat.repository.AdvertRepository;
import nix.finalproject.raincoat.service.AdvertCRUD;
import nix.finalproject.raincoat.util.WsSender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

@Service
@Transactional
public class AdvertService implements AdvertCRUD {

    private final AdvertRepository advertRepository;
    private final BiConsumer<EventType, Advert> wsSender;

    public AdvertService(AdvertRepository advertRepository, WsSender wsSender) {
        this.advertRepository = advertRepository;
        this.wsSender = wsSender.getSender(ObjectType.ADVERT, Views.IdTitleDetailsDescriptionJob.class);
    }

    @Override
    public void delete(Advert advert) {
        advertRepository.delete(advert);
        wsSender.accept(EventType.REMOVE, advert);
    }

    @Override
    public Advert update(Advert advertFromDb, Advert advert) {
        advertFromDb.setTitle(advert.getTitle());
        advertFromDb.setJob(advert.isJob());
        advertFromDb.setDetails(advert.getDetails());
        advertFromDb.setDescription(advert.getDescription());


        Advert updatedAdvert = advertRepository.save(advertFromDb);

        wsSender.accept(EventType.UPDATE, updatedAdvert);

        return updatedAdvert;
    }

    @Override
    public Advert create(Advert advert, User user) {
        advert.setCreationDate(LocalDateTime.now());
        advert.setAuthor(user);

        Advert createdAdvert = advertRepository.save(advert);
        wsSender.accept(EventType.CREATE, createdAdvert);

        return createdAdvert;
    }

    @Override
    public AdvertPageDto findAll(Pageable pageable) {
        Page<Advert> page = advertRepository.findAll(pageable);
        return new AdvertPageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }
}
