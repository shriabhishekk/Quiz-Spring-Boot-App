package com.abhishek.quizapp.controller;


import com.abhishek.quizapp.model.Question;
import com.abhishek.quizapp.model.QuestionWrapper;
import com.abhishek.quizapp.model.Response;
import com.abhishek.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping ("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numOfQ, @RequestParam String title){
        return quizService.createQuiz(category,numOfQ,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);

    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculate(id,responses);
    }

}
