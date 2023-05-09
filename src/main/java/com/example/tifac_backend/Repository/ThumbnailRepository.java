package com.example.tifac_backend.Repository;

import com.example.tifac_backend.Models.Thumbnails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnails, String> {
}
