package com.example.btvn_buoi5_andrroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipItemCallback extends ItemTouchHelper.SimpleCallback implements View.OnTouchListener {
    private IItemSwipListener itemSwipeListener;
    private RecyclerView.Adapter maAdapter;
    private Context mContext;
    private Drawable iconDelete;
    private int deleteColor, backgroundColor;
    private int iconFilterColor;
    private Paint circlePaint;
    private float swipeThreshold, progress;
    private float circleRadius = 3;
    public static final int CIRCLE_ACCELERATION = 3;
    private float opacity = 1f;
    private float iconScale = 1f;
    private float iconPadding = 12;

    public SwipItemCallback(Context context, RecyclerView.Adapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.maAdapter = adapter;
        this.mContext = context;

        this.iconDelete = ContextCompat.getDrawable(mContext, R.drawable.ic_delete);
        deleteColor = Color.parseColor("#FFE57373");
        backgroundColor = Color.parseColor("FF979797");
        iconFilterColor = deleteColor;
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(deleteColor);
        swipeThreshold = getSwipeThreshold();
    }

    private float getSwipeThreshold() {
        return 0.5f;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int positon = viewHolder.getAdapterPosition();
        if (itemSwipeListener != null) {
            itemSwipeListener.onItemSwiped(positon);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dX == 0) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }
        View itemView = viewHolder.itemView;
        float left = itemView.getLeft();
        float right = itemView.getRight();
        float top = itemView.getTop();
        float bottom = itemView.getBottom();
        float width = itemView.getMeasuredWidth();
        float height = itemView.getMeasuredHeight();
        float saveCount = c.save();

        c.clipRect(right + dX, top, right, bottom);
        c.drawColor(backgroundColor);

        progress = -dX / width;


        circleRadius = (progress - swipeThreshold) * width * CIRCLE_ACCELERATION;

        // Tính toạ độ x,y của hình tròn
        // sau đó sẽ dùng x và y này để làm tâm vẽ icon và hình tròn sau này
        float cx = right - iconPadding - iconDelete.getIntrinsicWidth() / 2f;
        float cy = top + height / 2f;
        float halfIconSize = iconDelete.getIntrinsicWidth() * iconScale / 2f;

        iconDelete.setBounds((int) (cx - halfIconSize), (int) (cy - halfIconSize), (int) (cx + halfIconSize), (int) (cy + halfIconSize));
        iconDelete.setAlpha(Math.round(opacity * 255f));
        iconDelete.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.gray), PorterDuff.Mode.SRC_IN));
        // Vẽ hình tròn khi progress > swipeThreshold => radius > 0
        if (circleRadius > 0) {
            c.drawCircle(cx, cy, circleRadius, circlePaint);
            iconDelete.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.white), PorterDuff.Mode.SRC_IN));
        }
        iconDelete.draw(c);
        c.restoreToCount(Math.round(saveCount));
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        float value = defaultValue + 10;
        return value;
    }

    public void setItemSwipeListener(IItemSwipListener itemSwipeListener) {
        this.itemSwipeListener = itemSwipeListener;
    }
}
