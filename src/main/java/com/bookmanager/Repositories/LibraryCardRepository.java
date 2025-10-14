package com.bookmanager.Repositories;

import com.bookmanager.Models.LibraryCard;
import com.bookmanager.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {
    Optional<LibraryCard> findByUserIdAndBookId(Long userId, Long bookId);

    Collection<LibraryCard> findByUser(User user);


    boolean existsByUserAndStatusIn(User user, List<String> statuses);

}
