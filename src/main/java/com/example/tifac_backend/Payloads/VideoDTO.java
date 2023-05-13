package com.example.tifac_backend.Payloads;

import com.example.tifac_backend.Models.PlayList;
import com.example.tifac_backend.Models.Thumbnails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    private String etag;
    private String id;
    private String videoId;
    private Date publishedAt;
    private String title;
    private String description;
    private Thumbnails thumbnails;
    private Collection<PlayListDto> playLists;
}
