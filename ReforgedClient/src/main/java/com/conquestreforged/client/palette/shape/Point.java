package com.conquestreforged.client.palette.shape;

public class Point {

    public static final Point ZERO = new Point(0, 0);

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float distance(Point other) {
        return distance(other.x, other.y);
    }
    public float distance(int x, int y) {
        return FloatMath.sqrt(distance2(x, y));
    }

    public int distance2(Point other) {
        return distance2(other.x, other.y);
    }

    public int distance2(int ox, int oy) {
        int dx = x - ox;
        int dy = y - oy;
        return dx * dx + dy * dy;
    }
}
