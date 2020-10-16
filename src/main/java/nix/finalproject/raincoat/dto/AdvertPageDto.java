package nix.finalproject.raincoat.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nix.finalproject.raincoat.domain.Advert;
import nix.finalproject.raincoat.domain.Views;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullAdvert.class)
public class AdvertPageDto {
    private List<Advert> adverts;
    private int currentPage;
    private int totalPages;
}
