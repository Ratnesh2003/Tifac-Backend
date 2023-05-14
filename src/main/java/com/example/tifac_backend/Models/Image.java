package com.example.tifac_backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @JsonIgnore
    private String id;
    private String url;
    private Integer width;
    private Integer height;
}
