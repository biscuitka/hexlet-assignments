package exercise.dto;

import exercise.model.Comment;
import exercise.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostMapper {
    public static PostDTO fromPostToDto(Post post, List<CommentDTO> comments){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setBody(post.getBody());
        postDTO.setTitle(post.getTitle());
        postDTO.setComments(comments);
        return postDTO;
    }

    public static List<PostDTO> fromPostToDtoList(Map<Post, List<CommentDTO>> posts){
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Map.Entry<Post, List<CommentDTO>> entry: posts.entrySet()){
            postDTOList.add(fromPostToDto(entry.getKey(), entry.getValue()));
        }
        return postDTOList;
    }
}
