package com.bookmanager.Services;

import com.bookmanager.DTOs.Response.CategoryResponse;
import com.bookmanager.DTOs.Response.FilterBookTitleResponse;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Models.Category;
import com.bookmanager.Repositories.BookTitleRepository;
import com.bookmanager.Repositories.CategoryReposirory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryReposirory categoryReposirory;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    public List<FilterBookTitleResponse> filterBooksByClassify(Map<Long, Integer> classifyMap) {
        List<BookTitle> allBooks = bookTitleRepository.findAll();

        return allBooks.stream()
                .filter(book -> {
                    Set<Long> bookCateIds = book.getCategories()
                            .stream()
                            .map(Category::getId)
                            .collect(Collectors.toSet());

                    Set<Long> mustHave = classifyMap.entrySet().stream()
                            .filter(e -> e.getValue() == 1)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet());

                    Set<Long> mustNotHave = classifyMap.entrySet().stream()
                            .filter(e -> e.getValue() == 0)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet());

                    boolean hasAllRequired = bookCateIds.containsAll(mustHave);
                    boolean hasExcluded = bookCateIds.stream().anyMatch(mustNotHave::contains);

                    return hasAllRequired && !hasExcluded;
                })
                .map(book -> new FilterBookTitleResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getPublicationDate(),
                        book.getCompensationCost(),
                        book.getStatus(),
                        book.getCategories().stream()
                                .map(c -> new CategoryResponse(c.getId(), c.getCategoryName()))
                                .collect(Collectors.toSet())
                ))
                .toList();
    }

}
