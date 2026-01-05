package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.Dialog;
import one.nure.bookhive.Services.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/dialogs")
public class DialogController {

    private final DialogService dialogService;

    @Autowired
    public DialogController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @PostMapping(path = "/create")
    public Dialog createDialog(@RequestParam UUID user1Id, @RequestParam UUID user2Id) {
        Set<UUID> userIds = new HashSet<>();
        return dialogService.createDialog(userIds);
    }

    @GetMapping(path = "/get/{userId}")
    public List<Dialog> getUserDialogs(@PathVariable UUID userId) {
        return dialogService.getUserDialogs(userId);
    }

    @DeleteMapping(path = "/delete/{dialogId}")
    public void deleteDialog(@PathVariable Long dialogId) {
        dialogService.deleteDialog(dialogId);
    }
}
