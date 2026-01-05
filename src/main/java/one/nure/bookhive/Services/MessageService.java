package one.nure.bookhive.Services;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import one.nure.bookhive.Models.Dialog;
import one.nure.bookhive.Models.Message;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.DialogRepository;
import one.nure.bookhive.Repositories.MessageRepository;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final DialogRepository dialogRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository, DialogRepository dialogRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.dialogRepository = dialogRepository;
    }

    public Message sendMessage(Long dialogId, UUID userId, String content) {
        Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(() ->
                new IllegalArgumentException("Dialog not found with id: " + dialogId));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + userId));

        return messageRepository.save(new Message(dialog, user, content));

    }

    public List<Message> getDialogMessages(Long dialogId, UUID userId) {
        if (!dialogRepository.existsById(dialogId)) {
            throw new IllegalArgumentException("Dialog not found with id: " + dialogId);
        }
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        return messageRepository.getDialogMessages(dialogId, userId);
    }

    public void markAsRead(Long dialogId, UUID userId) {
        if (!dialogRepository.existsById(dialogId)) {
            throw new IllegalArgumentException("Dialog not found with id: " + dialogId);
        }
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        List<Message> unreadMessages = messageRepository.findMessageByDialogIdAndUser_UserIdAndIsReadFalse(dialogId, userId);

        for (Message message : unreadMessages) {
            message.setIsRead(true);
            messageRepository.save(message);
        }
    }
}
