package banquemisr.challenge05.model.entities.user;

import banquemisr.challenge05.model.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role_test")
public class Role extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_actions_test",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    private Set<Action> actions = new HashSet<>();

    public void addAction(Action action) {
        actions.add(action);
        action.getRoles().add(this);
    }

    public void removeAction(Action action) {
        actions.remove(action);
        action.getRoles().remove(this);
    }

}
