package com.manyquiz.quiz;

import java.util.List;

public interface IQuestion {

    String getCategory();

    String getText();

    String getExplanation();

    List<String> getChoices();

    String getCorrectAnswer();

    String getSelectedAnswer();

    void setSelectedAnswer(String answer);

    boolean wasAnswered();

    boolean wasCorrectlyAnswered();

}