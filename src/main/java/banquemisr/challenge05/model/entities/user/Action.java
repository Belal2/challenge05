package banquemisr.challenge05.model.entities.user;

import banquemisr.challenge05.model.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "action_test")
public class Action extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "actions")
    private Set<Role> roles = new HashSet<>();

}
