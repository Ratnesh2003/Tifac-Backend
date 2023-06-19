package com.tifac.Payloads;

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
