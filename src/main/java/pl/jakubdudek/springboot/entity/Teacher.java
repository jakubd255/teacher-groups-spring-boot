package pl.jakubdudek.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.jakubdudek.springboot.enumerate.TeacherCondition;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column(name = "teacherCondition", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PRESENT'")
    private TeacherCondition condition;

    @Column
    private int birthYear;

    @Column
    @ColumnDefault("3000")
    private int salary;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "groupId")
    private Group group;

    @Transient
    private Long groupId;

    public Teacher(String name, String lastName, TeacherCondition condition, int birthYear, int salary) {
        this.name = name;
        this.lastName = lastName;
        this.condition = condition;
        this.birthYear = birthYear;
        this.salary = salary;
    }

    public Teacher(String name, String lastName, int birthYear, int salary) {
        this.name = name;
        this.lastName = lastName;
        this.condition = TeacherCondition.PRESENT;
        this.birthYear = birthYear;
        this.salary = salary;
    }

    public Teacher(Long id, String name, String lastName, TeacherCondition condition, int birthYear, int salary, Long groupId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.condition = condition;
        this.birthYear = birthYear;
        this.salary = salary;
        this.groupId = groupId;
    }
}