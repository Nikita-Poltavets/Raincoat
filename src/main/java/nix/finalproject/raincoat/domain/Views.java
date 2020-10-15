package nix.finalproject.raincoat.domain;

public final class Views {

    public interface Id {}

    public interface IdTitleDetailsDescriptionJob extends Id {}

    public interface FullComment extends IdTitleDetailsDescriptionJob {}

    public interface FullAdvert extends IdTitleDetailsDescriptionJob {}
}
