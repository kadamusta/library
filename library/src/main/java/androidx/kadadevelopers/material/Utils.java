package androidx.kadadevelopers.material;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.PowerManager;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.Locale;

@Keep
class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    // التحقق من السرعة
    public static void checkSpeed(float speed) {
        if (speed <= 0.0F) {
            throw new IllegalArgumentException("Speed must be >= 0");
        }
    }

    // التحقق من الألوان
    public static void checkColors(int[] colors) {
        if (colors == null || colors.length == 0) {
            throw new IllegalArgumentException("You must provide at least 1 color");
        }
    }

    // التحقق من الزاوية
    public static void checkAngle(int angle) {
        if (angle < 0 || angle > 360) {
            throw new IllegalArgumentException(
                    String.format(Locale.US,
                            "Illegal angle %d: must be >=0 and <=360", angle)
            );
        }
    }

    // التحقق من القيمة ≥0
    public static void checkPositiveOrZero(float number, String name) {
        if (number < 0.0F) {
            throw new IllegalArgumentException(
                    String.format(Locale.US, "%s %f must be positive", name, number)
            );
        }
    }

    // التحقق من أن الكائن غير null
    public static void checkNotNull(Object o, String name) {
        if (o == null) {
            throw new IllegalArgumentException(name + " must be not null");
        }
    }

    // نسبة التقدم في الأنيميشن
    public static float getAnimatedFraction(ValueAnimator animator) {
        return animator.getAnimatedFraction();
    }

    // التحقق من وضع حفظ الطاقة
    public static boolean isPowerSaveModeEnabled(@NonNull PowerManager powerManager) {
        try {
            return powerManager.isPowerSaveMode();
        } catch (Exception e) {
            return false;
        }
    }

    // الحصول على PowerManager
    public static PowerManager powerManager(@NonNull Context context) {
        return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }
}
