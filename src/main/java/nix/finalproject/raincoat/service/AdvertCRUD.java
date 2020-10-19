package nix.finalproject.raincoat.service;

import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.dto.AdvertPageDto;
import org.springframework.data.domain.Pageable;

public interface AdvertCRUD {

    void delete(Advert advert);

    Advert update(Advert advertFromDb, Advert advert);

    Advert create(Advert advert, User user);

    AdvertPageDto findAll(Pageable pageable);
}
