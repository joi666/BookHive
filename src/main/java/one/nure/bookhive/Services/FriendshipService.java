package one.nure.bookhive.Services;

import one.nure.bookhive.Models.CompositeKeys.FriendshipId;
import one.nure.bookhive.Models.DataTransferObjects.FriendshipDTO;
import one.nure.bookhive.Models.Friendship;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.FriendshipRepository;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public FriendshipDTO CreateRequest(UUID senderId, UUID recipientId) {
        User sender = userRepository.findById(senderId).orElseThrow(() ->
                new IllegalArgumentException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() ->
                new IllegalArgumentException("Recipient not found"));

        FriendshipId id = new FriendshipId(senderId, recipientId);

        if (friendshipRepository.existsById(id)) {
            Friendship existingFriendship = friendshipRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Friendship not found"));

            if (!Objects.equals(existingFriendship.getStatus(), "Pending")) {
                existingFriendship.setStatus("Pending");
                existingFriendship.setCreation_date(LocalDate.now());
            }
            else {
                throw new IllegalArgumentException("Request already pending");
            }
            friendshipRepository.save(existingFriendship);
            return ConvertToDTO(existingFriendship);
        }
        else {
            Friendship friendship = new Friendship(id, sender, recipient, LocalDate.now(), "Pending");
            return ConvertToDTO(friendship);
        }
    }

    public FriendshipDTO AcceptRequest(UUID senderId, UUID recipientId) {
        if (userRepository.existsById(senderId) && userRepository.existsById(recipientId)) {
            FriendshipId id = new FriendshipId(senderId, recipientId);

            Friendship existingFriendship = friendshipRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Friendship not found"));

            existingFriendship.setStatus("Accepted");
            existingFriendship.setCreation_date(LocalDate.now());

            friendshipRepository.save(existingFriendship);
        }
        else {
            throw new IllegalArgumentException("User(s) do not exist");
        }
        return null;
    }

    public void DeclineRequest(UUID senderId, UUID recipientId) {
        if (userRepository.existsById(senderId) && userRepository.existsById(recipientId)) {
            FriendshipId id = new FriendshipId(senderId, recipientId);

            Friendship existingFriendship = friendshipRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Friendship not found"));

            existingFriendship.setStatus("Declined");
            existingFriendship.setCreation_date(LocalDate.now()); // If request is declined for at least
                                                                  // 1 month - it needs to be deleted
            friendshipRepository.save(existingFriendship);
        }
        else {
            throw new IllegalArgumentException("User(s) do not exist");
        }
    }

    public void DeleteRequest(UUID senderId, UUID recipientId) {
        if (userRepository.existsById(senderId) && userRepository.existsById(recipientId)) {
            FriendshipId id = new FriendshipId(senderId, recipientId);
            friendshipRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("User(s) do not exist");
        }
    }

    public static FriendshipDTO ConvertToDTO(Friendship friendship) {
        FriendshipDTO friendshipDTO = new FriendshipDTO();
        friendshipDTO.setUserId1(friendship.getId().getUserId1());
        friendshipDTO.setUserId2(friendship.getId().getUserId2());
        friendshipDTO.setCreation_date(friendship.getCreation_date());
        friendshipDTO.setStatus(friendship.getStatus());
        return friendshipDTO;
    }
}
