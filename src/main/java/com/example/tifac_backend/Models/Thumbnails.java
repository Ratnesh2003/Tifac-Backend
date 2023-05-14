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
    private Image Default;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image medium;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image high;

    public Thumbnails(String videoId, Image aDefault, Image medium, Image high) {
        this.videoId = videoId;
        this.Default = aDefault;
        this.medium = medium;
        this.high = high;
    }
}
