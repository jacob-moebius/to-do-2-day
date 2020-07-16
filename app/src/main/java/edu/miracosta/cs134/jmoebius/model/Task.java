package edu.miracosta.cs134.jmoebius.model;

public class Task {

    private long mId;
    private String mDescription;
    private boolean mIsDone;

    public Task(long id, String description, boolean isDone) {
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

    public long getId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescripion(String description) {
        this.mDescription = description;
    }

    public void setIsDone(boolean isDone) {
        this.mIsDone = isDone;
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
