package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.model.Category;
import com.yers.pandev_tech_task.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public String getCategoryTree() {
        List<Category> roots = categoryRepository.findByParentIsNull();
        if (roots.isEmpty()) {
            return "❌ В базе данных нет категорий!";
        }

        StringBuilder tree = new StringBuilder("📂 Дерево категорий:\n");
        for (Category root : roots) {
            buildTree(root, tree, 0);
        }
        return tree.toString();
    }

    private void buildTree(Category category, StringBuilder tree, int level) {
        tree.append("  ".repeat(level))
                .append(" ➥ ")
                .append(category.getName())
                .append("\n");

        for (Category child : category.getChildren()) {
            buildTree(child, tree, level + 1);
        }
    }

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

        return "✅ Дерево категорий успешно добавлено!";
    }


    @Transactional
    public String removeCategory(String name) {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);

        if (categoryOptional.isEmpty()) {
            return "❌ Ошибка: категория '" + name + "' не найдена!";
        }

        Category category = categoryOptional.get();
        categoryRepository.delete(category);

        return "✅ Категория '" + name + "' и все её подкатегории удалены!";

    }


        @Transactional
        public String addCategory(String name, String parentName) {
            if (categoryRepository.findByName(name).isPresent()) {
                return "❌ Ошибка: категория '" + name + "' уже существует!";
            }

            Category parent = null;
            if (parentName != null) {
                parent = categoryRepository.findByName(parentName)
                        .orElse(null);
                if (parent == null) {
                    return "❌ Ошибка: родительская категория '" + parentName + "' не найдена!";
                }
            }

            Category category = new Category();
            category.setName(name);
            category.setParent(parent);
            categoryRepository.save(category);

            return (parent == null) ?
                    "✅ Добавлена корневая категория: " + name :
                    "✅ Добавлен дочерний элемент '" + name + "' в '" + parentName + "'";
        }
    }
