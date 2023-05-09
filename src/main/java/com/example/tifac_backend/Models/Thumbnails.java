package com.example.tifac_backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Thumbnails {
    @Id
    @JsonIgnore
    private String videoId;
    @JsonProperty("default")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image Default = new Image();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image medium = new Image();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image high = new Image();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image standard = new Image();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image maxres = new Image();

    public Thumbnails(String videoId, Image aDefault, Image medium, Image high, Image standard, Image maxres) {
        this.videoId = videoId;
        this.Default = aDefault;
        this.medium = medium;
        this.high = high;
        this.standard = standard;
        this.maxres = maxres;
    }
}
