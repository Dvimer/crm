package com.crm.car.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetMediaCommentsRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUserFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramComment;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetMediaCommentsResult;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstagrammService {
    private Instagram4j instagram;

    @PostConstruct
    void setUp() throws IOException {
        instagram = Instagram4j.builder().username("dvimerjoke@gmail.com").password("wodqur-ryston-1Coztu").build();
        instagram.setup();
        instagram.login();
    }

    public List<String> servie(String accName, int postNumber) throws IOException {
        long plDetkiKonfetki91 = instagram.sendRequest(new InstagramSearchUsernameRequest(accName)).getUser().getPk();
        InstagramFeedResult instagramFeedResult = instagram.sendRequest(new InstagramUserFeedRequest(plDetkiKonfetki91));
        return instagramFeedResult.getItems().stream().map(InstagramFeedItem::getCode).collect(Collectors.toList());
    }


    public List<String> getTextByCode(String accName, String code) throws IOException {

        long plDetkiKonfetki91 = instagram.sendRequest(new InstagramSearchUsernameRequest(accName)).getUser().getPk();
        InstagramFeedResult instagramFeedResult = instagram.sendRequest(new InstagramUserFeedRequest(plDetkiKonfetki91));
        String firstPostId = instagramFeedResult.getItems().stream().filter(p -> p.getCode().equals(code)).findFirst().get().getId();


        List<String> textFromPost = getTextFromPost(instagram, firstPostId, null);

        System.out.println("Перед уникальностью=" + textFromPost.size());
        return textFromPost.stream().map(String::toLowerCase).distinct().sorted().collect(Collectors.toList());

    }

    private static List<String> getTextFromPost(Instagram4j instagram, String firstPostId, String maxId) throws
            IOException {
        List<String> collect;
        String newMaxId = getMaxId(maxId);
        InstagramGetMediaCommentsResult instagramGetMediaCommentsResult = instagram.sendRequest(new InstagramGetMediaCommentsRequest(firstPostId, newMaxId));
        //todo если закрыт - не отображать
        System.out.println("Получено " + instagramGetMediaCommentsResult.getComments().size() + " сообщений");
        collect = getCommentText(instagramGetMediaCommentsResult);

        if (instagramGetMediaCommentsResult.getNext_max_id() != null) {
            List<String> textFromPost = getTextFromPost(instagram, firstPostId, instagramGetMediaCommentsResult.getNext_max_id());
            collect.addAll(textFromPost);
        }
        return collect;
    }

    private static String getMaxId(String maxId) {
        if (maxId != null) {
            return maxId.replaceAll("\\{", "%7B").replaceAll("\"", "%22").replaceAll(" ", "%20").replaceAll("}", "%7D");
        } else {
            return maxId;
        }
    }

    private static List<String> getCommentText(InstagramGetMediaCommentsResult instagramGetMediaCommentsResult) {
        return instagramGetMediaCommentsResult
                .getComments().stream().map(InstagramComment::getText).collect(Collectors.toList());
    }

}
