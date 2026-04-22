package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.CompositeKeys.FriendshipId;
import one.nure.bookhive.Models.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {
}
