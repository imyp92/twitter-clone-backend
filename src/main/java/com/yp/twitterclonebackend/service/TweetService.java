package com.yp.twitterclonebackend.service;

import com.yp.twitterclonebackend.dto.SessionUser;
import com.yp.twitterclonebackend.dto.TweetRequestDto;
import com.yp.twitterclonebackend.dto.TweetResponseDto;
import com.yp.twitterclonebackend.entity.Tweet;
import com.yp.twitterclonebackend.entity.User;
import com.yp.twitterclonebackend.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public Slice<Tweet> findTweetsSlice(Pageable pageable, Long userId) {
        Slice<Tweet> result;
        if (userId != null) {
            User user = em.getReference(User.class, userId);
            result = tweetRepository.findByUser(pageable, user);
        } else {
            result = tweetRepository.findAll(pageable);
        }
        return result;
    }

    @Transactional
    public TweetResponseDto saveTweet(TweetRequestDto dto, SessionUser sessionUser) {
        User createdBy = em.getReference(User.class, sessionUser.getUserId());
        Long id = tweetRepository.save(new Tweet(dto.getText(), dto.getAttachment(), createdBy)).getId();
        return new TweetResponseDto(id, dto.getText(), dto.getAttachment(), createdBy.getUserId());
    }

    @Transactional
    public void deleteTweet(Long id, SessionUser sessionUser) {
        User user = em.getReference(User.class, sessionUser.getUserId());
        Long count = tweetRepository.deleteByIdAndUser(id, user);
        if (count == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public TweetResponseDto updateTweetText(Long id, String text, SessionUser user) {
        Optional<Tweet> result = tweetRepository.findById(id);
        Tweet tweet = result.orElseThrow(IllegalArgumentException::new);
        if (tweet.getUser().getUserId() != user.getUserId()) {
            throw new IllegalArgumentException();
        }
        tweet.updateText(text);
        return new TweetResponseDto(tweet);
    }
}
