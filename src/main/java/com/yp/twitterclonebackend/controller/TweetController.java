package com.yp.twitterclonebackend.controller;

import com.yp.twitterclonebackend.annotation.LoginUser;
import com.yp.twitterclonebackend.dto.SessionUser;
import com.yp.twitterclonebackend.dto.TweetDto;
import com.yp.twitterclonebackend.entity.Tweet;
import com.yp.twitterclonebackend.repository.TweetRepository;
import com.yp.twitterclonebackend.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TweetController {

    private final TweetService tweetService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/tweets")
    public Slice<TweetDto> getTweets(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size, @RequestParam(required = false) Long userId) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Slice<Tweet> slice = tweetService.findTweetsSlice(pageRequest, userId);

        return slice.map(TweetDto::new);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/tweets")
    public ResponseEntity<Long> sendTweet(@RequestBody TweetDto dto, @LoginUser SessionUser user) {
        return ResponseEntity.ok(tweetService.saveTweet(dto, user));
    }
}
