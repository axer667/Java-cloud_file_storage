package ru.netology.cloudstorage.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class FileEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "filesSequence", sequenceName = "file_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filesSequence")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Column(name = "body")
    private byte[] body;

    public FileEntity(String name, UserEntity user, long size, String type, byte[] body) {
        this.name = name;
        this.user = user;
        this.size = size;
        this.type = type;
        this.body = body;
    }
}