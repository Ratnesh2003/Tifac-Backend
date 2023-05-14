package com.example.tifac_backend.Repository;

import com.example.tifac_backend.Models.PlayList;
import com.example.tifac_backend.Models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PLayListRepo extends JpaRepository<PlayList, String> {

    @Query(value = "SELECT \"v.id\", \"v.video_id\", \"v.title\", \"p.id\", \"p.title\" from \"video\" \"v\", \"play_list\" \"p\" where \"\"", nativeQuery = true)
    PlayList findPlayListById(String id);

}
