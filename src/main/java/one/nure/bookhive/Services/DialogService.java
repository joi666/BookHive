package one.nure.bookhive.Services;

import one.nure.bookhive.Models.Dialog;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.DialogRepository;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DialogService {

    private final DialogRepository dialogRepository;
    private final UserRepository userRepository;

    @Autowired
    public DialogService(DialogRepository dialogRepository, UserRepository userRepository) {
        this.dialogRepository = dialogRepository;
        this.userRepository = userRepository;
    }

    public Dialog createDialog(Set<UUID> userIds) {
        Set<User> users = new HashSet<>();

        for (UUID userId: userIds) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("User not found with id: " + userId));
            users.add(user);
        }

        Dialog dialog = new Dialog(users);
        return dialogRepository.save(dialog);
    }

    public List<Dialog> getUserDialogs(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        return dialogRepository.findByParticipants_UserId(userId);
    }

    public Boolean isParticipant(Long dialogId, UUID userId) {
        Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(() ->
                new IllegalArgumentException("Dialog not found"));

        return dialog.getParticipants().contains(userId);
    }

    public void deleteDialog(Long dialogId) {
        if (!dialogRepository.existsById(dialogId)) {
            throw new IllegalArgumentException("Dialog not found with id: " + dialogId);
        }
        dialogRepository.deleteById(dialogId);
    }
}
