package nix.finalproject.raincoat.repository;

import nix.finalproject.raincoat.domain.Advert;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    @EntityGraph(attributePaths = { "comments" })
    List<Advert> findAll();
}
