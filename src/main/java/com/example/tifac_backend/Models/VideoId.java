package com.example.tifac_backend.Models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class VideoId implements Serializable {
    private String etag;
    private String id;
    private String videoId;
}
