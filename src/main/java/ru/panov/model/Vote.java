package ru.panov.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import ru.panov.HasId;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint
        (name = "UniqueUserIdAndDateTimeVote", columnNames = {"user_id", "date_vote"})})
public class Vote extends AbstractBaseEntity implements HasId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "date_vote", nullable = false, columnDefinition = "date default now()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateVote = LocalDate.now();
}
