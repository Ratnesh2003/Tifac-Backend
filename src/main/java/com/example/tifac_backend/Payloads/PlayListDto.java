package com.example.tifac_backend.Payloads;
import com.example.tifac_backend.Models.Thumbnails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListDto {
    private String id;
    private Date publishedAt;
    private String title;
    private Thumbnails thumbnails = new Thumbnails();
}
