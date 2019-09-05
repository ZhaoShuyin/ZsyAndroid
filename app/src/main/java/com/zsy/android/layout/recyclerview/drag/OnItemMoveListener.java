package com.zsy.android.layout.recyclerview.drag;

/**
 * Item移动后 触发
 */
public interface OnItemMoveListener {
    void onItemMove(int fromPosition, int toPosition);
}
