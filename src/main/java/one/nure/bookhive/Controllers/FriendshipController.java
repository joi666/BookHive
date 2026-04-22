package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.CompositeKeys.FriendshipId;
import one.nure.bookhive.Models.DataTransferObjects.FriendshipDTO;
import one.nure.bookhive.Models.Friendship;
import one.nure.bookhive.Services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping(path = "/create")
    public FriendshipDTO CreateRequest(@RequestParam UUID userId1, @RequestParam UUID userId2) {
        return friendshipService.CreateRequest(userId1, userId2);
    }

    @PutMapping(path = "/accept")
    public FriendshipDTO AcceptRequest(@RequestParam UUID userId1, @RequestParam UUID userId2) {
        return friendshipService.AcceptRequest(userId1, userId2);
    }

    @PutMapping(path = "/decline")
    public void DeclineRequest(@RequestParam UUID userId1, @RequestParam UUID userId2) {
        friendshipService.DeclineRequest(userId1, userId2);
    }

    @DeleteMapping(path = "/delete")
    public void DeleteRequest(@RequestParam UUID userId1, @RequestParam UUID userId2) {
        friendshipService.DeleteRequest(userId1, userId2);
    }
}