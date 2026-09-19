package com.example.cpsmod;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Keeps a rolling one-second window of click timestamps for the left and
 * right mouse buttons, and reports how many fell in that window (= current CPS).
 */
public class ClickTracker {

    public static final ClickTracker INSTANCE = new ClickTracker();

    private static final long WINDOW_MS = 1000L;

    private final Deque<Long> leftClicks = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();

    private ClickTracker() {
    }

    /** Called from the Mouse mixin whenever a button is pressed. GLFW button ids: 0 = left, 1 = right. */
    public void registerClick(int button) {
        long now = System.currentTimeMillis();
        if (button == 0) {
            leftClicks.addLast(now);
        } else if (button == 1) {
            rightClicks.addLast(now);
        }
    }

    public int getLeftCps() {
        return countRecent(leftClicks);
    }

    public int getRightCps() {
        return countRecent(rightClicks);
    }

    private int countRecent(Deque<Long> deque) {
        long now = System.currentTimeMillis();
        while (!deque.isEmpty() && now - deque.peekFirst() > WINDOW_MS) {
            deque.pollFirst();
        }
        return deque.size();
    }
}
