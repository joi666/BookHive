package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> getDialogMessages(Long dialogId, UUID userId);
    public List<Message> findMessageByDialogIdAndUser_UserIdAndIsReadFalse(Long dialogId, UUID userId);
}
