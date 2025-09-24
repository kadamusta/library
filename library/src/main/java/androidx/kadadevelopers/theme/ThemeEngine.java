package androidx.kadadevelopers.theme;

import android.content.Context;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.kadadevelopers.R;
import androidx.kadadevelopers.utils.PrefManager;

public class ThemeEngine {

    private final PrefManager prefManager;
    private static final String KEY_THEME_PAGE = "theme_page";

    public ThemeEngine(Context context) {
        prefManager = new PrefManager(context);
    }

    // استرجاع رقم الثيم
    public int getThemePage() {
        return prefManager.getThemePage();
    }

    public void saveTheme(int themePage) {
        prefManager.setThemePage(themePage);
    }

    // تطبيق الثيم عند بدء التطبيق أو بعد تغييره
    public void applyTheme() {
        int themePage = getThemePage();

        switch (themePage) {
            case 1: // Dark
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2: // Grey
            case 3: // Blue
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            default: // Light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    // تحويل رقم الثيم إلى R.style
    @StyleRes
    public int getAppThemeRes() {
        int theme = getThemePage();
        switch (theme) {
            case 1:
                return R.style.Nemosofts_ThemeEngine_Dark;
            case 2:
                return R.style.Nemosofts_ThemeEngine_Grey;
            case 3:
                return R.style.Nemosofts_ThemeEngine_Blue;
            default:
                return R.style.Nemosofts_ThemeEngine_Light;
        }
    }

}
