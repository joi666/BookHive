package one.nure.bookhive.comment;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long comment_id;
    private String user_name;
    private String user_lastname;
    private Integer book_rating;
    private String comment_body;
    private Integer comment_likes;
    private LocalDate comment_date;
}
