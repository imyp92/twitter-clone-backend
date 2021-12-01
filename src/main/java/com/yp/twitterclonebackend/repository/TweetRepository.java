package com.yp.twitterclonebackend.repository;

import com.yp.twitterclonebackend.entity.Tweet;
import com.yp.twitterclonebackend.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    @EntityGraph(attributePaths = {"user", "attachment"})
    Slice<Tweet> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "attachment"})
    Slice<Tweet> findByUser(Pageable pageable, User user);

    @EntityGraph(attributePaths = {"user", "attachment"})
    Optional<Tweet> findById(Long id);

    Long deleteByIdAndUser(Long id, User user);

    Slice<Tweet> findByUser(Pageable pageable, User user);
}
