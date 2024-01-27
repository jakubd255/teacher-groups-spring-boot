package pl.jakubdudek.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String groupName;

    @Column(nullable = false)
    @NonNull
    private int maxTeachers;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Teacher> teachers = new ArrayList<Teacher>();

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Rate> rates = new ArrayList<Rate>();

    public Group(Long id, String groupName, int maxTeachers) {
        this.id = id;
        this.groupName = groupName;
        this.maxTeachers = maxTeachers;
    }

    @JsonIgnore
    public double getPercentage() {
        return (double) teachers.size()*100.0/(double)maxTeachers;
    }

    @JsonIgnore
    public int getRatesCount() {
        return rates.size();
    }

    @JsonIgnore
    public double getAvarageRate() {
        if(rates.isEmpty())
            return 0;

        double sum = 0.0;
        for(Rate rate: rates) {
            sum += rate.getRate();
        }
        sum /= rates.size();
        return sum;
    }
}