package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_sequence")
    @SequenceGenerator(name = "todo_sequence", sequenceName = "TODO_SEQUENCE", allocationSize = 1)
    private Integer id;
    @NotNull
    @NotBlank
    private String username;
    @Size(min = 8,message = "Opps! Enter atleast 8 characters.")
    private String description;
    @NotNull
    @Future
    private LocalDate targetedDate;
    @NotNull
    private boolean done;

}
