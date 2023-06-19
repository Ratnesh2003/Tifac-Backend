package com.tifac.Payloads;

import com.tifac.Models.Thumbnails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListListDto {
    private String id;
    private String etag;
    private Date publishedAt;
    private String title;
    private String description;
    private Thumbnails thumbnails = new Thumbnails();
    private List<VideoListDto> playlistItems = new ArrayList<>();
}
