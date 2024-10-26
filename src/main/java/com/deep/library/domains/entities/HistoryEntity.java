package com.deep.library.domains.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "History")
public class HistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_generator")
    @SequenceGenerator(name = "history_id_generator", sequenceName = "history_id_seq")
    @Column(name = "history_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "record_date", nullable = false)
    private Instant recordDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
}
