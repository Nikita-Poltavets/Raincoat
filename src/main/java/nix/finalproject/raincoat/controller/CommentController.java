package nix.finalproject.raincoat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nix.finalproject.raincoat.domain.Comment;
import nix.finalproject.raincoat.domain.User;
import nix.finalproject.raincoat.domain.Views;
import nix.finalproject.raincoat.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(
            @RequestBody Comment comment,
            @AuthenticationPrincipal User user
    ){
        return commentService.create(comment, user);
    }
}
