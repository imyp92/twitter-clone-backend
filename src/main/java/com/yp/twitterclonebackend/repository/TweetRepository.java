package com.yp.twitterclonebackend.repository;

import com.yp.twitterclonebackend.entity.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    @EntityGraph(attributePaths = "user")
    Slice<Tweet> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Slice<Tweet> findById(Pageable pageable, Long userId);

}
