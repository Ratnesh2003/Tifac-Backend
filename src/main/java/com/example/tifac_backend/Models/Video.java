package com.example.tifac_backend.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Video {
    @EmbeddedId
    private VideoId videoId;
    private Date publishedAt;
    private String title;
    @Column(length = 10000)
    private String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Thumbnails thumbnails = new Thumbnails();
    @ManyToMany
    private Collection<PlayList> playLists;

    public Video(VideoId videoId, Date publishedAt, String title, String description, Thumbnails thumbnails) {
        this.videoId = videoId;
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
    }
}

