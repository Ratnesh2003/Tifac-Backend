package com.example.tifac_backend.Payloads.Videos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String kind;
    private String etag;
    private String id;
    private SnippetDto snippet = new SnippetDto();
    private ContentDetailsDto contentDetails = new ContentDetailsDto();
}