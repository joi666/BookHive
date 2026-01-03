package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long bookId;
    private Set<String> genres;
    private Set<String> authors;
    private String title;
    private LocalDate publishing_year;
    private Integer pages;
    private Double rating;
}
