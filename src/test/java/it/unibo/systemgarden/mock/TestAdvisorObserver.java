package it.unibo.systemgarden.mock;

import it.unibo.systemgarden.model.api.observer.AdvisorObserver;

public class TestAdvisorObserver implements AdvisorObserver {

    private boolean notified = false;
    private String lastAreaId;
    private String lastAdviceTitle;
    private int notificationCount = 0;

    @Override
    public void onAdviceReceived(String areaId, String adviceTitle) {
        this.notified = true;
        this.lastAreaId = areaId;
        this.lastAdviceTitle = adviceTitle;
        this.notificationCount++;
    }

    public boolean wasNotified() {
        return notified;
    }

    public String getLastAreaId() {
        return lastAreaId;
    }

    public String getLastAdviceTitle() {
        return lastAdviceTitle;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void reset() {
        this.notified = false;
        this.lastAreaId = null;
        this.lastAdviceTitle = null;
        this.notificationCount = 0;
    }
}
