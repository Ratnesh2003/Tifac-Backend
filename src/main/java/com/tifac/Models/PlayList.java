package com.tifac.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PlayList {
    @Id
    private String id;
    private String etag;
    private Date publishedAt;
    private String title;
    @Column(length = 10000)
    private String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Thumbnails thumbnails = new Thumbnails();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "playLists")
    private List<Video> playlistItems = new ArrayList<>();
    public PlayList(String id, String etag, Date publishedAt, String title, String description, Thumbnails thumbnails) {
        this.id = id;
        this.etag = etag;
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
    }
}
