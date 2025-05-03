package com.example.ks.tonerMonth.repository;

import com.example.ks.tonerMonth.domain.TonerMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TonerMonthRepository extends JpaRepository<TonerMonth, Integer> {
        @Query("SELECT tm FROM TonerMonth tm WHERE FUNCTION('YEAR', tm.tonerMonthDate) = :year AND FUNCTION('MONTH', tm.tonerMonthDate) = :month")
        List<TonerMonth> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

        @Query("SELECT tm FROM TonerMonth tm WHERE FUNCTION('YEAR', tm.tonerMonthDate) = :year AND FUNCTION('MONTH', tm.tonerMonthDate) = :month AND tm.tonerName = :tonerName")
        TonerMonth findByYearAndMonthAndTonerName(@Param("year") int year, @Param("month") int month, @Param("tonerName") String tonerName);


}
