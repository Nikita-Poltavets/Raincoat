package nix.finalproject.raincoat.service.impl;

import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.repository.AdvertRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final AdvertRepository advertRepository;

    public UserService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public List<Advert> findAdvertsForUser(User user) {
        return advertRepository.findAdvertsByAuthor(user);
    }

}
