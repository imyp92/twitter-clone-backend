package com.yp.twitterclonebackend.controller;

import com.yp.twitterclonebackend.annotation.LoginUser;
import com.yp.twitterclonebackend.dto.SessionUser;
import com.yp.twitterclonebackend.dto.TweetRequestDto;
import com.yp.twitterclonebackend.dto.TweetResponseDto;
import com.yp.twitterclonebackend.dto.TweetUpdateDto;
import com.yp.twitterclonebackend.entity.Tweet;
import com.yp.twitterclonebackend.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private final TweetService tweetService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public Slice<TweetResponseDto> getTweets(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size, @RequestParam(required = false) Long userId) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Slice<Tweet> slice = tweetService.findTweetsSlice(pageRequest, userId);

        return slice.map(TweetResponseDto::new);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public TweetResponseDto sendTweet(@Valid @RequestBody TweetRequestDto dto, @LoginUser SessionUser user) {
        return tweetService.saveTweet(dto, user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTweet(@PathVariable Long id, @LoginUser SessionUser user) {
        tweetService.deleteTweet(id, user);
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public TweetResponseDto updateTweet(@PathVariable Long id, @Valid @RequestBody TweetUpdateDto dto, @LoginUser SessionUser user) {
        return tweetService.updateTweetText(id, dto.getText(), user);
    }
}
