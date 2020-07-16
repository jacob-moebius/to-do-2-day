package edu.miracosta.cs134.jmoebius.model;

public class Task {

    private int mId;
    private String mDescription;
    private boolean mIsDone;

    public Task(int id, String description, boolean isDone) {
        this.mId = id;
        this.mDescription = description;
        this.mIsDone = isDone;
    }

    public Task(String description, boolean isDone) {
        this(-1, description, isDone);
    }

    public Task(String description) {
        this(-1, description, false);
    }

    public String getDescription() {
        return mDescription;
    }

    public int getId() {
        return mId;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDescripion(String description) {
        this.mDescription = description;
    }

    public void setIsDone(boolean idDone) {
        this.mIsDone = idDone;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mDescripion='" + mDescription + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
