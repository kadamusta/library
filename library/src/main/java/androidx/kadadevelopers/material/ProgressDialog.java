package androidx.kadadevelopers.material;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public class ProgressDialog extends Dialog {
    private static final String TAG = "ProgressDialog";
    private final boolean isHide;
    private final boolean isStatus;

    // === Constructors ===
    public ProgressDialog(@NonNull Context context) {
        this(context, false, false);
    }

    public ProgressDialog(@NonNull Context context, boolean isStatus, boolean isHide) {
        super(context);
        this.isStatus = isStatus;
        this.isHide = isHide;
    }

    public ProgressDialog(@NonNull Context context, int themeResId, boolean isStatus, boolean isHide) {
        super(context, themeResId);
        this.isStatus = isStatus;
        this.isHide = isHide;
    }

    // === Hide status bar helper ===
    private static void hideStatusBarDialog(Window window) {
        if (window != null) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new ProgressBar(getContext()));
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (window != null) {
            if (isHide) {
                hideStatusBarDialog(window);
            }
            if (isStatus) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }
}
