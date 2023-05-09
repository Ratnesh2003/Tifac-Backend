package com.example.tifac_backend.Repository;

import com.example.tifac_backend.Models.Video;
import com.example.tifac_backend.Models.VideoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, VideoId> {
    Page<Video> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}