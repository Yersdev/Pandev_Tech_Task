package com.yers.pandev_tech_task;

import com.yers.pandev_tech_task.repository.CategoryRepository;
import com.yers.pandev_tech_task.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testAddCategory() {
        categoryService.addCategory("Test", null);
        assertTrue(categoryRepository.findByName("Test").isPresent());
    }
}
