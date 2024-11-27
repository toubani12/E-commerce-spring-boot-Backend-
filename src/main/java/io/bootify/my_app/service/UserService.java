package io.bootify.my_app.service;

import io.bootify.my_app.domain.Order;
import io.bootify.my_app.domain.Review;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.model.UserDTO;
import io.bootify.my_app.repos.OrderRepository;
import io.bootify.my_app.repos.ReviewRepository;
import io.bootify.my_app.repos.UserRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public UserService(final UserRepository userRepository, final OrderRepository orderRepository,
            final ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer userId) {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getUserId();
    }

    public void update(final Integer userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer userId) {
        userRepository.deleteById(userId);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setCreatedAt(userDTO.getCreatedAt());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Integer userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final Order userOrder = orderRepository.findFirstByUser(user);
        if (userOrder != null) {
            referencedWarning.setKey("user.order.user.referenced");
            referencedWarning.addParam(userOrder.getOrderId());
            return referencedWarning;
        }
        final Review userReview = reviewRepository.findFirstByUser(user);
        if (userReview != null) {
            referencedWarning.setKey("user.review.user.referenced");
            referencedWarning.addParam(userReview.getReviewId());
            return referencedWarning;
        }
        return null;
    }

}
