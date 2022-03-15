package main.controller;

import main.entity.Post;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/posts")
    public String allPosts(Model model) {
        model.addAttribute("allPosts", postService.allPosts());
        model.addAttribute("postForm", new Post());

        return "posts";
    }

    @PostMapping("/posts")
    public String addPost(@ModelAttribute("postForm") @Valid Post postForm, BindingResult bindingResult, Model model, Principal principal, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "posts";
        }

        postForm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        postForm.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        User postAuthor = userService.findUserByUsername(principal.getName());
        postForm.setUser(postAuthor);
        postService.savePost(postForm);

        return "redirect:/posts";
    }

    @GetMapping("/post")
    public String specificPost(@RequestParam String id, Model model) {
        Long postId = Long.valueOf(id);
        Post specificPost = postService.findPostById(postId);

        model.addAttribute("specificPost", postService.findPostById(postId));
        model.addAttribute("allPostComments", commentService.allCommentsByPost(specificPost));
        model.addAttribute("commentForm", new Comment());

        return "post";
    }
}
