package jp.libroworks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameTestMain {

	public static void main(String[] args) {
		GameTestMain gtm = new GameTestMain();
		gtm.start();//startメソッドの呼び出し
	}
	JFrame mainwindow;//クラス　mainwindow
	BufferStrategy strategy;//クラス　Strategy
	//機１の移動ボタン
	boolean up = false;//上キーの状態を記憶するboolean型フィールドup 上キーは押されてないはずなので、upにはfalseを代入しておきます
	boolean down = false;//下キーの状態を記憶するboolean型フィールドdown 上キーは押されてないはずなので、downにはfalseを代入しておきます
	boolean right = false;//右キーの状態を記憶するboolean型フィールドright 右キーは押されてないはずなので、rightにはfalseを代入しておきます
	boolean left = false;//左キーの状態を記憶するboolean型フィールドleft 左キーは押されてないはずなので、leftにはfalseを代入しておきます
	
	//機２の移動ボタン
	boolean up2 = false;
	boolean down2 = false;
	boolean right2 = false;
	boolean left2 = false;
	
	//機１の座標
	double cy = 600;//Y座標(縦位置）を記録するint型フィールドcy 500
	double cx = 360;//X座標（横位置）を記録するint型フィールドcx 260
	
	//機２の座標
	double cy2 = 115; //15
	double cx2 = 355; //255
	
	//機１の弾１
	boolean tama=false;
	double indox=cx,indoy=cy;
	boolean india=false;
	
	//機２の弾２
	boolean tama2 = false;
	double indox2=cx2,indoy2=cy2;
	boolean india2 = false;
	
	//画像を入れるところ
	BufferedImage bimage = null,hanai,indo,indo2,bimage2,kill,kill2;
	
	
	public Point2D.Double position = new Point2D.Double(100,100);//キャラクターの位置を記録するPoint2D.Double型のフィールド
	public Point2D.Double center = new Point2D.Double();//キャラクターの中心位置を記録するPoint2D.Double型のフィールド
	//public double angle = 0.0;//画像の角度を記録するdouble型フィールド
	public boolean visible = true;//表示、非表示を記録するdouble型フィールド
	public double size;//サイズを記録するdouble型フィールド　高さと幅の区別は無い
	//BufferedImage bimage;
	//コンストラクタ
	GameTestMain(){
		this.mainwindow = new JFrame("シューティング");//インスタンス作成　ウィンドウタイトル
		this.mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//閉じる際の処理
		this.mainwindow.setSize(800,720);//画面サイズ
		this.mainwindow.setLocationRelativeTo(null);//ウィンドウをデスクトップ中央に配置する
		this.mainwindow.setVisible(true);//ウィンドウ表示
		this.mainwindow.setResizable(false);//サイズ変更不可
		//バッファストラテジー
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.strategy = this.mainwindow.getBufferStrategy();
		//キーアダプター
		this.mainwindow.addKeyListener(new MyKeyAdapter());
		//読み込み
		try {
			this.bimage = ImageIO.read(new File("test-chara.png"));
			this.hanai = ImageIO.read(new File("utyu.jpg"));
			this.indo = ImageIO.read(new File("tama1.png"));
			this.bimage2 = ImageIO.read(new File("test-chara2.png"));
			this.indo2 = ImageIO.read(new File("tama2.png"));
			this.kill = ImageIO.read(new File("test-chara-damage.png"));
			this.kill2 = ImageIO.read(new File("test-chara2-damage.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainwindow,"画像ファイルが読み込めません");
		}
	}
	void start(){//startメソッドの定義
		Timer t = new Timer();//Timerクラスのインスタンス
		t.schedule(new RenderTask(),0,16);// schedule(TimerTaskインスタンス,呼び出し開始ミリ秒,呼び出し間隔ミリ秒　　　　　　
	}
	void render(){//Renderメソッド
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();//getDrawGraphicsメソッドでGraphicsインスタンスを取得
		//g.setBackground(Color.BLACK);//背景色の設定
		g.clearRect(0,0,this.mainwindow.getWidth(),this.mainwindow.getHeight());//ウィンドウ全体をクリア
		//機１の移動
		if(up == true){//上キーが押されているときに
			cy -= 5;//cy（縦）から5引く
		}
		else{//上キーが押されていないときに
			cy += 0;//cy（縦）に5を足す
		}
		if(down == true){//下キーが押されているときに
			cy += 5;//cy（縦）から5引く
		}
		else{//下キーが押されていないときに
			cy -= 0;//cy（縦）に5を足す
		}
		if(right == true){//右キーが押されているときに
			cx += 5;//cx（横）から5引く
		}
		else{//上キーが押されていないときに
			cx -= 0;//cx（横）に5を足す
		}
		if(left == true){//下キーが押されているときに
			cx -= 5;//cx（横）から5引く
		}
		else{//下キーが押されていないときに
			cx += 0;//cx（横）に5を足す
		}
		
		//機２の移動
		if(up2 == true){//上キーが押されているときに
			cy2 -= 5;//cy（縦）から5引く
		}
		else{//上キーが押されていないときに
			cy2 += 0;//cy（縦）に5を足す
		}
		if(down2 == true){//下キーが押されているときに
			cy2 += 5;//cy（縦）から5引く
		}
		else{//下キーが押されていないときに
			cy2 -= 0;//cy（縦）に5を足す
		}
		if(right2 == true){//右キーが押されているときに
			cx2 += 5;//cx（横）から5引く
		}
		else{//上キーが押されていないときに
			cx2 -= 0;//cx（横）に5を足す
		}
		if(left2 == true){//下キーが押されているときに
			cx2 -= 5;//cx（横）から5引く
		}
		else{//下キーが押されていないときに
			cx2 += 0;//cx（横）に5を足す
		}
		
		//this.center.x = this.bimage.getWidth()/2;//画像のサイズから中心位置を求める（横サイズ）
		//this.center.y = this.bimage.getHeight()/2;//画像のサイズから中心位置を求める（縦サイズ）
		//AffineTransform oldtr = g.getTransform();//変形設定の待機　現在の変形待機を取得するにはGraphics2DクラスのgetTransformメソッドを利用する
		//g.translate(this.position.x,this.position.y);//画像の描画の位置はtranslateメソッドで指定する必要があります
		//g.rotate(this.angle / 180.0 * Math.PI,0,0);//画像を回転するにはGraphics2Dクラスのrotateメソッドを利用する
		//g.drawImage(this.bimage,-(int)this.center.x,-(int)this.center.y,null);//drawImageメソッドで画像を描画する　画像の位置はtarnslateメソッド
		//　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　で指定しますが、ここではcenterフィールドに記録した中心位置の分だけ画像をずらしています
		g.drawImage(this.hanai,0,0,null);//背景画像 -100,-100
		
		//機１の弾
		if(tama==true&&india==true){
			indox=cx;
			indoy=cy;
		}
		if(india==true)indoy=indoy-20;//弾速の設定、向き
		if(indoy<-50)india=false;//100より下になったら 100
		if(india==true){
			g.drawImage(this.indo,(int)this.indox,(int)this.indoy,null);
		}
		
		//機２の弾
		if(tama2==true&&india2==true){
			indox2=cx2;
			indoy2=cy2;
		}
		if(india2==true)indoy2=indoy2+20;//弾速の設定、向き
		if(indoy2>+710)india2=false;//100より下になったら 610
		if(india2==true){
			g.drawImage(this.indo2,(int)this.indox2,(int)this.indoy2,null);
		}
		
		//機１の移動範囲
		//上にいけない
		if(cy<380)cy+=5;//+5は移動分に足して+-０にする 280
		//下にいけない
		if(cy>672)cy-=5; //572
		//左にいけない
		if(cx<5)cx+=5; //-95
		//右にいけない
		if(cx>750)cx-=5;// 650
		
		//機２の移動範囲
		//上にいけない
		if(cy2>20)cy2-=5;// -80
		//下にいけない
		if(cy2<330)cy2+=5;// 230
		//左にいけない
		if(cx2<5)cx2+=5;// -95
		//右にいけない
		if(cx2>750)cx2-=5;// 650
		
		//当たり判定
		//機１の当たり判定
		if((cy+44 > indoy2+8) && (indoy2+42 > cy+4) && (cx+44 > indox2+8) && (indox2+42 > cx+4)){
			this.bimage = this.kill;
			india2=false;
		}
		
		//機２の当たり判定
		if((cy2+44 > indoy+8) && (indoy+42 > cy2+4) && (cx2+44 > indox+8) && (indox+42 > cx2+4)){
			this.bimage2 = this.kill2;
			india=false;
		}
		
		//弾同士が当たったときの処理
		if((indox+44 > indox2+6) && (indox2+44 > indox+6) && (indoy+44 > indoy2+6) && (indoy2+44 > indoy+6)){
			india=false;
			india2=false;
			tama=false;
			tama2=false;
			indox=cx;
			indoy=cy;
			indox2=cx2;
			indoy2=cy2;
		}
		
		
		
		//機１の表示
		g.drawImage(this.bimage,(int)this.cx,(int)this.cy,null);//画像表示 変数.drawImage(this.変数,(int)this.X座標+数値,(int)this.Y座標+数値,null);　　
		g.drawImage(this.bimage2,(int)this.cx2,(int)this.cy2,null);
		
		
		this.strategy.show();
	}
	class RenderTask extends TimerTask{
		int count = 0;//countフィールドの宣言　初期化
		
		@Override
		public void run() {
			GameTestMain.this.render();//RenderTaskクラスのrunメソッドからrenderメソッドを呼び出す
		}
	}
	class MyKeyAdapter extends KeyAdapter{//ボタン押すときのクラス

		@Override
		public void keyPressed(KeyEvent e) {//キーを押したときの処理
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD5){//5キーを押したときの処理
				GameTestMain.this.up = true;//5キーをチェックして結果upに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_UP){//上キーを押したときの処理
				GameTestMain.this.tama = true;//上キーをチェックして結果tamaに記憶する処理
				GameTestMain.this.india = true;//上キーをチェックしてindiaに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD2){//2を押したときの処理
				GameTestMain.this.down = true;//2キーをチェックして結果downに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD3){//3を押したときの処理
				GameTestMain.this.right = true;//3キーをチェックして結果rightに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD1){//1を押したときの処理
				GameTestMain.this.left = true;//1キーをチェックして結果leftに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_H){//5キーを押したときの処理
				GameTestMain.this.up2 = true;//5キーをチェックして結果upに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_J){//5キーを押したときの処理
				GameTestMain.this.up2 = true;//5キーをチェックして結果upに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_Z){//上キーを押したときの処理
				GameTestMain.this.tama2 = true;//上キーをチェックして結果tamaに記憶する処理
				GameTestMain.this.india2 = true;//上キーをチェックしてindiaに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_N){//2を押したときの処理
				GameTestMain.this.down2 = true;//2キーをチェックして結果downに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_M){//3を押したときの処理
				GameTestMain.this.right2 = true;//3キーをチェックして結果rightに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_B){//1を押したときの処理
				GameTestMain.this.left2 = true;//1キーをチェックして結果leftに記憶する処理
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {//キーを離したときの処理
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD5){//5キーが離されたときの処理
				GameTestMain.this.up = false;//5キーをチェックして結果upに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD2){//2キーが離されたときの処理
				GameTestMain.this.down = false;//2キーをチェックして結果downに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD3){//3キーが離されたときの処理
				GameTestMain.this.right = false;//3キーをチェックして結果rightに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD1){//1キーが離されたときの処理
				GameTestMain.this.left = false;//1キーをチェックして結果leftに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_UP){//上キーを押したときの処理
				GameTestMain.this.tama = false;//上キーをチェックして結果tamaに記憶する処理
				GameTestMain.this.india = true;//上キーをチェックしてindiaに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_H){//5キーが離されたときの処理
				GameTestMain.this.up2 = false;//5キーをチェックして結果upに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_J){//5キーが離されたときの処理
				GameTestMain.this.up2 = false;//5キーをチェックして結果upに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_N){//2キーが離されたときの処理
				GameTestMain.this.down2 = false;//2キーをチェックして結果downに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_M){//3キーが離されたときの処理
				GameTestMain.this.right2 = false;//3キーをチェックして結果rightに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_B){//1キーが離されたときの処理
				GameTestMain.this.left2 = false;//1キーをチェックして結果leftに記憶する処理
			}
			if(e.getKeyCode()==KeyEvent.VK_Z){//上キーを押したときの処理
				GameTestMain.this.tama2 = false;//上キーをチェックして結果tamaに記憶する処理
				GameTestMain.this.india2 = true;//上キーをチェックしてindiaに記憶する処理
			}
		}
	}
}








