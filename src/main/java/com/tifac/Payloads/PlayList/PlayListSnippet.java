package com.tifac.Payloads.PlayList;

import com.tifac.Payloads.ThumbnailsDto;
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
