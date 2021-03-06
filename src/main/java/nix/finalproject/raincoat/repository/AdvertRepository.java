package nix.finalproject.raincoat.repository;

import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    @Override
    @EntityGraph(attributePaths = { "comments" })
    Page<Advert> findAll(Pageable pageable);

    List<Advert> findAdvertsByAuthor(User user);
}
