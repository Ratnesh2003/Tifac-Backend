package com.example.tifac_backend.Payloads.PlayList;

import com.example.tifac_backend.Payloads.ThumbnailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayListSnippet {
    private Date publishedAt;
    private String channelId;
    private String title;
    private String description;
    private ThumbnailsDto thumbnails = new ThumbnailsDto();
}
