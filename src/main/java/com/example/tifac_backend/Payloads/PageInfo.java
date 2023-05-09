package com.example.tifac_backend.Payloads;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Long totalResults;
    private Long resultsPerPage;
}
