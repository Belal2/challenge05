package banquemisr.challenge05.model.dto;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseDto {
    private Long id;
    @Transient
    private String createdBy;
    private LocalDateTime createdDate;
}
