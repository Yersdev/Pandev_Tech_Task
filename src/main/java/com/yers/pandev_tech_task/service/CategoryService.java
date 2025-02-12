package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.model.Category;
import com.yers.pandev_tech_task.repository.CategoryRepository;
import com.yers.pandev_tech_task.util.CategoryTreeUtil;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления категориями и их иерархией.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Получает дерево категорий в виде строки.
     *
     * @return Структура дерева категорий или сообщение, если категории отсутствуют.
     */
    @Transactional
    public String getCategoryTree() {
        List<Category> roots = categoryRepository.findByParentIsNull();
        if (roots.isEmpty()) {
            return TextsHelperUtil.noCategoriesInDb();
        }

        StringBuilder tree = new StringBuilder("\uD83C\uDF33 Дерево категорий:\n\n");
        for (Category root : roots) {
            CategoryTreeUtil.buildTree(root, tree, 0);
        }
        return tree.toString();
    }

    /**
     * Добавляет дерево категорий, создавая вложенные элементы, если они отсутствуют.
     *
     * @param categoryPath Путь категорий в формате "Родитель -> Дочерняя1 -> Дочерняя2".
     * @return Сообщение об успешном добавлении.
     */
    @Transactional
    public String addTree(String categoryPath) {
        String[] categories = categoryPath.split("->");
        Category parent = null;

        for (String categoryName : categories) {
            categoryName = categoryName.trim();

            Optional<Category> existingCategory = categoryRepository.findByNameAndParent(categoryName, parent);
            if (existingCategory.isPresent()) {
                parent = existingCategory.get();
            } else {
                Category newCategory = new Category();
                newCategory.setName(categoryName);
                newCategory.setParent(parent);
                parent = categoryRepository.save(newCategory);
            }
        }

        return TextsHelperUtil.categoriesSuccessfullyAddedInDb();
    }

    /**
     * Удаляет категорию и все её подкатегории.
     *
     * @param name Название категории.
     * @return Сообщение о результате удаления.
     */
    @Transactional
    public String removeCategory(String name) {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);

        if (categoryOptional.isEmpty()) {
            return TextsHelperUtil.categoriesNotFound(name);
        }

        Category category = categoryOptional.get();
        categoryRepository.delete(category);

        return TextsHelperUtil.categoriesSuccessfullyRemovedFromDb(name);
    }

    /**
     * Добавляет новую категорию, либо корневую, либо как дочернюю для указанного родителя.
     *
     * @param name       Название новой категории.
     * @param parentName Название родительской категории (может быть null для корневой).
     * @return Сообщение о результате операции.
     */
    @Transactional
    public String addCategory(String name, String parentName) {
        if (categoryRepository.findByName(name).isPresent()) {
            return TextsHelperUtil.categoriesParentNameAlreadyExist(name);
        }

        Category parent = null;
        if (parentName != null) {
            parent = categoryRepository.findByName(parentName)
                    .orElse(null);
            if (parent == null) {
                return TextsHelperUtil.categoriesWithParentNameNotFound(parentName);
            }
        }

        Category category = new Category();
        category.setName(name);
        category.setParent(parent);
        categoryRepository.save(category);

        return (parent == null) ?
                 TextsHelperUtil.categoriesAddedInRootOfCategory(name) : TextsHelperUtil.categoriesAddedInRootOfCategoryWithChildElement(name , parentName);

    }

    /**
     * Получает список всех категорий из базы данных.
     *
     * @return Список всех категорий.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
