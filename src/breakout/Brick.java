package breakout;

import java.awt.Color;
import java.awt.Graphics2D;

public class Brick {
    private int x, y, width, height;
    private boolean isDestroyed = false;
    private int type; // 0: 普通, 1: 道具磚塊

    public Brick(int x, int y, int width, int height, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public void draw(Graphics2D g) {
        if (isDestroyed) return;
        if (type == 1) {
            g.setColor(new Color(255, 69, 0));    // 道具磚塊：亮橘紅色 (Neon Orange)
        } else {
            g.setColor(new Color(0, 191, 255));   // 普通磚塊：深天藍色 (Deep Sky Blue)
        }
        g.fillRect(x, y, width, height);
        
        // ✨ 修正 4：將磚塊邊框改為黑色，在霓虹磚塊矩陣中做視覺區隔
        g.setColor(Color.BLACK); 
        g.drawRect(x, y, width, height);
    }

    // 簡單的矩形碰撞檢查
    public boolean checkCollision(Ball ball) {
        if (isDestroyed) return false;
        if (ball.getX() + ball.getSize() > x && ball.getX() < x + width &&
            ball.getY() + ball.getSize() > y && ball.getY() < y + height) {
            isDestroyed = true;
            return true;
        }
        return false;
    }

    public boolean isDestroyed() { return isDestroyed; }
    public int getType() { return type; }
}
