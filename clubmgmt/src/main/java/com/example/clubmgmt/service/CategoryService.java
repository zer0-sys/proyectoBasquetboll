package com.example.clubmgmt.service;

import com.example.clubmgmt.dto.CategoryDTO;
import com.example.clubmgmt.entity.Category;
import com.example.clubmgmt.repository.CategoryRepository;
import com.example.clubmgmt.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private EntityDtoConverter converter;

    public List<CategoryDTO> getAllCategories() {
        return converter.convertToCategoryDtoList(categoryRepository.findAll());
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(converter::convertToDto);
    }

    public CategoryDTO createCategory(Category category) {
        return converter.convertToDto(categoryRepository.save(category));
    }

    public CategoryDTO updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            return converter.convertToDto(categoryRepository.save(category));
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    // Método para obtener la entidad Category directamente (para uso interno)
    public Optional<Category> getCategoryEntityById(Long id) {
        return categoryRepository.findById(id);
    }
}