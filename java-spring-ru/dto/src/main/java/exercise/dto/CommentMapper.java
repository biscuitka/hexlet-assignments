package exercise.dto;

import exercise.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDTO fromCommentToDto(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }

    public static List<CommentDTO> fromCommentListToDto(List<Comment> comments) {
        List<CommentDTO> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(fromCommentToDto(comment));
        }
        return dtos;
    }
}
