package com.deep.library.domains.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Book")
public class BookEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name = "book_id_generator", sequenceName = "book_id_seq")
    @Column(name = "book_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "descrip")
    private String description;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "book")
    private List<HistoryEntity> borrowHistory;

    public BookEntity(Long id, String title, String description, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isAvailable = isAvailable;
    }
}