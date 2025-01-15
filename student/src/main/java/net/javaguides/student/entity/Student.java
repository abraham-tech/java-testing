package net.javaguides.student.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    private String email;

    public Student() {
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
