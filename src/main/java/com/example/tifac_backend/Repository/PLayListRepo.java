package com.example.tifac_backend.Repository;

import com.example.tifac_backend.Models.PlayList;
import com.example.tifac_backend.Models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PLayListRepo extends JpaRepository<PlayList, String> {
    Page<PlayList> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
