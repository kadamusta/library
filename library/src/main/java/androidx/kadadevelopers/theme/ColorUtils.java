package androidx.kadadevelopers.theme;

import android.content.Context;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.kadadevelopers.R;

@androidx.annotation.Keep
public class ColorUtils {

    private ColorUtils() {
        throw new IllegalStateException("Utility class");
    }

    // ----- ألوان عامة -----
    @ColorInt
    public static int colorWhite(Context ctx) {
        return ContextCompat.getColor(ctx, R.color.white);
    }

    @ColorInt
    public static int colorBlack(Context ctx) {
        return ContextCompat.getColor(ctx, R.color.black);
    }

    // ----- الألوان حسب الثيم -----
    @ColorInt
    public static int colorPrimary(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_primary);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_primary);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_primary);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_primary);
        }
    }

    @ColorInt
    public static int colorPrimarySub(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_primary_sub);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_primary_sub);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_primary_sub);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_primary_sub);
        }
    }

    @ColorInt
    public static int colorTitle(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_title);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_title);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_title);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_title);
        }
    }

    @ColorInt
    public static int colorTitleSub(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_title_sub);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_title_sub);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_title_sub);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_title_sub);
        }
    }

    @ColorInt
    public static int colorBg(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_bg);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_bg);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_bg);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_bg);
        }
    }

    @ColorInt
    public static int colorBgSub(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_bg_sub);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_bg_sub);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_bg_sub);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_bg_sub);
        }
    }

    @ColorInt
    public static int colorBorder(Context ctx) {
        int themePage = new ThemeEngine(ctx).getThemePage();
        switch (themePage) {
            case 1: return ContextCompat.getColor(ctx, R.color.ns_dark_border);
            case 2: return ContextCompat.getColor(ctx, R.color.ns_grey_border);
            case 3: return ContextCompat.getColor(ctx, R.color.ns_blue_border);
            default: return ContextCompat.getColor(ctx, R.color.ns_classic_border);
        }
    }
}
