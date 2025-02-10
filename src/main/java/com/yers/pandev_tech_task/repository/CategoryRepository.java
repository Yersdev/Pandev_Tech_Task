package com.yers.pandev_tech_task.repository;

import com.yers.pandev_tech_task.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findByParentIsNull();

    Optional<Category> findByNameAndParent(String categoryName, Category parent);

    boolean existsByName(String categoryName);
}

