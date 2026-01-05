package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    List<Dialog> findByParticipants_UserId(UUID userId);
}
