package com.tifac.Payloads.PlayList;
import com.tifac.Payloads.PageInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayListResponse {
    private String etag;
    private String prevPageToken;
    private String nextPageToken;
    private PageInfo pageInfo;
    @OneToMany(mappedBy = "youtubeResponse", cascade = CascadeType.ALL)
    private List<PlaylistDto> items = new ArrayList<>();
}
