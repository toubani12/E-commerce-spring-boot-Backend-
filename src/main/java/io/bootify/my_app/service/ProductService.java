package io.bootify.my_app.service;

import io.bootify.my_app.domain.Category;
import io.bootify.my_app.domain.OrderDetail;
import io.bootify.my_app.domain.Product;
import io.bootify.my_app.domain.Review;
import io.bootify.my_app.model.ProductDTO;
import io.bootify.my_app.repos.CategoryRepository;
import io.bootify.my_app.repos.OrderDetailRepository;
import io.bootify.my_app.repos.ProductRepository;
import io.bootify.my_app.repos.ReviewRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(final ProductRepository productRepository,
            final CategoryRepository categoryRepository,
            final OrderDetailRepository orderDetailRepository,
            final ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("productId"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Integer productId) {
        return productRepository.findById(productId)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getProductId();
    }

    public void update(final Integer productId, final ProductDTO productDTO) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Integer productId) {
        productRepository.deleteById(productId);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        productDTO.setCategory(product.getCategory() == null ? null : product.getCategory().getCategoryId());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setUpdatedAt(productDTO.getUpdatedAt());
        final Category category = productDTO.getCategory() == null ? null : categoryRepository.findById(productDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        product.setCategory(category);
        return product;
    }

    public ReferencedWarning getReferencedWarning(final Integer productId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        final OrderDetail productOrderDetail = orderDetailRepository.findFirstByProduct(product);
        if (productOrderDetail != null) {
            referencedWarning.setKey("product.orderDetail.product.referenced");
            referencedWarning.addParam(productOrderDetail.getOrderDetailId());
            return referencedWarning;
        }
        final Review productReview = reviewRepository.findFirstByProduct(product);
        if (productReview != null) {
            referencedWarning.setKey("product.review.product.referenced");
            referencedWarning.addParam(productReview.getReviewId());
            return referencedWarning;
        }
        return null;
    }

}
