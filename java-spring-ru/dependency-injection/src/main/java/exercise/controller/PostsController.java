package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// BEGIN
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
public class PostsController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post getById(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return post;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post){
        return postRepository.save(post);
    }

    @PutMapping(path = "/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post post){
        Post postInRepo = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        Optional.of(post.getTitle()).ifPresent(postInRepo::setTitle);
        Optional.of(post.getBody()).ifPresent(postInRepo::setBody);
        return postRepository.save(postInRepo);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable Long id){
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }

}
// END
