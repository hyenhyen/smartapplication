package com.monad.kpu.Model;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.Timer;

/**
 * Created by temp on 2017. 5. 19..
 */

public class ResultScoreModel {
    private String title;
    private float myScore;
    private float difference;
    private String diagnose;
    private String content;

    public String major;
    public String type;
    public String year;

    public ResultScoreModel() {

    }

    public ResultScoreModel(String title, float myScore, float difference, String diagnose, String content) {
        this.title = title;
        this.myScore = myScore;
        this.difference = difference;
        this.diagnose = diagnose;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public float getDifference() {
        return difference;
    }

    public void setDifference(float difference) {
        this.difference = difference;
    }

    public float getMyScore() {
        return myScore;
    }

    public void setMyScore(float myScore) {
        this.myScore = myScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
