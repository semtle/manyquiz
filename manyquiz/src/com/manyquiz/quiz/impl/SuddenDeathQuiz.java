package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.IQuestion;
import com.manyquiz.quiz.model.IQuestionControl;

import java.util.List;

public class SuddenDeathQuiz extends QuizControlBase {

    public SuddenDeathQuiz(List<IQuestion> questions) {
        super(questions);
    }

    @Override
    public IQuestionControl createQuestionControl(IQuestion question) {
        return new SuddenDeathQuestion(this, question);
    }

    @Override
    public boolean isGameOver() {
        for (IQuestionControl question : questionControls) {
            if (question.canChangeAnswer()) {
                return false;
            }
            if (! question.isCorrectlyAnswered()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean canNavigateBack() {
        return false;
    }
}
