package com.example.tifac_backend.Payloads.Videos;
import com.example.tifac_backend.Payloads.ThumbnailsDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnippetDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date publishedAt;
    private String channelId;
    private String title;
    private String description;
    private ThumbnailsDto thumbnails = new ThumbnailsDto();
    private String channelTitle;
    private String playlistId;
    private Long position;
    private ResourceDto resourceId = new ResourceDto();
    private String videoOwnerChannelTitle;
    private String videoOwnerChannelId;
}