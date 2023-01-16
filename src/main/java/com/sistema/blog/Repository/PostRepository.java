package com.sistema.blog.Repository;

import com.sistema.blog.Models.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
