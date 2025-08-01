package one.nure.bookhive.Models;

import lombok.*;

import java.time.LocalDate;

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
