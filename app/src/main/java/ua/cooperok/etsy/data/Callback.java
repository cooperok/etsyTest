package ua.cooperok.etsy.data;

/**
 * Слушатель на получение данных от какого либо сервиса данных
 *
 * @param <T>
 */
public interface Callback<T> {

    void onDataReceived(T data);

    void onError();

}