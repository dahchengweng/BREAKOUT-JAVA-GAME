package breakout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GameController implements KeyListener {
    private GameModel model;
    private GameView view;
    
    // 追蹤按鍵狀態以保持移動平滑（對手部協調度不佳的患者很有幫助）
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameController() {
        SoundManager.init();
        this.model = new GameModel();
        this.view = new GameView(model);

        JFrame frame = new JFrame("Java 物件導向遊戲 - 打磚塊篇");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.addKeyListener(this); // 將監聽器註冊在視窗上
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 遊戲主迴圈
        Timer timer = new Timer(16, e -> {
            if (leftPressed) model.movePaddleLeft();
            if (rightPressed) model.movePaddleRight();
            
            model.updateGame();
            view.repaint();
        });
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GameController::new);
    }
}
