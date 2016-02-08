package ua.cooperok.etsy.view;

public interface IListingView {

    /**
     * Notifying about changing state of listing, it's added to saved list or it's removed from it
     *
     * @param saved state, true - if listing was saved, false - if listing was removed
     */
    void onListingSavedStateChanged(boolean saved);

}