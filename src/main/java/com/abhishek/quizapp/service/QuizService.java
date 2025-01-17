package com.abhishek.quizapp.service;

import com.abhishek.quizapp.dao.QuestionDao;
import com.abhishek.quizapp.dao.QuizDao;
import com.abhishek.quizapp.model.Question;
import com.abhishek.quizapp.model.QuestionWrapper;
import com.abhishek.quizapp.model.Quiz;
import com.abhishek.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numOfQ, String title) {

        List<Question> questions= questionDao.findRandomQuestionsByCategory(category,numOfQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);   //Option takes care of data might come or not

        //Now we need to convert those questions into question wrapper
        List<Question> questionFromDb = quiz.get().getQuestions();;

        List<QuestionWrapper> quesetionForUsers = new ArrayList<>();

        for(Question q: questionFromDb){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            quesetionForUsers.add(qw);
        }

        return new ResponseEntity<>(quesetionForUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculate(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        //Its just comparing the value in the response and the question list
        int right=0;
        int i=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
