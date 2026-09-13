# 物件導向 霓虹打磚塊遊戲 (Breakout Strategy MVC)

這是一個專為大學資訊工程/軟體工程系（大三）學生設計的 Java 2D 遊戲教學範例。本專案以經典街機遊戲「打磚塊」為原型，採用 **MVC 架構**，融入高對比度的**黑夜霓虹視覺風格**，並透過**策略模式 (Strategy Pattern)** 實作動態復健輔助機制。

## 🎯 專案特色與復健導向設計 (Rehabilitation Focus)
* **黑夜霓虹高對比視覺**：將背景改為純黑，搭配高飽和度的青色與橘紅色磚塊，提升低視力或高齡患者的視覺專注力與追蹤能力。
* **認知開局預判 (Anticipation Time)**：內建啟動倒數 3、2、1 機制與音效提示，給予患者充足的心理準備時間，專注於即時開局。
* **動態難度適應 (DDA) 輔助**：當系統偵測到玩家漏接掉球時，會主動透過策略模式強制切換為「放大球板」，減輕患者失誤的挫折感。

## 🛠️ 物件導向程式設計 (OOP) 與物理引擎教學重點
本專案的程式碼結構清晰，旨在向學生演示以下軟體工程與物理引擎觀念：
1. **策略模式 (Strategy Pattern) 解耦道具**：
   * 建立 `PaddleStrategy` 介面，衍生出正常、放大、縮小等策略類別。
   * 球板物件（Paddle）與 View 渲染完全不需知道當前吃到什麼道具，皆由策略物件動態決定寬度與顏色，符合**開放封閉原則 (OCP)**。
2. **防穿隧現象 (Tunneling Bug Fix)**：
   * 向學生演示遊戲開發中的經典穿透問題。本專案將「面碰撞」升級為「區間攔截」，並在碰撞瞬間實作**座標強制修正 (Position Snap)**，徹底解決高速球穿透球板的物理瑕疵。
3. **音效快取池 (Audio Preloading)**：
   * 延續 `SoundManager` 快取機制，在遊戲初始化的靜態階段預載所有 `.wav` 檔案，確保高頻反彈擊碎磚塊時達到 **0ms 延遲** 聽覺回饋。

## 📂 專案目錄結構
```text
.
├── sounds/                # 音效資源資料夾 (請確保使用 .wav 格式)
│   ├── bgm.wav            # 背景音樂 (無限循環)
│   ├── countdown.wav      # 開局 3, 2, 1 滴答聲
│   ├── start.wav          # 正式開始提示音
│   ├── slice.wav          # 磚塊/球板反彈撞擊聲 (hit)
│   └── won.wav            # 關卡全消勝利音效
├── src/
│   └── breakout/
│       ├── PaddleStrategy.java # 核心設計模式：策略介面與子策略群
│       ├── Ball.java           # 球體物理物件 (包含防穿透與動態反射 Setter)
│       ├── Brick.java          # 霓虹磚塊物件 (包含矩形碰撞偵測)
│       ├── GameModel.java      # 遊戲邏輯、DDA 難度控制與全消檢查 (Model)
│       ├── GameView.java       # 黑化霓虹畫面渲染與 UI 置中 (View)
│       ├── GameController.java # 平滑按鍵監聽與遊戲迴圈 (Controller)
│       └── SoundManager.java   # 音效池管理器 (Utility)
├── .gitignore             # Git 忽略檔案清單
├── LICENSE                # MIT 開源授權條款
└── README.md              # 本說明文件
```

## 🚀 快速開始 (VS Code)
1. **複製儲存庫**：
   ```bash
   git clone https://github.com](https://github.com/dahchengweng/BREAKOUT-JAVA-GAME
   ```
2. **放置音效檔案**：
   請將準備好的 `.wav` 音效檔案放入根目錄下的 `sounds/` 資料夾中，確認命名與代號相符。
3. **編譯並執行**：
   在 VS Code 中開啟此專案資料夾，點擊 `GameController.java`，按下 `F5` 啟動，即可開始享受霓虹風格的打磚塊復健遊戲！
