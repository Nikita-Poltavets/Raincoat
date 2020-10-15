package nix.finalproject.raincoat.service;

import nix.finalproject.raincoat.domain.Comment;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.dto.EventType;
import nix.finalproject.raincoat.dto.ObjectType;
import nix.finalproject.raincoat.repository.CommentRepository;
import nix.finalproject.raincoat.util.WsSender;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    public CommentService(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.IdTitleDetailsDescriptionJob.class);
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        Comment commentFromDb = commentRepository.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }

}
