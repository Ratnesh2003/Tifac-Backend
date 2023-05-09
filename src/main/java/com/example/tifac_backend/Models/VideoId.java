package com.example.tifac_backend.Models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class VideoId implements Serializable {
    private String etag;
    private String id;
    private String videoId;
}
