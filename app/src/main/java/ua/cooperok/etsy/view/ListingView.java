package ua.cooperok.etsy.view;

import ua.cooperok.etsy.data.model.Listing;

public interface ListingView {

    /**
     * Оповещает об изменении состояния товара, добавлен либо удален из списка сохраненных товаров
     *
     * @param listing товар, состояние которого было изменено
     * @param saved   состояние, true - товар был добавлен в список, false - товар был удален из списка
     */
    void onListingSavedStateChanged(Listing listing, boolean saved);

}