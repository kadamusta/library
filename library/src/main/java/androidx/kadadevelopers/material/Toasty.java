package androidx.kadadevelopers.material;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.kadadevelopers.R;

import java.util.Objects;

@Keep
public class Toasty {

    private static final String TAG = "Toasty";
    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;

    private static final int TOAST_DURATION = 1800; // ms

    private Toasty() {
        throw new IllegalStateException("Utility class");
    }

    // --- Public API ---
    public static void makeText(Activity activity, String message) {
        makeText(activity, false, message, SUCCESS);
    }

    public static void makeText(Activity activity, Boolean isStatus, String message) {
        makeText(activity, isStatus, message, SUCCESS);
    }

    public static void makeText(Activity activity, Boolean isStatus, String message, int toastType) {
        if (activity != null && !activity.isFinishing()) {
            configureToastyView(activity, message, toastType, isStatus);
        }
    }

    public static void makeText(Activity activity, String message, int toastType) {
        makeText(activity, false, message, toastType);
    }

    // --- Core ---
    private static void configureToastyView(Activity activity, String message, int toastType, Boolean hideStatusBar) {
        try {
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.toasty_dialog);

            // Views
            RelativeLayout toastBg = dialog.findViewById(R.id.ll_toast_bg);
            ImageView toastIcon = dialog.findViewById(R.id.iv_toast_icon);
            TextView toastTitle = dialog.findViewById(R.id.tv_toast_title);
            TextView toastMessage = dialog.findViewById(R.id.tv_toast_message);

            // Close button
            dialog.findViewById(R.id.iv_toast_close).setOnClickListener(v -> dialog.dismiss());

            // Apply style
            applyDefaultStyles(activity, toastType, toastBg, toastIcon, toastTitle);

            // Title + Icon override
            switch (toastType) {
                case ERROR:
                    toastTitle.setText("Error!");
                    toastIcon.setImageResource(R.drawable.toasty_ic_error);
                    toastIcon.setBackgroundResource(R.drawable.toasty_icon_error_bg);
                    toastBg.setBackgroundResource(R.drawable.toasty_error_bg);
                    break;

                case SUCCESS:
                    toastTitle.setText("Success!");
                    toastIcon.setImageResource(R.drawable.toasty_ic_success);
                    toastIcon.setBackgroundResource(R.drawable.toasty_icon_success_bg);
                    toastBg.setBackgroundResource(R.drawable.toasty_success_bg);
                    break;

                case WARNING:
                    toastTitle.setText("Warning!");
                    toastIcon.setImageResource(R.drawable.toasty_ic_warning);
                    toastIcon.setBackgroundResource(R.drawable.toasty_icon_warning_bg);
                    toastBg.setBackgroundResource(R.drawable.toasty_warning_bg);
                    break;

                case INFO:
                    toastTitle.setText("Info!");
                    toastIcon.setImageResource(R.drawable.toasty_ic_info);
                    toastIcon.setBackgroundResource(R.drawable.toasty_icon_info_bg);
                    toastBg.setBackgroundResource(R.drawable.toasty_info_bg);
                    break;
            }

            // Message
            if (message == null || message.trim().isEmpty()) {
                message = getDefaultMessage(toastType);
            }
            toastMessage.setText(message);

            // Window styling
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                );
                window.getAttributes().windowAnimations = R.style.dialogAnimation;

                if (Boolean.TRUE.equals(hideStatusBar)) {
                    hideStatusBarDialog(window);
                }
            }

            // Auto dismiss
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!activity.isFinishing() && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }, TOAST_DURATION);

        } catch (Exception e) {
            Log.e(TAG, "Error displaying toast", e);
        }
    }

    // --- Styling ---
    private static void applyDefaultStyles(Context context, int toastType,
        RelativeLayout toastBg, ImageView toastIcon, TextView toastTitle) {
        switch (toastType) {
            case ERROR:
                toastTitle.setText(context.getString(R.string.toasty_error_title));
                toastIcon.setImageResource(R.drawable.toasty_ic_error);
                toastIcon.setBackgroundResource(R.drawable.toasty_icon_error_bg);
                toastBg.setBackgroundResource(R.drawable.toasty_error_bg);
                break;

            case SUCCESS:
                toastTitle.setText(context.getString(R.string.toasty_success_title));
                toastIcon.setImageResource(R.drawable.toasty_ic_success);
                toastIcon.setBackgroundResource(R.drawable.toasty_icon_success_bg);
                toastBg.setBackgroundResource(R.drawable.toasty_success_bg);
                break;

            case WARNING:
                toastTitle.setText(context.getString(R.string.toasty_warning_title));
                toastIcon.setImageResource(R.drawable.toasty_ic_warning);
                toastIcon.setBackgroundResource(R.drawable.toasty_icon_warning_bg);
                toastBg.setBackgroundResource(R.drawable.toasty_warning_bg);
                break;

            case INFO:
                toastTitle.setText(context.getString(R.string.toasty_info_title));
                toastIcon.setImageResource(R.drawable.toasty_ic_info);
                toastIcon.setBackgroundResource(R.drawable.toasty_icon_info_bg);
                toastBg.setBackgroundResource(R.drawable.toasty_info_bg);
                break;
        }
    }

    @NonNull
    private static String getDefaultMessage(int toastType) {
        switch (toastType) {
            case ERROR:
                return "This is an error message.";
            case SUCCESS:
                return "This is a success message.";
            case WARNING:
                return "This is a warning message.";
            case INFO:
                return "This is an information message.";
            default:
                return "This is a default message.";
        }
    }

    private static void hideStatusBarDialog(Window window) {
        try {
            View decorView = window.getDecorView();
            WindowCompat.setDecorFitsSystemWindows(window, false);
            WindowInsetsControllerCompat insetsController =
                    new WindowInsetsControllerCompat(window, decorView);

            int types = Type.statusBars() | Type.navigationBars();
            insetsController.hide(types);
            insetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        } catch (Exception e) {
            Log.e(TAG, "Failed to hide status bar toasty", e);
        }
    }
}
