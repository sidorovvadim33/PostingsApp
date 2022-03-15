package main.controller;

import main.entity.Comment;
import main.entity.User;
import main.service.PostService;
import main.service.CommentService;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class CommentController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/addComment")
    public String addPostComment(@ModelAttribute("commentForm") @Valid Comment commentForm,
                                 @RequestParam String postId,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal,
                                 Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "post?id=" + postId;
        }


        commentForm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentForm.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        User postAuthor = userService.findUserByUsername(principal.getName());
        commentForm.setUser(postAuthor);

        Long postIdToLong = Long.valueOf(postId);
        commentForm.setPost(postService.findPostById(postIdToLong));

        commentService.saveComment(commentForm);
        return "redirect:/post?id=" + postId;
    }
}
