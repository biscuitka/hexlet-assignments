package exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class CommentsController {
    private final CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment getById(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource by id:" + id + " not found"));
        return comment;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment){
        return commentRepository.save(comment);
    }

    @PutMapping(path = "/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Comment comment){
        Comment commentInRepo = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource by id:" + id + " not found"));
        Optional.of(comment.getBody()).ifPresent(commentInRepo::setBody);
        return commentRepository.save(commentInRepo);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable Long id){
        commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource by id:" + id + " not found"));
        commentRepository.deleteById(id);
    }
}

// END
