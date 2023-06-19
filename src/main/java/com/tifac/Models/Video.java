package com.tifac.Models;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Video {
    private String etag;
    @Id
    private String id;
    private String videoId;
    private Date publishedAt;
    private String title;
    @Column(length = 10000)
    private String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Thumbnails thumbnails = new Thumbnails();
    @ManyToMany
    private Collection<PlayList> playLists = new ArrayList<>();

    public Video(String etag, String id, String videoId, Date publishedAt, String title, String description, Thumbnails thumbnails) {
        this.etag = etag;
        this.id = id;
        this.videoId = videoId;
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
    }
}

