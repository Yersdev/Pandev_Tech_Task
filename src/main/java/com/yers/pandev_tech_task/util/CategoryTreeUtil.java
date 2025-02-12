package com.yers.pandev_tech_task.util;

import com.yers.pandev_tech_task.model.Category;

/**
 * Утилитный класс для работы с деревом категорий.
 */
public class CategoryTreeUtil {

    /**
     * Рекурсивно строит строковое представление дерева категорий.
     *
     * @param category Категория.
     * @param tree     Буфер для построения дерева.
     * @param level    Уровень вложенности категории.
     */
    public static void buildTree(Category category, StringBuilder tree, int level) {
        if (level == 0) {
            tree.append("➡\uFE0F")
                    .append(category.getName())
                    .append("\n\n");
        } else {
            tree.append("  ".repeat(level))
                    .append(" ↘ ")
                    .append(category.getName())
                    .append("\n");
        }
        for (Category child : category.getChildren()) {
            buildTree(child, tree, level + 1);
        }
    }
}
