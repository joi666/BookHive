package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookShortDTO {

    private Long bookId;
    private String title;
    private LocalDate publishingYear;
    private Double rating;

}
