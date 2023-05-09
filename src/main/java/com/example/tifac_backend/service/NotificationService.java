package com.example.tifac_backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionContentDetails;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final YouTube youTube;

    @Value("${clientId}")
    private String clientId;
    @Value(("${clientSecret}"))
    private String clientSecret;

    private String accessToken;
    private String refreshToken;


    public void subscribe(String channelId, String callbackUrl) throws IOException {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(new NetHttpTransport())
                .setJsonFactory(new JacksonFactory())
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);

        Subscription subscription = new Subscription();
        SubscriptionSnippet snippet = new SubscriptionSnippet();

        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#channel");
        resourceId.setChannelId(channelId);

        snippet.setChannelId(channelId);
        snippet.setResourceId(resourceId);
        subscription.setSnippet(snippet);
        youTube.subscriptions().insert("snippet", subscription).execute();

        SubscriptionContentDetails details = new SubscriptionContentDetails();
        
//        YouTube.Subscriptions.Insert subscriptionInsert = youTube.subscriptions().insert("snippet", new Subscription());
//        ResourceId resourceId = new ResourceId();
//        resourceId.setKind("youtube#channel");
//        resourceId.setChannelId(channelId);
//        SubscriptionSnippet snippet = new SubscriptionSnippet();
//        snippet.setChannelId(channelId);
//        snippet.setResourceId(resourceId);
//        subscriptionInsert.setSnippet(snippet);




    }

}
