package com.tifac.Repository;

import com.tifac.Models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Page<Video> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Optional<Video> findByVideoId(String videoId);
}