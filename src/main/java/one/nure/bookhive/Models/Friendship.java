package one.nure.bookhive.Models;

import jakarta.persistence.*;
import lombok.*;
import one.nure.bookhive.Models.CompositeKeys.FriendshipId;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friendships")
public class Friendship {

    @EmbeddedId
    private FriendshipId id;

    @OneToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user1;

    @OneToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user2;

    private LocalDate creation_date;

    private String status;

}
