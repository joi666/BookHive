package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.Message;
import one.nure.bookhive.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(path = "/get/{dialogId}/{userId}")
    public List<Message> getDialogMessages(@PathVariable Long dialogId, @PathVariable UUID userId) {
        return messageService.getDialogMessages(dialogId, userId);
    }

    @PostMapping(path = "/create/{dialogId}/{userId}")
    public Message sendMessage(@PathVariable Long dialogId, @PathVariable UUID userId, @RequestParam String content) {
        return messageService.sendMessage(dialogId, userId, content);
    }

    @PutMapping(path = "/read/{dialogId}/{userId}")
    public void markAsRead(@PathVariable Long dialogId, @PathVariable UUID userId) {
        messageService.markAsRead(dialogId, userId);
    }
}
