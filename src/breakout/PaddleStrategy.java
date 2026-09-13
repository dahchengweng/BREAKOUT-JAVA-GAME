package breakout;

import java.awt.Color;

// 策略介面
public interface PaddleStrategy {
    int getWidth(int baseWidth);
    Color getColor();
    String getName();
}

// 策略實作 A：正常狀態
class NormalPaddle implements PaddleStrategy {
    @Override public int getWidth(int baseWidth) { return baseWidth; }
    @Override public Color getColor() { return Color.BLUE; }
    @Override public String getName() { return "正常模式"; }
}

// 策略實作 B：放大球板 (降低物理操作難度，適合復健初期)
class EnlargedPaddle implements PaddleStrategy {
    @Override public int getWidth(int baseWidth) { return (int)(baseWidth * 1.6); }
    @Override public Color getColor() { return Color.GREEN; }
    @Override public String getName() { return "放大模式 (復健輔助)"; }
}

// 策略實作 C：縮小球板 (提高挑戰性，訓練精細動作)
class ShrinkedPaddle implements PaddleStrategy {
    @Override public int getWidth(int baseWidth) { return (int)(baseWidth * 0.6); }
    @Override public Color getColor() { return Color.RED; }
    @Override public String getName() { return "挑戰模式 (精細訓練)"; }
}
