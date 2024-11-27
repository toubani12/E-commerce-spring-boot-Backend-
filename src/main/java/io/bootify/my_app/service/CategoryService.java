package io.bootify.my_app.service;

import io.bootify.my_app.domain.Category;
import io.bootify.my_app.domain.Product;
import io.bootify.my_app.model.CategoryDTO;
import io.bootify.my_app.repos.CategoryRepository;
import io.bootify.my_app.repos.ProductRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(final CategoryRepository categoryRepository,
            final ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("categoryId"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getCategoryId();
    }

    public void update(final Integer categoryId, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setCategoryDescription(category.getCategoryDescription());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        return category;
    }

    public ReferencedWarning getReferencedWarning(final Integer categoryId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);
        final Product categoryProduct = productRepository.findFirstByCategory(category);
        if (categoryProduct != null) {
            referencedWarning.setKey("category.product.category.referenced");
            referencedWarning.addParam(categoryProduct.getProductId());
            return referencedWarning;
        }
        return null;
    }

}
