package com.example.tifac_backend.Payloads.PlayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto {
    private String etag;
    private String id;
    private PlayListSnippet snippet;
}
