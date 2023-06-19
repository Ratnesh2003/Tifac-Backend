package com.tifac.Payloads.PlayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto {
    private String etag;
    private String id;
    private PlayListSnippet snippet;
}
