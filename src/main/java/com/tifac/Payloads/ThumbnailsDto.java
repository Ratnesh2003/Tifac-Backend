package com.tifac.Payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailsDto {
    @JsonProperty("default")
    private ImageDto Default = new ImageDto();
    private ImageDto medium = new ImageDto();
    private ImageDto high = new ImageDto();
    private ImageDto standard = new ImageDto();
    private ImageDto maxres = new ImageDto();
}
