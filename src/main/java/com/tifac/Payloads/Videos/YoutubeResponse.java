package com.tifac.Payloads.Videos;

import com.tifac.Payloads.PageInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeResponse {
    private String kind;
    private String etag;
    private String prevPageToken;
    private String nextPageToken;
    @OneToMany(mappedBy = "youtubeResponse", cascade = CascadeType.ALL)
    private List<VideoDto> items = new ArrayList<>();
    private PageInfo pageInfo;
}