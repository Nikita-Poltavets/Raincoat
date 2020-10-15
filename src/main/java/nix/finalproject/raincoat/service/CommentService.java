package nix.finalproject.raincoat.service;

import nix.finalproject.raincoat.domain.Comment;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        commentRepository.save(comment);

        return comment;
    }

}
