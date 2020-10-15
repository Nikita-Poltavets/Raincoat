package nix.finalproject.raincoat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@ToString(of = {"id", "title", "details", "description", "job"})
@EqualsAndHashCode(of = {"id"})
@Data
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String title;
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String details;
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String description;
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private boolean job;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullAdvert.class)
    private LocalDateTime creationDate;
}
