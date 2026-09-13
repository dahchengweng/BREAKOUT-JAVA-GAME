package breakout;

import java.awt.*;
import javax.swing.JPanel;

public class GameView extends JPanel {
    private GameModel model;

    public GameView(GameModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. 畫球
        model.getBall().draw(g2d);

        // 2. 畫球板 (顏色與寬度取決於當前的策略物件)
        g2d.setColor(model.getCurrentStrategy().getColor());
        g2d.fillRect(model.getPaddleX(), model.getPaddleY(), model.getPaddleWidth(), model.getPaddleHeight());

        // 3. 畫所有磚塊
        Brick[][] bricks = model.getBricks();
        for (int r = 0; r < bricks.length; r++) {
            for (int c = 0; c < bricks[r].length; c++) {
                bricks[r][c].draw(g2d);
            }
        }

        // 4. UI 資訊 (包含當前復健策略狀態)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        g2d.drawString("分數: " + model.getScore() + "  |  生命值: " + model.getLives(), 20, 30);
        g2d.drawString("當前輔助狀態: " + model.getCurrentStrategy().getName(), 550, 30);

        if (model.isCountingDown()) {
            // 半透明白色遮罩，在黑背景下營造煙霧感
            g2d.setColor(new Color(255, 255, 255, 30));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // 亮黃色倒數大字
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Impact", Font.BOLD, 120));
            String text = String.valueOf(model.getCountdownTime());
            
            FontMetrics fm = g2d.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(text)) / 2;
            int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2d.drawString(text, textX, textY);
        }

        if (model.isGameOver()) {
            g2d.setColor(new Color(255, 255, 255, 30));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Microsoft JhengHei", Font.BOLD, 40));
            g2d.drawString("遊戲結束 (請重新練習)", 220, 300);
        }
    }
}
