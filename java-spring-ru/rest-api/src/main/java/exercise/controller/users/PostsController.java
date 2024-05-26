package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts")
    public List<Post> getAllByUser(@PathVariable Integer id) {
        return posts.stream()
                .filter(post -> post.getUserId() == id).toList();
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createByUser(@PathVariable Integer id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }
}
// END
