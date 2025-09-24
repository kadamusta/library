package androidx.kadadevelopers.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String PREF_NAME = "ThemePrefs";
    private static final String KEY_THEME_PAGE = "theme_page";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // حفظ رقم الثيم
    public void setThemePage(int themePage) {
        editor.putInt(KEY_THEME_PAGE, themePage);
        editor.apply();
    }

    // استرجاع رقم الثيم
    public int getThemePage() {
        return pref.getInt(KEY_THEME_PAGE, 0); // 0 = Light افتراضي
    }
}
