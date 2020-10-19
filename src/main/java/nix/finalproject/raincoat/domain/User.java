package nix.finalproject.raincoat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = { "id" })
public class User implements Serializable {
    @Id
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String id;

    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String name;

    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private String userpic;

    private String email;

    @JsonView(Views.FullProfile.class)
    private String gender;

    @JsonView(Views.FullProfile.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.IdTitleDetailsDescriptionJob.class)
    private LocalDateTime lastVisit;
}
