package nix.finalproject.raincoat.repository;

import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGoogleDetailsRepository extends JpaRepository<User, String> {

    @Override
    Optional<User> findById(String s);
}
