package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.model.Category;
import com.yers.pandev_tech_task.repository.CategoryRepository;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category rootCategory;
    private Category childCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        rootCategory = new Category(1L, "Movies", null, List.of());
        childCategory = new Category(2L, "Action", rootCategory, List.of());

        when(categoryRepository.findByParentIsNull()).thenReturn(List.of(rootCategory));
        when(categoryRepository.findByName("Movies")).thenReturn(Optional.of(rootCategory));
        when(categoryRepository.findByName("Action")).thenReturn(Optional.of(childCategory));
        when(categoryRepository.findByNameAndParent("Action", rootCategory)).thenReturn(Optional.of(childCategory));
        when(categoryRepository.findAll()).thenReturn(List.of(rootCategory, childCategory));
    }

    @Test
    @DisplayName("Получение дерева категорий")
    void testGetCategoryTree() {
        String result = categoryService.getCategoryTree();
        assertThat(result).contains("Дерево категорий");
    }

    @Test
    @DisplayName("Добавление дерева категорий")
    void testAddTree() {
        when(categoryRepository.findByNameAndParent("Movies", null)).thenReturn(Optional.of(rootCategory));
        when(categoryRepository.findByNameAndParent("Action", rootCategory)).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(childCategory);

        String result = categoryService.addTree("Movies -> Action");

        assertThat(result).isEqualTo(TextsHelperUtil.categoriesSuccessfullyAddedInDb());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Удаление существующей категории")
    void testRemoveCategory() {
        when(categoryRepository.findByName("Movies")).thenReturn(Optional.of(rootCategory));

        String result = categoryService.removeCategory("Movies");

        assertThat(result).isEqualTo(TextsHelperUtil.categoriesSuccessfullyRemovedFromDb("Movies"));
        verify(categoryRepository, times(1)).delete(rootCategory);
    }

    @Test
    @DisplayName("Удаление несуществующей категории")
    void testRemoveCategory_NotFound() {
        when(categoryRepository.findByName("Comedy")).thenReturn(Optional.empty());

        String result = categoryService.removeCategory("Comedy");

        assertThat(result).isEqualTo(TextsHelperUtil.categoriesNotFound("Comedy"));
        verify(categoryRepository, never()).delete(any(Category.class));
    }

    @Test
    @DisplayName("Добавление категории в корень")
    void testAddCategoryToRoot() {
        when(categoryRepository.findByName("Comedy")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category(3L, "Comedy", null, List.of()));

        String result = categoryService.addCategory("Comedy", null);

        assertThat(result).isEqualTo(TextsHelperUtil.categoriesAddedInRootOfCategory("Comedy"));
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Добавление дочерней категории")
    void testAddCategoryWithParent() {
        when(categoryRepository.findByName("Movies")).thenReturn(Optional.of(rootCategory));
        when(categoryRepository.findByName("Horror")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category(4L, "Horror", rootCategory, List.of()));

        String result = categoryService.addCategory("Horror", "Movies");

        assertThat(result).isEqualTo(TextsHelperUtil.categoriesAddedInRootOfCategoryWithChildElement("Horror", "Movies"));
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Получение всех категорий")
    void testGetAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        assertThat(categories).hasSize(2);
        assertThat(categories).contains(rootCategory, childCategory);
    }
}
