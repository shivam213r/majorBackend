package com.example.demo.repository;

import com.example.demo.model.DailyWorkoutSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyWorkoutSuggestionRepository extends JpaRepository<DailyWorkoutSuggestion, Long> {
    Optional<DailyWorkoutSuggestion> findByUserIdAndSuggestionDate(Long userId, LocalDate suggestionDate);
}