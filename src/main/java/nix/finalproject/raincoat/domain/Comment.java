package nix.finalproject.raincoat.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Comment {
    @Id
    @GeneratedValue
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private Long id;

    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private Advert advert;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private User author;

}
