package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "notes")
public class Note {

    @Id
    private UUID id;
    private String title;

    public Note(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }
}
