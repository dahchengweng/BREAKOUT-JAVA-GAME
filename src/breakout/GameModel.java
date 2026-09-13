package breakout;

public class GameModel {
    private int width = 800;
    private int height = 600;
    
    private Ball ball;
    private int paddleX = 350;
    private int paddleY = 530;
    private final int BASE_PADDLE_WIDTH = 100;
    private int paddleHeight = 15;
    private int paddleSpeed = 8;

    private Brick[][] bricks;
    private final int ROWS = 5;
    private final int COLS = 8;

    private PaddleStrategy currentStrategy = new NormalPaddle(); // 預設策略
    private int score = 0;
    private int lives = 3;
    private boolean isGameOver = false;

    private int countdownTime = 3;
    private int frameCount = 0;

    private boolean isCountingDown = true;

    private boolean isGameWon = false;          // ✨ 新增：紀錄是否過關
    private boolean hasPlayedWonSound = false;  // ✨ 新增：紀錄過關音效是否播過

    public GameModel() {
        resetBall();
        initBricks();
    }

    private void initBricks() {
        bricks = new Brick[ROWS][COLS];
        int bWidth = width / COLS;
        int bHeight = 30;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // 隨機讓 15% 的磚塊變成道具磚塊
                int type = (Math.random() < 0.15) ? 1 : 0;
                bricks[r][c] = new Brick(c * bWidth, r * bHeight + 50, bWidth, bHeight, type);
            }
        }
    }

    public void resetBall() {
        ball = new Ball(400, 300, 3, -4);
    }

    private void updateCountdown(){
        frameCount++;
        if(frameCount ==1 && countdownTime ==3){
            SoundManager.playSound("countdown");
        }

        if(frameCount>=100){
            countdownTime--;
            frameCount = 0;
        }
        if(countdownTime ==0){
            SoundManager.playSound("start");
            SoundManager.playBGM();
            isCountingDown = false;
        }
    }

    public void updateGame() {
        if (isGameOver || isGameWon) return;
        if (isCountingDown) {
            updateCountdown();
            return;
        }

        ball.update();
        ball.checkWallCollisions(width);

        // 1. 檢查與球板的碰撞
                // 1. 檢查與球板的碰撞 (請替換 GameModel.java 內的此區塊)
                // 1. 檢查與球板的碰撞 (安全防穿透版本)
        int curWidth = currentStrategy.getWidth(BASE_PADDLE_WIDTH);
        
        // 核心修正：將原本的 == 判斷，改為檢查球的「下邊緣」是否穿過板子的頂部
        // 同時限制球必須是「向下運動 (vy > 0)」才觸發，避免球在反彈向上時重複觸發
        if (ball.getVy() > 0 && 
            ball.getY() + ball.getSize() >= paddleY && 
            ball.getY() <= paddleY + paddleHeight) {
            
            // 檢查 X 軸是否在球板範圍內
            if (ball.getX() + ball.getSize() >= paddleX && ball.getX() <= paddleX + curWidth) {
                
                // 🔊 播放撞擊音效
                SoundManager.playSound("hit");
                
                // ✨ 關鍵防穿透步驟：將球的 Y 座標強制重設回「球板頂部緊貼處」
                // 這樣下一幀物理更新時，球才不會被誤判為已經穿過板子
                ball.setY(paddleY - ball.getSize());
                
                // 讓球反彈向上 (Y軸速度變負數)
                ball.reverseY(); 
                
                // 計算球打在球板的相對位置 (-0.5 到 +0.5)
                double relativeHitPoint = ((ball.getX() + ball.getSize() / 2.0) - (paddleX + curWidth / 2.0)) / curWidth;
                
                // 根據相對位置給予 X 軸新速度
                ball.setVx(relativeHitPoint * 7); 
            }

        }



        // 2. 檢查與磚塊的碰撞
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bricks[r][c].checkCollision(ball)) {
                    ball.reverseY();
                    score += 10;
                    SoundManager.playSound("hit");
                    // ♿ 復健導向機制：打破道具磚塊時，動態改變球板策略
                    if (bricks[r][c].getType() == 1) {
                        currentStrategy = new EnlargedPaddle(); // 給予復健患者回饋輔助
                        
                    }
                }
            }
        }

        // 3. 檢查是否漏接 (掉出底面)
        if (ball.getY() > height) {
            lives--;
            // ♿ DDA 動態難度適應：如果掉球，強制切換成「放大策略」減輕患者挫折感
            currentStrategy = new EnlargedPaddle();
            
            if (lives <= 0) {
                isGameOver = true;
                SoundManager.playSound("gameover");
            } else {
                resetBall();
            }
        }
        if (checkAllBricksDestroyed()) {
            isGameWon = true;
            SoundManager.stopBGM(); // 🔊 關閉背景音樂
            if (!hasPlayedWonSound) {
                SoundManager.playSound("gameover"); // 🔊 播放過關勝利音效
                hasPlayedWonSound = true;
            }
        }
    }
    private boolean checkAllBricksDestroyed() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // 只要還有一個磚塊沒被摧毀，就代表還沒過關
                if (!bricks[r][c].isDestroyed()) {
                    return false; 
                }
            }
        }
        return true; // 走到這裡代表全部都被摧毀了
    }
    public int getCountdownTime(){return countdownTime;}

    public boolean isCountingDown(){return isCountingDown;}

    public void movePaddleLeft() {
        paddleX -= paddleSpeed;
        if (paddleX < 0) paddleX = 0;
    }

    public void movePaddleRight() {
        int curWidth = currentStrategy.getWidth(BASE_PADDLE_WIDTH);
        paddleX += paddleSpeed;
        if (paddleX > width - curWidth) paddleX = width - curWidth;
    }

    // Getters
    public Ball getBall() { return ball; }
    public Brick[][] getBricks() { return bricks; }
    public int getPaddleX() { return paddleX; }
    public int getPaddleY() { return paddleY; }
    public int getPaddleWidth() { return currentStrategy.getWidth(BASE_PADDLE_WIDTH); }
    public int getPaddleHeight() { return paddleHeight; }
    public PaddleStrategy getCurrentStrategy() { return currentStrategy; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return isGameOver; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
