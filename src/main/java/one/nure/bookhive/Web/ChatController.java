package one.nure.bookhive.Web;

import one.nure.bookhive.Models.DataTransferObjects.MessageDTO;
import one.nure.bookhive.Models.Message;
import one.nure.bookhive.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDTO message, Principal principal) {
        Message savedMessage = messageService.sendMessage(
                message.getDialogId(),
                UUID.fromString(principal.getName()),
                message.getContent()
        );

        messagingTemplate.convertAndSend(
                "/topic/dialog/" + message.getDialogId(),
                savedMessage
        );
    }
}
