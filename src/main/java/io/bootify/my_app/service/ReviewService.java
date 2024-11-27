package io.bootify.my_app.service;

import io.bootify.my_app.domain.Product;
import io.bootify.my_app.domain.Review;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.model.ReviewDTO;
import io.bootify.my_app.repos.ProductRepository;
import io.bootify.my_app.repos.ReviewRepository;
import io.bootify.my_app.repos.UserRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewService(final ReviewRepository reviewRepository,
            final ProductRepository productRepository, final UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> findAll() {
        final List<Review> reviews = reviewRepository.findAll(Sort.by("reviewId"));
        return reviews.stream()
                .map(review -> mapToDTO(review, new ReviewDTO()))
                .toList();
    }

    public ReviewDTO get(final Integer reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> mapToDTO(review, new ReviewDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ReviewDTO reviewDTO) {
        final Review review = new Review();
        mapToEntity(reviewDTO, review);
        return reviewRepository.save(review).getReviewId();
    }

    public void update(final Integer reviewId, final ReviewDTO reviewDTO) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reviewDTO, review);
        reviewRepository.save(review);
    }

    public void delete(final Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO mapToDTO(final Review review, final ReviewDTO reviewDTO) {
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setProduct(review.getProduct() == null ? null : review.getProduct().getProductId());
        reviewDTO.setUser(review.getUser() == null ? null : review.getUser().getUserId());
        return reviewDTO;
    }

    private Review mapToEntity(final ReviewDTO reviewDTO, final Review review) {
        review.setRating(reviewDTO.getRating());
        review.setReviewText(reviewDTO.getReviewText());
        review.setCreatedAt(reviewDTO.getCreatedAt());
        final Product product = reviewDTO.getProduct() == null ? null : productRepository.findById(reviewDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        review.setProduct(product);
        final User user = reviewDTO.getUser() == null ? null : userRepository.findById(reviewDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        review.setUser(user);
        return review;
    }

}
