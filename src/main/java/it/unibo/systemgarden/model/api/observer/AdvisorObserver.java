package it.unibo.systemgarden.model.api.observer;

import it.unibo.systemgarden.model.utils.IrrigationAdvice;

public interface AdvisorObserver {

    /**
     * Callback Method called by an AdvisorObservable.
     *
     * @param areaId the ID of the area related to the advice
     * @param adviceTitle the title of the advice to show {@link IrrigationAdvice}
    */
    void onAdviceReceived( String areaId, String adviceTitle );
}
