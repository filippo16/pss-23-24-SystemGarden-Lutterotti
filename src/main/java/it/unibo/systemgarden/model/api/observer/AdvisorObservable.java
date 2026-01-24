package it.unibo.systemgarden.model.api.observer;

import it.unibo.systemgarden.model.utils.IrrigationAdvice;

/**
 * Observable Interface for irrigation advisors.
*/
public interface AdvisorObservable {

    /**
     * Adds an observer to the list of observers to receive updates.
     * @param observer the observer to be added {@link AdvisorObserver}
    */
    void addAdvisorObserver( AdvisorObserver observer );

    /**
     * Removes an observer from the list of observers to stop receiving updates.
     * @param observer the observer to be removed {@link AdvisorObserver}
    */
    void removeAdvisorObserver( AdvisorObserver observer );

    /**
     * Notifies all observers about an advice related to a specific area.
     * @param areaId the ID of the area related to the advice
     * @param adviceTitle the title of the advice to show {@link IrrigationAdvice}
    */
    void notifyAdvisorObservers( String areaId, String adviceTitle );
}
