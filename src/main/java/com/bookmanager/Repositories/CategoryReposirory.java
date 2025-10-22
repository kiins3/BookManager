package com.bookmanager.Repositories;

import com.bookmanager.Models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReposirory extends CrudRepository<Category, Long> {
}
