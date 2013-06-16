package com.manyquiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements IQuestion {

	private final String category;
	private final String text;
	private final String explanation;

	private final List<String> choices;
	private final String correctAnswer;

	private String selectedAnswer;
	private boolean correctlyAnswered;

	public Question(String category, String text, String explanation,
			String answer, List<String> decoyChoices) {
		this.category = category;
		this.text = text;
		this.explanation = explanation;
		this.correctAnswer = answer;
		this.correctlyAnswered = false;
		
		this.choices = new ArrayList<String>(decoyChoices);
		this.choices.add(answer);
		Collections.shuffle(choices);
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getExplanation() {
		return explanation;
	}

	@Override
	public List<String> getChoices() {
		return choices;
	}

	@Override
	public String getCorrectAnswer() {
		return correctAnswer;
	}

	@Override
	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	@Override
	public void setSelectedAnswer(String answer) {
		this.selectedAnswer = answer;
		correctlyAnswered = answer.equals(correctAnswer);
	}

	@Override
	public boolean wasCorrectlyAnswered() {
		return correctlyAnswered;
	}

}
