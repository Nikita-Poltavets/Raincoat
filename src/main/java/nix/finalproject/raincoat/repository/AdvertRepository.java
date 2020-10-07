package nix.finalproject.raincoat.repository;

import nix.finalproject.raincoat.domain.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
}
