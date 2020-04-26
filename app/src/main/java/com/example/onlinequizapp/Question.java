package com.example.onlinequizapp;

public class Question {

    private String Question, ReponseA, ReponseB, ReponseC, CorrectReponse, CategoryId, IsImageQuestion;

    public Question(){

    }

    public Question(String question, String reponseA, String reponseB, String reponseC, String correctReponse, String categoryId, String isImageQuestion) {
        Question= question;
        ReponseA= reponseA;
        ReponseB= reponseB;
        ReponseC= reponseC;
        CorrectReponse= correctReponse;
        CategoryId=categoryId;
        IsImageQuestion= isImageQuestion;


    }

    public String getReponseA() {
        return ReponseA;
    }

    public void setReponseA(String reponseA) {
        ReponseA = reponseA;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }

    public String getCorrectReponse() {
        return CorrectReponse;
    }

    public void setCorrectReponse(String correctReponse) {
        CorrectReponse = correctReponse;
    }

    public String getReponseC() {
        return ReponseC;
    }

    public void setReponseC(String reponseC) {
        ReponseC = reponseC;
    }

    public String getReponseB() {
        return ReponseB;
    }

    public void setReponseB(String reponseB) {
        ReponseB = reponseB;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}