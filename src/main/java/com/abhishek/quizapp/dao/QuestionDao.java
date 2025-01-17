package com.abhishek.quizapp.dao;

import com.abhishek.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);


    //This cant be given by SPring 😂😂 , it gives data till a limit.
    //We need to write a native query to fetch the data  (JPQL Query)

    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numOfQ", nativeQuery = true)
//    @Query(value = "SELECT * FROM question q where q.category=:category ORDER By RANDOM() LIMIT:numOfQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQ);
}
