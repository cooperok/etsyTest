package ua.cooperok.etsy.log;

import android.content.Context;
import android.util.Log;

import ua.cooperok.etsy.BuildConfig;


/**
 * Класс отвечающий за логирование ошибок
 */
public class Logger {

    private static final String TAG = "debug";

    private static Logger sInstance;

    /**
     * Указывает на то что сервис Crashltics был успешно подключен и запущен
     */
    private boolean mCrashlyticsInitialized;

    private Logger(Context context) {
        if (!BuildConfig.DEBUG) {
            try {
//                CrashlyticsCore core = new CrashlyticsCore.Builder().build();
//                Fabric.with(context, new Crashlytics.Builder().core(core).build());
                mCrashlyticsInitialized = true;
            } catch (Exception e) {
                // Если произошла какая то ошибка при инциализации, например не указан API key в манифесте
                mCrashlyticsInitialized = false;
            }
        }
    }

    /**
     * Создает объект логера
     *
     * @param context контекст необходим для использования Crashlytics
     */
    public static void buildLogger(Context context) {
        sInstance = new Logger(context);
    }

    /**
     * Метод используется для получения объекта логера. Создание самого объекта происходит отдельно,
     * для этого необходимо вызывать метод {@link #buildLogger(Context)}
     *
     * @return объект логера, либо null если он не был до этого проинициализирован с помощью метода {@link #buildLogger(Context)}
     */
    public static Logger getInstance() {
        return sInstance;
    }

    public void log(String log) {
        if (mCrashlyticsInitialized) {
//            Crashlytics.getInstance().core.log(log);
        } else {
            Log.i(TAG, log);
        }
    }

    public void log(Exception e) {
        if (mCrashlyticsInitialized) {
//            Crashlytics.getInstance().core.logException(e);
        } else {
            Log.e(TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

}