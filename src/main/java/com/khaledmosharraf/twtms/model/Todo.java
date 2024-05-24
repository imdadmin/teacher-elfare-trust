package com.khaledmosharraf.twtms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {
    @NotNull
    @Id
    @GeneratedValue
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
