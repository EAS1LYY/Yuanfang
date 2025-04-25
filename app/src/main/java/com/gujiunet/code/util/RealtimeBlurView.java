package com.gujiunet.code.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

public class RealtimeBlurView extends View {
    private static int blurEventType;
    private static int o;
    private static final RuntimeException EXCEPTION = new RuntimeException();
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private final BlurImpl blurImpl;
    private Canvas canvas;
    private View decorView;
    private boolean e;
    private boolean i;
    private boolean n;
    private final ViewTreeObserver.OnPreDrawListener onPreDrawListener;
    private int overlayColor;
    private final Paint paint;
    private final Rect rect;
    private final Rect rect1;
    private float roundCornerRadius;
    private float scaleFactor;

    public RealtimeBlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.blurImpl = getBlurImpl();
        this.rect = new Rect();
        this.rect1 = new Rect();
        TypedArray var3 = context.obtainStyledAttributes(attributeSet, new int[]{2130771968, 2130771969, 2130771970});
        this.roundCornerRadius = var3.getDimension(0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f, context.getResources().getDisplayMetrics()));
        this.scaleFactor = var3.getFloat(1, 4.0f);
        this.overlayColor = var3.getColor(2, -1426063361);
        var3.recycle();
        this.paint = new Paint();
        RealtimeBlurView view = this;
        this.onPreDrawListener = () -> {

            int[] var1 = new int[2];
            Bitmap var2 = c(view);
            View var31 = e(view);
            if (var31 != null && view.isShown() && view.b()) {
                boolean var4 = c(view) != var2;
                var31.getLocationOnScreen(var1);
                int var5 = -var1[0];
                int var6 = -var1[1];
                view.getLocationOnScreen(var1);
                int var7 = var1[0];
                int var8 = var1[1];
                b(view).eraseColor(a(view) & 16777215);
                int var9 = d(view).save();
                a(view, true);
                a(a() + 1);
                try {
                    d(view).scale((float) b(view).getWidth() / view.getWidth(), (b(view).getHeight() * 1.0f) / view.getHeight());
                    d(view).translate(-(var5 + var7), -(var8 + var6));
                    if (var31.getBackground() != null) {
                        var31.getBackground().draw(d(view));
                    }
                    var31.draw(d(view));
                } catch (RuntimeException e) {
                } catch (Throwable th) {
                    a(view, false);
                    a(a() - 1);
                    d(view).restoreToCount(var9);
                    throw th;
                }
                a(view, false);
                a(a() - 1);
                d(view).restoreToCount(var9);
                a(b(view), c(view));
                if (var4 || f(view)) {
                    view.invalidate();
                }
            }
            return true;
        };
        //setBlurRadius(100f);
        setBlurRadius(150f);
    }



    static int a() {
        return o;
    }

    static int a(RealtimeBlurView var0) {
        return var0.overlayColor;
    }

    static void a(int var0) {
        o = var0;
    }

    static void a(RealtimeBlurView var0, boolean var1) {
        var0.i = var1;
    }

    static Bitmap b(RealtimeBlurView var0) {
        return var0.bitmap;
    }

    static Bitmap c(RealtimeBlurView var0) {
        return var0.bitmap1;
    }

    protected final boolean b() {
        Bitmap bitmap;
        float f = 25.0f;
        float f2 = this.roundCornerRadius;
        if (f2 == 0.0f) {
            d();
            return false;
        }
        float f22 = this.scaleFactor;
        float f3 = f2 / f22;
        if (f3 > 25.0f) {
            f22 = (f22 * f3) / 25.0f;
        } else {
            f = f3;
        }
        int width = getWidth();
        int height = getHeight();
        int width2 = Math.max(1, (int) (width / f22));
        int height2 = Math.max(1, (int) (height / f22));
        boolean z = this.e;
        if (this.canvas == null || (bitmap = this.bitmap1) == null || bitmap.getWidth() != width2 || this.bitmap1.getHeight() != height2) {
            c();
            try {
                Bitmap createBitmap = Bitmap.createBitmap(width2, height2, Bitmap.Config.ARGB_8888);
                this.bitmap = createBitmap;
                if (createBitmap == null) {
                    d();
                    return false;
                }
                this.canvas = new Canvas(this.bitmap);
                Bitmap createBitmap2 = Bitmap.createBitmap(width2, height2, Bitmap.Config.ARGB_8888);
                this.bitmap1 = createBitmap2;
                if (createBitmap2 == null) {
                    d();
                    return false;
                }
                z = true;
            } catch (OutOfMemoryError e) {
            } catch (Throwable th) {
                d();
                return false;
            }
        }
        if (z) {
            if (!this.blurImpl.a(getContext(), this.bitmap, f)) {
                return false;
            }
            this.e = false;
        }
        return true;
    }

    private void c() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.bitmap = null;
        }
        Bitmap bitmap2 = this.bitmap1;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.bitmap1 = null;
        }
    }

    static Canvas d(RealtimeBlurView var0) {
        return var0.canvas;
    }

    private void d() {
        c();
        this.blurImpl.a();
    }

    static View e(RealtimeBlurView var0) {
        return var0.decorView;
    }

    static boolean f(RealtimeBlurView var0) {
        return var0.n;
    }

    protected final void a(Bitmap var1, Bitmap var2) {
        this.blurImpl.a(var1, var2);
    }

    @Override // android.view.View
    public void draw(Canvas var1) {
        if (this.i) {
            throw EXCEPTION;
        }
        if (o <= 0) {
            super.draw(var1);
        }
    }

    protected View getActivityDecorView() {
        Context var1 = getContext();
        for (int var2 = 0; var2 < 4 && !(var1 instanceof Activity) && (var1 instanceof ContextWrapper); var2++) {
            var1 = ((ContextWrapper) var1).getBaseContext();
        }
        if (var1 instanceof Activity) {
            View var3 = ((Activity) var1).getWindow().getDecorView();
            return var3;
        }
        return null;
    }

    protected BlurImpl getBlurImpl() {
        if (blurEventType == 0) {
            try {
                BlurImpl2 var1 = new BlurImpl2();
                Bitmap var2 = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                var1.a(getContext(), var2, 4.0f);
                var1.a();
                var2.recycle();
                blurEventType = 3;
            } catch (Throwable th) {
            }
        }
        if (blurEventType == 0) {
            try {
                BlurImpl2 var8 = new BlurImpl2();
                Bitmap var6 = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                var8.a(getContext(), var6, 4.0f);
                var8.a();
                var6.recycle();
                blurEventType = 1;
            } catch (Throwable th2) {
            }
        }
        if (blurEventType == 0) {
            try {
                BlurImpl3 blurImpl3 = new BlurImpl3();
                Bitmap var22 = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                blurImpl3.a(getContext(), var22, 4.0f);
                blurImpl3.a();
                var22.recycle();
                blurEventType = 2;
            } catch (Throwable th3) {
            }
        }
        if (blurEventType == 0) {
            blurEventType = -1;
        }
        switch (blurEventType) {
            case 1:
                return new BlurImpl2();
            case 2:
                return new BlurImpl3();
            case 3:
                return new BlurImpl4();
            default:
                return new BlurImpl1();
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        boolean var1 = false;
        super.onAttachedToWindow();
        View activityDecorView = getActivityDecorView();
        this.decorView = activityDecorView;
        if (activityDecorView != null) {
            activityDecorView.getViewTreeObserver().addOnPreDrawListener(this.onPreDrawListener);
            if (this.decorView.getRootView() != getRootView()) {
                var1 = true;
            }
            this.n = var1;
            if (var1) {
                this.decorView.postInvalidate();
                return;
            }
            return;
        }
        this.n = false;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        View view = this.decorView;
        if (view != null) {
            view.getViewTreeObserver().removeOnPreDrawListener(this.onPreDrawListener);
        }
        d();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas var1) {
        super.onDraw(var1);
        Bitmap var2 = this.bitmap1;
        int var3 = this.overlayColor;
        if (var2 != null) {
            this.rect.right = var2.getWidth();
            this.rect.bottom = var2.getHeight();
            this.rect1.right = getWidth();
            this.rect1.bottom = getHeight();
            var1.drawBitmap(var2, this.rect, this.rect1, (Paint) null);
        }
        this.paint.setColor(var3);
        var1.drawRect(this.rect1, this.paint);
    }

    public void setBlurRadius(float var1) {
        if (this.roundCornerRadius != var1) {
            this.roundCornerRadius = var1;
            this.e = true;
            invalidate();
        }
    }

    public void setDownsampleFactor(float Factor) {
        if (Factor <= 0.0f) {
            throw new IllegalArgumentException("");
        }
        if (this.scaleFactor != Factor) {
            this.scaleFactor = Factor;
            this.e = true;
            c();
            invalidate();
        }
    }

    public void setOverlayColor(int color) {
        if (this.overlayColor != color) {
            this.overlayColor = color;
            invalidate();
        }
    }


    public interface BlurImpl {
        void a();

        void a(Bitmap bitmap, Bitmap bitmap2);

        boolean a(Context context, Bitmap bitmap, float f);
    }

    public final class BlurImpl1 implements BlurImpl {
        public  void a() {
        }

        public  void a(Bitmap bitmap, Bitmap bitmap2) {
        }

        public  boolean a(Context context, Bitmap bitmap, float f) {
            return false;
        }
    }

    public static final class BlurImpl2 implements BlurImpl {
        private final Boolean e = null;
        private RenderScript a;
        private ScriptIntrinsicBlur b;
        private Allocation c;
        private Allocation d;

        public  boolean a(Context context, Bitmap bitmap, float f) {
            if (this.a == null) {
                try {
                    RenderScript create = RenderScript.create(context);
                    this.a = create;
                    this.b = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                } catch (RSRuntimeException e) {
                    RSRuntimeException rSRuntimeException = e;
                    if ((context.getApplicationInfo().flags & 2) != 0) {
                        Boolean.TRUE.booleanValue();
                        if (1 != 0) {
                            a();
                            return false;
                        }
                        throw rSRuntimeException;
                    }
                }
            }
            this.b.setRadius(f);
            Allocation createFromBitmap = Allocation.createFromBitmap(this.a, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            this.c = createFromBitmap;
            this.d = Allocation.createTyped(this.a, createFromBitmap.getType());
            return true;
        }

        public  void a() {
            Allocation allocation = this.c;
            if (allocation != null) {
                allocation.destroy();
                this.c = null;
            }
            allocation = this.d;
            if (allocation != null) {
                allocation.destroy();
                this.d = null;
            }
            ScriptIntrinsicBlur scriptIntrinsicBlur = this.b;
            if (scriptIntrinsicBlur != null) {
                scriptIntrinsicBlur.destroy();
                this.b = null;
            }
            RenderScript renderScript = this.a;
            if (renderScript != null) {
                renderScript.destroy();
                this.a = null;
            }
        }

        public  void a(Bitmap bitmap, Bitmap bitmap2) {
            this.c.copyFrom(bitmap);
            this.b.setInput(this.c);
            this.b.forEach(this.d);
            this.d.copyTo(bitmap2);
        }
    }

    public static final class BlurImpl3 implements BlurImpl {
        private final Boolean e = null;
        private RenderScript a;
        private ScriptIntrinsicBlur b;
        private Allocation c;
        private Allocation d;

        public  boolean a(Context context, Bitmap bitmap, float f) {
            if (this.a == null) {
                try {
                    RenderScript create = RenderScript.create(context);
                    this.a = create;
                    this.b = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                } catch (RSRuntimeException e) {
                    RSRuntimeException rSRuntimeException = e;
                    if ((context.getApplicationInfo().flags & 2) != 0) {
                        Boolean.TRUE.booleanValue();
                        if (1 != 0) {
                            a();
                            return false;
                        }
                        throw rSRuntimeException;
                    }
                }
            }
            this.b.setRadius(f);
            Allocation createFromBitmap = Allocation.createFromBitmap(this.a, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            this.c = createFromBitmap;
            this.d = Allocation.createTyped(this.a, createFromBitmap.getType());
            return true;
        }

        public void a() {
            Allocation allocation = this.c;
            if (allocation != null) {
                allocation.destroy();
                this.c = null;
            }
            allocation = this.d;
            if (allocation != null) {
                allocation.destroy();
                this.d = null;
            }
            ScriptIntrinsicBlur scriptIntrinsicBlur = this.b;
            if (scriptIntrinsicBlur != null) {
                scriptIntrinsicBlur.destroy();
                this.b = null;
            }
            RenderScript renderScript = this.a;
            if (renderScript != null) {
                renderScript.destroy();
                this.a = null;
            }
        }

        public void a(Bitmap bitmap, Bitmap bitmap2) {
            this.c.copyFrom(bitmap);
            this.b.setInput(this.c);
            this.b.forEach(this.d);
            this.d.copyTo(bitmap2);
        }
    }


    public static final class BlurImpl4 implements BlurImpl {
        private final Boolean e = null;
        private RenderScript a;
        private ScriptIntrinsicBlur b;
        private Allocation c;
        private Allocation d;

        public boolean a(Context context, Bitmap bitmap, float f) {
            if (this.a == null) {
                try {
                    RenderScript create = RenderScript.create(context);
                    this.a = create;
                    this.b = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                } catch (RSRuntimeException e) {
                    RSRuntimeException rSRuntimeException = e;
                    if ((context.getApplicationInfo().flags & 2) != 0) {
                        Boolean.TRUE.booleanValue();
                        if (1 != 0) {
                            a();
                            return false;
                        }
                        throw rSRuntimeException;
                    }
                }
            }
            this.b.setRadius(f);
            Allocation createFromBitmap = Allocation.createFromBitmap(this.a, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            this.c = createFromBitmap;
            this.d = Allocation.createTyped(this.a, createFromBitmap.getType());
            return true;
        }

        public void a() {
            Allocation allocation = this.c;
            if (allocation != null) {
                allocation.destroy();
                this.c = null;
            }
            allocation = this.d;
            if (allocation != null) {
                allocation.destroy();
                this.d = null;
            }
            ScriptIntrinsicBlur scriptIntrinsicBlur = this.b;
            if (scriptIntrinsicBlur != null) {
                scriptIntrinsicBlur.destroy();
                this.b = null;
            }
            RenderScript renderScript = this.a;
            if (renderScript != null) {
                renderScript.destroy();
                this.a = null;
            }
        }

        public void a(Bitmap bitmap, Bitmap bitmap2) {
            this.c.copyFrom(bitmap);
            this.b.setInput(this.c);
            this.b.forEach(this.d);
            this.d.copyTo(bitmap2);
        }
    }


}