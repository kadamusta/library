package androidx.kadadevelopers.material;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Keep;
import androidx.annotation.UiThread;

@Keep
interface PBDelegate {
    @UiThread
    void draw(Canvas canvas, Paint paint);

    @UiThread
    void start();

    @UiThread
    void stop();

    @UiThread
    void progressiveStop(CircularProgressDrawable.OnEndListener listener);
}
