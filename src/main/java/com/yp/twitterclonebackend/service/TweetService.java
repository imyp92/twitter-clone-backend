package com.yp.twitterclonebackend.service;

import com.yp.twitterclonebackend.dto.SessionUser;
import com.yp.twitterclonebackend.dto.TweetDto;
import com.yp.twitterclonebackend.entity.Tweet;
import com.yp.twitterclonebackend.entity.User;
import com.yp.twitterclonebackend.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public Slice<Tweet> findTweetsSlice(Pageable pageable, Long userId) {
        Slice<Tweet> result;
        if (userId != null) {
            result = tweetRepository.findById(pageable, userId);
        } else {
            result = tweetRepository.findAll(pageable);
        }
        return result;
    }

    @Transactional
    public Long saveTweet(TweetDto dto, SessionUser sessionUser) {
        if (dto.getCreatedBy() != sessionUser.getUserId()) {
            throw new IllegalArgumentException();
        }
        User createdBy = em.getReference(User.class, sessionUser.getUserId());
        return tweetRepository.save(new Tweet(dto.getText(), dto.getAttachmentUrl(), createdBy)).getId();
    }
}
