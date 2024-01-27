package pl.jakubdudek.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private int rate;

    @Column
    private String comment;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "groupId")
    private Group group;

    @Transient
    private Long groupId;

    public Rate(int rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }

    public Rate(int rate, String comment, Long groupId) {
        this.rate = rate;
        this.comment = comment;
        this.groupId = groupId;
    }

    @JsonIgnore
    public boolean isValid() {
        return rate >= 0 && rate <= 6;
    }
}