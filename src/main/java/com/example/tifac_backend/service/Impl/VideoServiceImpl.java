package com.example.tifac_backend.service.Impl;

import com.example.tifac_backend.Models.*;
import com.example.tifac_backend.Payloads.Message;
import com.example.tifac_backend.Payloads.PageResult.PageResponse;
import com.example.tifac_backend.Payloads.PageResult.PageableDto;
import com.example.tifac_backend.Payloads.PlayList.PlayListResponse;
import com.example.tifac_backend.Payloads.Videos.YoutubeResponse;
import com.example.tifac_backend.Repository.PLayListRepo;
import com.example.tifac_backend.Repository.VideoRepository;
import com.example.tifac_backend.service.FeignClient;
import com.example.tifac_backend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final FeignClient feignClient;
    private final ModelMapper modelMapper;
    private final PLayListRepo pLayListRepo;
    @Value("${key}")
    private String key;
    @Value("${channelId}")
    private String channelId;
    @Value("${playlistId}")
    private String playlistId;

    @Override
    public ResponseEntity<?> webScrapVideos() {
        try {
            String part = "snippet%2CcontentDetails";
            ResponseEntity<?> response = this.feignClient.getPlaylistItems(key, part, playlistId,50,null);

            YoutubeResponse youtubeResponse = this.modelMapper.map(response.getBody(), YoutubeResponse.class);

            youtubeResponse.getItems().forEach((youtubeVideo)-> {
                Thumbnails thumbnails = new Thumbnails(
                        youtubeVideo.getContentDetails().getVideoId(),
                        this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getDefault(), Image.class),
                        this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getMedium(), Image.class),
                        this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getHigh(), Image.class),
                        this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getStandard(), Image.class),
                        this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getMaxres(), Image.class)
                );
                Video videoModel = new Video(new VideoId(youtubeVideo.getEtag(), youtubeVideo.getId(), youtubeVideo.getContentDetails().getVideoId()), youtubeVideo.getContentDetails().getVideoPublishedAt(), youtubeVideo.getSnippet().getTitle(), youtubeVideo.getSnippet().getDescription(), thumbnails);
                this.videoRepository.save(videoModel);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Message("The Youtube Response Has Been Fetched and Saved"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> webScrapPlayList() {
        try {
            String part = "snippet%2CcontentDetails";
            ResponseEntity<?> response = this.feignClient.getAllPlaylist(key, part, channelId,50,null);
            PlayListResponse youtubeResponse = this.modelMapper.map(response.getBody(), PlayListResponse.class);
            youtubeResponse.getItems().forEach((youtubePlayList)-> {
                Thumbnails thumbnails = new Thumbnails(
                        youtubePlayList.getId(),
                        this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getDefault(), Image.class),
                        this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getMedium(), Image.class),
                        this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getHigh(), Image.class),
                        this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getStandard(), Image.class),
                        this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getMaxres(), Image.class)
                );
                PlayList playListModel = new PlayList(youtubePlayList.getId(), youtubePlayList.getEtag(), youtubePlayList.getSnippet().getPublishedAt(), youtubePlayList.getSnippet().getTitle(), youtubePlayList.getSnippet().getDescription(), thumbnails);
                this.pLayListRepo.save(playListModel);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Message("The Youtube Response Has Been Fetched and Saved"), HttpStatus.OK);
    }

    @Override
    public PageResponse searchVideos(String value, PageableDto pageable){
        Integer pN = pageable.getPageNumber(), pS = pageable.getPageSize();
        Sort sort = null;
        if(pageable.getSortDir().equalsIgnoreCase("asc")) {
            sort = Sort.by(pageable.getSortBy()).ascending();
        }
        else {
            sort = Sort.by(pageable.getSortBy()).descending();
        }
        Pageable p = PageRequest.of(pN, pS, sort);
        Page<Video> pageVideos = this.videoRepository.findByTitleContainingIgnoreCase(value, p);
        List<Video> getVideos = pageVideos.getContent();
        return new PageResponse(new ArrayList<>(getVideos), pageVideos.getNumber(), pageVideos.getSize(), pageVideos.getTotalPages(), pageVideos.getTotalElements(), pageVideos.isLast());
    }

    @Override
    public PageResponse getAllVideos(PageableDto pageable){
        Integer pN = pageable.getPageNumber(), pS = pageable.getPageSize();
        Sort sort = null;
        if(pageable.getSortDir().equalsIgnoreCase("asc")) {
            sort = Sort.by(pageable.getSortBy()).ascending();
        }
        else {
            sort = Sort.by(pageable.getSortBy()).descending();
        }
        Pageable p = PageRequest.of(pN, pS, sort);
        Page<Video> pageVideos = this.videoRepository.findAll(p);
        List<Video> getVideos = pageVideos.getContent();
        return new PageResponse(new ArrayList<>(getVideos), pageVideos.getNumber(), pageVideos.getSize(), pageVideos.getTotalPages(), pageVideos.getTotalElements(), pageVideos.isLast());
    }
}
