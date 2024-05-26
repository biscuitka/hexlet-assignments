package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getAll( @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int limit) {
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, posts.size());

        if(startIndex >= posts.size()) {
            return new ArrayList<>();
        }
        return posts.subList(startIndex, endIndex);
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getById(@PathVariable String id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post post) {
        Optional<Post> findingPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (findingPost.isPresent()) {
            Post updatingPost = findingPost.get();
            updatingPost.setId(post.getId());
            updatingPost.setBody(post.getBody());
            updatingPost.setTitle(post.getTitle());
            posts.add(updatingPost);
        }
        return post;
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END

}
