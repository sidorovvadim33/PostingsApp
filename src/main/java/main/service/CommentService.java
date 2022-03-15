package main.service;

import main.entity.Post;
import main.entity.Comment;
import main.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class CommentService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CommentRepository commentRepository;


    public boolean saveComment(Comment comment) {
        commentRepository.save(comment);
        return true;
    }

    public List<Comment> allCommentsByPost(Post post) {
        List<Comment> commentList = commentRepository.findByPost_Id(post.getId());
        return commentList;
    }

}
