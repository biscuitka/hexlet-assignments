package exercise.controller;

import exercise.dto.CommentMapper;
import exercise.dto.PostMapper;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getAll(){
        List<Post> posts = postRepository.findAll();
        List<Long> postIds = posts.stream().map(Post::getId).toList();
        List<Comment> comments = commentRepository.findByPostIdIn(postIds);

        Map<Post, List<CommentDTO>> map = new HashMap<>();
        List<CommentDTO> postComments = new ArrayList<>();
        for (Post post:posts){
            for (Comment comment:comments){
                if (comment.getPostId() == post.getId()){
                    postComments.add(CommentMapper.fromCommentToDto(comment));
                }
            }
            map.put(post, postComments);
        }
        return PostMapper.fromPostToDtoList(map);
    }

    @GetMapping(path = "/{id}")
    public PostDTO getById(@PathVariable Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id "+ id    +" not found"));
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        List<CommentDTO> commentDTOS = CommentMapper.fromCommentListToDto(comments);
        return PostMapper.fromPostToDto(post, commentDTOS);
    }
}
// END
