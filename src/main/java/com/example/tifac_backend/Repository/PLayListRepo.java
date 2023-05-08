package com.example.tifac_backend.Repository;

import com.example.tifac_backend.Models.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PLayListRepo extends JpaRepository<PlayList, String> {
}
