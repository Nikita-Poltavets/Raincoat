package nix.finalproject.raincoat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString(of = {"id", "title", "details", "description", "job"})
@EqualsAndHashCode(of = {"id"})
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;


    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Details is mandatory")
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String details;

    @NotBlank(message = "Description is mandatory")
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String description;

    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private boolean job;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullAdvert.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullAdvert.class)
    private User author;

    @OneToMany(mappedBy = "advert", orphanRemoval = true)
    @JsonView(Views.FullAdvert.class)
    private List<Comment> comments;
}
