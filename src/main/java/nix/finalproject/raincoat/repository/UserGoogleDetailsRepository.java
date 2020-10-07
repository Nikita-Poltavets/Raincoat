package nix.finalproject.raincoat.repository;

import nix.finalproject.raincoat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGoogleDetailsRepository extends JpaRepository<User, String> {
}
