package com.example.tifac_backend.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    private String url;
    private Integer width;
    private Integer height;
}
