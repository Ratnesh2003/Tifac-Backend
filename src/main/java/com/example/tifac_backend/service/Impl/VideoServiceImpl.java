package com.example.tifac_backend.service.Impl;
import com.example.tifac_backend.Models.*;
import com.example.tifac_backend.Payloads.Message;
import com.example.tifac_backend.Payloads.PageResult.PageResponse;
import com.example.tifac_backend.Payloads.PageResult.PageableDto;
import com.example.tifac_backend.Payloads.PlayList.PlayListResponse;
import com.example.tifac_backend.Payloads.PlayListDto;
import com.example.tifac_backend.Payloads.VideoListDto;
import com.example.tifac_backend.Payloads.Videos.VideoDto;
import com.example.tifac_backend.Payloads.Videos.YoutubeResponse;
import com.example.tifac_backend.Repository.PLayListRepo;
import com.example.tifac_backend.Repository.VideoRepository;
import com.example.tifac_backend.service.FeignClient;
import com.example.tifac_backend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.OK;

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
    public String part = "snippet%2CcontentDetails";

    //---------------------------------------------------- Database Service ---------------------------------------------------
    public ResponseEntity<?> webScrapVideos() {
        AtomicInteger i= new AtomicInteger(0);
        try {
            String nextToken = null;
            do {
                ResponseEntity<?> response = this.feignClient.getPlaylistItems(key, part, playlistId, 50, nextToken);
                YoutubeResponse youtubeResponse = this.modelMapper.map(response.getBody(), YoutubeResponse.class);
                youtubeResponse.getItems().forEach((youtubeVideo) -> {
                    System.out.println("\n"+i+"\t"+youtubeVideo.getContentDetails().getVideoId()+"\n");
                    Thumbnails thumbnails = createThumbnail(youtubeVideo);
                    if(!youtubeVideo.getSnippet().getTitle().equals("Private video") || youtubeVideo.getContentDetails().getVideoPublishedAt() != null) {
                        Video videoModel = new Video(youtubeVideo.getEtag(), youtubeVideo.getId(), youtubeVideo.getContentDetails().getVideoId(), youtubeVideo.getContentDetails().getVideoPublishedAt(), youtubeVideo.getSnippet().getTitle(), youtubeVideo.getSnippet().getDescription(), thumbnails);
                        this.videoRepository.save(videoModel);
                    }
                    i.addAndGet(1);
                });
                nextToken = youtubeResponse.getNextPageToken();
                System.out.println("\n\n"+youtubeResponse.getNextPageToken()+"\t"+youtubeResponse.getPrevPageToken()+"\n\n");
            }while(nextToken!=null);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Message("The Youtube Response Has Been Fetched and Saved"), OK);
    }

    private Thumbnails createThumbnail(VideoDto youtubeVideo) {
        System.out.println( "Neeche dekho \n"+ youtubeVideo.getContentDetails() + "\n Upar dekho");
        youtubeVideo.getSnippet().getThumbnails().getDefault().setId(youtubeVideo.getContentDetails().getVideoId().concat("Default"));
        youtubeVideo.getSnippet().getThumbnails().getMedium().setId(youtubeVideo.getContentDetails().getVideoId().concat("Medium"));
        youtubeVideo.getSnippet().getThumbnails().getHigh().setId(youtubeVideo.getContentDetails().getVideoId().concat("High"));
        return new Thumbnails(
                youtubeVideo.getContentDetails().getVideoId(),
                this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getDefault(), Image.class),
                this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getMedium(), Image.class),
                this.modelMapper.map(youtubeVideo.getSnippet().getThumbnails().getHigh(), Image.class)
        );
    }

    @Override
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Kolkata")
    public void webScrapPlayListContent() {
//        webScrapVideos();
        webScrapPlayList();
        List<PlayList> playlists = this.pLayListRepo.findAll();
        if (!playlists.isEmpty()) {
            playlists.forEach((youtubePlaylist) -> {
                String pageToken = null;
                do {
                    ResponseEntity<?> response = this.feignClient.getPlaylistItems(key, part, youtubePlaylist.getId(), 50, pageToken);
                    YoutubeResponse youtubeResponse = this.modelMapper.map(response.getBody(), YoutubeResponse.class);
                    AtomicInteger i= new AtomicInteger(1);
                    if (!youtubeResponse.getItems().isEmpty())
                        youtubeResponse.getItems().forEach((youtubeVideo) -> {
                            try {
                                System.out.println("\n" + i + "\t" + youtubeVideo.getContentDetails().getVideoId() + "\n");
                                Optional<Video> optionalVideo = this.videoRepository.findByVideoId(youtubeVideo.getContentDetails().getVideoId());
                                Video videoModel;
                                if (optionalVideo.isPresent()) {
                                    videoModel = optionalVideo.get();
                                } else {
                                    Thumbnails thumbnails = createThumbnail(youtubeVideo);
                                    videoModel = new Video(
                                            youtubeVideo.getEtag(),
                                            youtubeVideo.getId(),
                                            youtubeVideo.getContentDetails().getVideoId(),
                                            youtubeVideo.getContentDetails().getVideoPublishedAt(),
                                            youtubeVideo.getSnippet().getTitle(),
                                            youtubeVideo.getSnippet().getDescription(),
                                            thumbnails
                                    );
                                }
                                if(!youtubeVideo.getSnippet().getTitle().equals("Private video") || youtubeVideo.getContentDetails().getVideoPublishedAt() != null){
                                    if(!videoModel.getPlayLists().contains(youtubePlaylist)) {
                                        videoModel.getPlayLists().add(youtubePlaylist);
                                        this.videoRepository.saveAndFlush(videoModel);
                                    }
                                    if(!youtubePlaylist.getPlaylistItems().contains(videoModel)) {
                                        youtubePlaylist.getPlaylistItems().add(videoModel);
                                        this.pLayListRepo.saveAndFlush(youtubePlaylist);
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            i.addAndGet(1);
                        });
                    pageToken = youtubeResponse.getNextPageToken();
                    this.pLayListRepo.saveAndFlush(youtubePlaylist);
                    System.out.println("\n\n" + youtubeResponse.getNextPageToken() + "\t" + youtubeResponse.getPrevPageToken() + "\t" + youtubePlaylist.getId() +"\n\n");
                } while (pageToken != null);
            });
        }
    }

    public void webScrapPlayList() {
        try {
            AtomicInteger i= new AtomicInteger();
            String nextToken = null;
            do {
                ResponseEntity<?> response = this.feignClient.getAllPlaylist(key, part, channelId, 50, nextToken);
                PlayListResponse youtubeResponse = this.modelMapper.map(response.getBody(), PlayListResponse.class);
                try {
                    youtubeResponse.getItems().forEach((youtubePlayList) -> {
                        System.out.println(i + "\t" + youtubePlayList.getId());
                        youtubePlayList.getSnippet().getThumbnails().getDefault().setId(youtubePlayList.getId().concat("Default"));
                        youtubePlayList.getSnippet().getThumbnails().getMedium().setId(youtubePlayList.getId().concat("Medium"));
                        youtubePlayList.getSnippet().getThumbnails().getHigh().setId(youtubePlayList.getId().concat("High"));
                        Thumbnails thumbnails = new Thumbnails(
                                youtubePlayList.getId(),
                                this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getDefault(), Image.class),
                                this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getMedium(), Image.class),
                                this.modelMapper.map(youtubePlayList.getSnippet().getThumbnails().getHigh(), Image.class)
                        );
                        if(youtubePlayList.getSnippet().getPublishedAt() != null) {
                            PlayList playListModel = new PlayList(youtubePlayList.getId(), youtubePlayList.getEtag(), youtubePlayList.getSnippet().getPublishedAt(), youtubePlayList.getSnippet().getTitle(), youtubePlayList.getSnippet().getDescription(), thumbnails);
                            this.pLayListRepo.save(playListModel);
                        }
                        i.addAndGet(1);
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("\n\n"+ youtubeResponse.getNextPageToken() + "\t"+ youtubeResponse.getPrevPageToken() + "\n\n");
                nextToken = youtubeResponse.getNextPageToken();
            } while(nextToken!=null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateVideo(String jsonString){
        JSONObject json = new JSONObject(jsonString);
        System.out.println("Second print: " + json);
        boolean isVideoToBeDeleted = json.has("feed") && json.getJSONObject("feed").has("at:deleted-entry");
        String videoId;
        if (isVideoToBeDeleted) {
            videoId = json.getJSONObject("feed").getJSONObject("at:deleted-entry").getString("ref");
            if(videoId==null){
                System.out.println("\n\nVideo ID is not able to determined\n");
                return;
            }
            deleteAVideo(videoId);
            System.out.println("Video 1 deleted: " + videoId);
        } else {
            videoId = json.getJSONObject("feed").getJSONObject("entry").getString("yt:videoId");
            if(videoId==null){
                System.out.println("\n\nVideo ID is not able to determined\n");
                return;
            }
            ResponseEntity<?> response = this.feignClient.getTheChannelDetails(key, part, videoId);
            YoutubeResponse youtubeResponse = this.modelMapper.map(response.getBody(), YoutubeResponse.class);

            try {
                if(!youtubeResponse.getItems().isEmpty())
                    youtubeResponse.getItems().forEach((youtubeVideo) -> {
                        Thumbnails thumbnails = createThumbnail(youtubeVideo);
                        System.out.println("Yaha tak chal gya: \n" + thumbnails);

                        if(!youtubeVideo.getSnippet().getTitle().equals("Private video") || youtubeVideo.getContentDetails().getVideoPublishedAt() != null) {
                            Video videoModel = new Video(youtubeVideo.getEtag(), youtubeVideo.getId(), videoId, youtubeVideo.getSnippet().getPublishedAt(), youtubeVideo.getSnippet().getTitle(), youtubeVideo.getSnippet().getDescription(), thumbnails);
                            this.videoRepository.save(videoModel);
                        }
                    });
            } catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("Video updated with videoId: " + videoId);
        }
    }

    public void deleteAVideo(String videoId){
        Optional<Video> video = this.videoRepository.findByVideoId(videoId);
        if(video.isEmpty())
            return;
        this.videoRepository.delete(video.get());
    }

    // --------------------------------------- Service By SpringBoot -----------------------------------------------------------------------
    @Override
    public PageResponse searchVideos(String value, PageableDto pageable){
        Page<Video> pageVideos = this.videoRepository.findByTitleContainingIgnoreCase(value, createPaginationRequest(pageable));
        List<Video> getVideos = pageVideos.getContent();
        List<VideoListDto> videoList = getVideos.stream().map((video) -> this.modelMapper.map(video, VideoListDto.class)).toList();
        return new PageResponse(new ArrayList<>(videoList), pageVideos.getNumber(), pageVideos.getSize(), pageVideos.getTotalPages(), pageVideos.getTotalElements(), pageVideos.isLast());
    }

    @Override
    public PageResponse searchPlaylist(String value, PageableDto pageable){
        Page<PlayList> pagePlaylist = this.pLayListRepo.findByTitleContainingIgnoreCase(value, createPaginationRequest(pageable));
        List<PlayList> getPLayList = pagePlaylist.getContent();
        List<PlayListDto> playlistList = getPLayList.stream().map((playList) -> this.modelMapper.map(playList, PlayListDto.class)).toList();
        return new PageResponse(new ArrayList<>(playlistList), pagePlaylist.getNumber(), pagePlaylist.getSize(), pagePlaylist.getTotalPages(), pagePlaylist.getTotalElements(), pagePlaylist.isLast());
    }

    @Override
    public PageResponse getAllVideos(PageableDto pageable){
        Page<Video> pageVideos = this.videoRepository.findAll(createPaginationRequest(pageable));
        List<VideoListDto> videoList = pageVideos.getContent().stream().map((video) -> this.modelMapper.map(video, VideoListDto.class)).toList();
        return new PageResponse(new ArrayList<>(videoList), pageVideos.getNumber(), pageVideos.getSize(), pageVideos.getTotalPages(), pageVideos.getTotalElements(), pageVideos.isLast());
    }

    @Override
    public PageResponse getVideosInAPlayList(String playlistId, PageableDto pageable){
        Integer pN = pageable.getPageNumber(), pS = pageable.getPageSize();
        Sort sort;
        if(pageable.getSortDir().equalsIgnoreCase("asc")) {
            sort = Sort.by(pageable.getSortBy()).ascending();
        }
        else {
            sort = Sort.by(pageable.getSortBy()).descending();
        }
        Pageable p = PageRequest.of(pN, pS, sort);
        PlayList playList = this.pLayListRepo.findById(playlistId).orElseThrow(()-> new RuntimeException("Playlist not found"));
        List<Video> videoList = playList.getPlaylistItems();
        int start = (int)p.getOffset();
        int end = Math.min((start + p.getPageSize()), videoList.size());
        List<Video> pagedVideos = videoList.subList(start, end);
        List<VideoListDto> mappedVideos = pagedVideos.stream().map((video) -> this.modelMapper.map(video, VideoListDto.class)).toList();
        return new PageResponse(new ArrayList<>(mappedVideos), pN, pS, (int)Math.ceil((double)videoList.size() / (double)pS), videoList.size(), pN == (int)Math.ceil((double)videoList.size() / (double)pS) - 1);
    }
    @Override
    public PageResponse getAllPlatList(PageableDto pageable) {
        Page<PlayList> pagePlayList = this.pLayListRepo.findAll(createPaginationRequest(pageable));
        List<PlayListDto> playListDtos = pagePlayList.getContent().stream().map((playList) -> this.modelMapper.map(playList, PlayListDto.class)).toList();
        return new PageResponse(new ArrayList<>(playListDtos), pagePlayList.getNumber(), pagePlayList.getSize(), pagePlayList.getTotalPages(), pagePlayList.getTotalElements(), pagePlayList.isLast());
    }
    @Override
    public ResponseEntity<?> getPlaylistById(String playlistId){
        return ResponseEntity.status(OK).body(this.pLayListRepo.findById(playlistId).orElseThrow(()-> new RuntimeException("Playlist not found with the entered playListId: "+ playlistId)));
    }
    @Override
    public ResponseEntity<?> getVideoById(String videoId){
        return ResponseEntity.status(OK).body(this.videoRepository.findById(videoId).orElseThrow(()-> new RuntimeException("Video not found with the entered videoId: "+ videoId)));
    }
    @Override
    public ResponseEntity<?> getChannelInfo(){
        return this.feignClient.getChannelDetails(key, "snippet,contentDetails,statistics", channelId);
    }


    private Pageable createPaginationRequest(PageableDto pageable) {
        Integer pN = pageable.getPageNumber(), pS = pageable.getPageSize();
        Sort sort;
        if(pageable.getSortDir().equalsIgnoreCase("asc")) {
            sort = Sort.by(pageable.getSortBy()).ascending();
        }
        else {
            sort = Sort.by(pageable.getSortBy()).descending();
        }
        return PageRequest.of(pN, pS, sort);
    }
}
