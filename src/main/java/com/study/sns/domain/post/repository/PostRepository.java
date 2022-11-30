package com.study.sns.domain.post.repository;

import com.study.sns.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
