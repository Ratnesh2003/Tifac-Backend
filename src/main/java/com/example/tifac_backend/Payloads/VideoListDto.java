package com.example.tifac_backend.Payloads;
import com.example.tifac_backend.Models.Thumbnails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoListDto {
    private String id;
    private String videoId;
    private Date publishedAt;
    private String title;
    private Thumbnails thumbnails;
}
