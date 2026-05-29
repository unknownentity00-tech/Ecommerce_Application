package com.EcomFashion.Ecommorse.Repositories.Review;

import com.EcomFashion.Ecommorse.Entity.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository
        extends JpaRepository<Review, Long> {
}
