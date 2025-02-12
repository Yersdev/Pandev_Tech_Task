package com.yers.pandev_tech_task.repository;

import com.yers.pandev_tech_task.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Category}.
 * Предоставляет методы для поиска, проверки существования и управления категориями в базе данных.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Поиск категории по названию.
     *
     * @param name название категории
     * @return {@link Optional}, содержащий категорию, если она найдена, иначе пустой {@link Optional}
     */
    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Optional<Category> findByName(String name);

    /**
     * Получение списка всех корневых категорий (категорий без родителя).
     *
     * @return список корневых категорий
     */
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findByParentIsNull();

    /**
     * Поиск категории по названию и родительской категории.
     *
     * @param categoryName название категории
     * @param parent родительская категория
     * @return {@link Optional}, содержащий найденную категорию, если она существует
     */
    @Query("SELECT c FROM Category c WHERE c.name = :categoryName AND c.parent = :parent")
    Optional<Category> findByNameAndParent(String categoryName, Category parent);

    /**
     * Проверяет существование категории с указанным названием.
     *
     * @param categoryName название категории
     * @return true, если категория с таким названием существует, иначе false
     */
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :categoryName")
    boolean existsByName(String categoryName);
}
