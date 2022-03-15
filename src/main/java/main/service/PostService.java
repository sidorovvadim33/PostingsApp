package main.service;

import main.entity.Post;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PostService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    PostRepository postRepository;

    public boolean savePost(Post post) {
        postRepository.save(post);
        return true;
    }

    public List<Post> allPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        Post post = postRepository.findPostById(id);

        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        return post;
    }
}
