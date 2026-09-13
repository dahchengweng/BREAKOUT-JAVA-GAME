package breakout;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball {
    private double x, y;
    private double vx, vy;
    private int size = 15;

    public Ball(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void update() {
        x += vx;
        y += vy;
    }

    // 邊界反彈 (左右壁與天花板)
    public void checkWallCollisions(int width) {
        if (x < 0) { x = 0; vx = -vx; }
        if (x > width - size) { x = width - size; vx = -vx; }
        if (y < 0) { y = 0; vy = -vy; }
    }

    public void reverseY() { vy = -vy; }
    public void reverseX() { vx = -vx; }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillOval((int)x, (int)y, size, size);
    }

    // Getters and Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public int getSize() { return size; }
    public void setVy(double vy) { this.vy = vy; }
    public void setY(double y) { this.y = y; }
    
    // ✨ 新增：讓 Model 可以動態改變球的 X 軸速度（控制反彈角度）
    public void setVx(double vx) { this.vx = vx; }
}
