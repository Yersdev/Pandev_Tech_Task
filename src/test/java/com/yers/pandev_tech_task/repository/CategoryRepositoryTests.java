package com.yers.pandev_tech_task.repository;

import com.yers.pandev_tech_task.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category rootCategory;
    private Category subCategory;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        rootCategory = Category.builder()
                .name("Movies")
                .build();

        subCategory = Category.builder()
                .name("Action")
                .parent(rootCategory)
                .build();

        categoryRepository.save(rootCategory);
        categoryRepository.save(subCategory);
    }

    @Test
    @DisplayName("Поиск категории по названию")
    void testFindByName() {
        Optional<Category> foundCategory = categoryRepository.findByName("Movies");
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("Movies");
    }

    @Test
    @DisplayName("Поиск несуществующей категории")
    void testFindByName_NotFound() {
        Optional<Category> foundCategory = categoryRepository.findByName("Comedy");
        assertThat(foundCategory).isEmpty();
    }

    @Test
    @DisplayName("Поиск всех корневых категорий")
    void testFindByParentIsNull() {
        List<Category> rootCategories = categoryRepository.findByParentIsNull();
        assertThat(rootCategories).hasSize(1);
        assertThat(rootCategories.get(0).getName()).isEqualTo("Movies");
    }

    @Test
    @DisplayName("Поиск категории по названию и родительской категории")
    void testFindByNameAndParent() {
        Optional<Category> foundCategory = categoryRepository.findByNameAndParent("Action", rootCategory);
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getParent()).isEqualTo(rootCategory);
    }

    @Test
    @DisplayName("Проверка существования категории по названию")
    void testExistsByName() {
        boolean exists = categoryRepository.existsByName("Movies");
        assertThat(exists).isTrue();

        boolean notExists = categoryRepository.existsByName("Comedy");
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Сохранение категории с дублирующим названием")
    void testSaveDuplicateCategoryName() {
        Category duplicateCategory = Category.builder()
                .name("Movies") // Дублируем существующее имя
                .build();

        assertThatThrownBy(() -> categoryRepository.saveAndFlush(duplicateCategory))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
