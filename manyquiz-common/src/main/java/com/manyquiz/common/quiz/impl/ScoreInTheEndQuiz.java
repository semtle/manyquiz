package com.manyquiz.common.quiz.impl;

import com.manyquiz.common.quiz.model.IQuestion;
import com.manyquiz.common.quiz.model.IQuestionControl;

import java.util.List;

public class ScoreInTheEndQuiz extends QuizControlBase {

    public ScoreInTheEndQuiz(List<IQuestion> questions) {
        super(questions);
    }

    @Override
    public IQuestionControl createQuestionControl(IQuestion question) {
        return new ScoreInTheEndQuestion(question);
    }

    @Override
    public boolean readyToEnd() {
        for (IQuestionControl question : questionControls) {
            if (!question.canNavigateForward()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void end() {
        for (IQuestionControl question : questionControls) {
            question.close();
        }
    }
}
