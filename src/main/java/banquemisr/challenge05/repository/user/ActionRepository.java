package banquemisr.challenge05.repository.user;

import banquemisr.challenge05.model.entities.user.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Optional<Action> findByName(String name);
}
