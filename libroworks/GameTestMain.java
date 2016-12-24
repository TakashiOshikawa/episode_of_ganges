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
		gtm.start();//start���\�b�h�̌Ăяo��
	}
	JFrame mainwindow;//�N���X�@mainwindow
	BufferStrategy strategy;//�N���X�@Strategy
	//�@�P�̈ړ��{�^��
	boolean up = false;//��L�[�̏�Ԃ��L������boolean�^�t�B�[���hup ��L�[�͉�����ĂȂ��͂��Ȃ̂ŁAup�ɂ�false�������Ă����܂�
	boolean down = false;//���L�[�̏�Ԃ��L������boolean�^�t�B�[���hdown ��L�[�͉�����ĂȂ��͂��Ȃ̂ŁAdown�ɂ�false�������Ă����܂�
	boolean right = false;//�E�L�[�̏�Ԃ��L������boolean�^�t�B�[���hright �E�L�[�͉�����ĂȂ��͂��Ȃ̂ŁAright�ɂ�false�������Ă����܂�
	boolean left = false;//���L�[�̏�Ԃ��L������boolean�^�t�B�[���hleft ���L�[�͉�����ĂȂ��͂��Ȃ̂ŁAleft�ɂ�false�������Ă����܂�
	
	//�@�Q�̈ړ��{�^��
	boolean up2 = false;
	boolean down2 = false;
	boolean right2 = false;
	boolean left2 = false;
	
	//�@�P�̍��W
	double cy = 600;//Y���W(�c�ʒu�j���L�^����int�^�t�B�[���hcy 500
	double cx = 360;//X���W�i���ʒu�j���L�^����int�^�t�B�[���hcx 260
	
	//�@�Q�̍��W
	double cy2 = 115; //15
	double cx2 = 355; //255
	
	//�@�P�̒e�P
	boolean tama=false;
	double indox=cx,indoy=cy;
	boolean india=false;
	
	//�@�Q�̒e�Q
	boolean tama2 = false;
	double indox2=cx2,indoy2=cy2;
	boolean india2 = false;
	
	//�摜������Ƃ���
	BufferedImage bimage = null,hanai,indo,indo2,bimage2,kill,kill2;
	
	
	public Point2D.Double position = new Point2D.Double(100,100);//�L�����N�^�[�̈ʒu���L�^����Point2D.Double�^�̃t�B�[���h
	public Point2D.Double center = new Point2D.Double();//�L�����N�^�[�̒��S�ʒu���L�^����Point2D.Double�^�̃t�B�[���h
	//public double angle = 0.0;//�摜�̊p�x���L�^����double�^�t�B�[���h
	public boolean visible = true;//�\���A��\�����L�^����double�^�t�B�[���h
	public double size;//�T�C�Y���L�^����double�^�t�B�[���h�@�����ƕ��̋�ʂ͖���
	//BufferedImage bimage;
	//�R���X�g���N�^
	GameTestMain(){
		this.mainwindow = new JFrame("�V���[�e�B���O");//�C���X�^���X�쐬�@�E�B���h�E�^�C�g��
		this.mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����ۂ̏���
		this.mainwindow.setSize(800,720);//��ʃT�C�Y
		this.mainwindow.setLocationRelativeTo(null);//�E�B���h�E���f�X�N�g�b�v�����ɔz�u����
		this.mainwindow.setVisible(true);//�E�B���h�E�\��
		this.mainwindow.setResizable(false);//�T�C�Y�ύX�s��
		//�o�b�t�@�X�g���e�W�[
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.strategy = this.mainwindow.getBufferStrategy();
		//�L�[�A�_�v�^�[
		this.mainwindow.addKeyListener(new MyKeyAdapter());
		//�ǂݍ���
		try {
			this.bimage = ImageIO.read(new File("test-chara.png"));
			this.hanai = ImageIO.read(new File("utyu.jpg"));
			this.indo = ImageIO.read(new File("tama1.png"));
			this.bimage2 = ImageIO.read(new File("test-chara2.png"));
			this.indo2 = ImageIO.read(new File("tama2.png"));
			this.kill = ImageIO.read(new File("test-chara-damage.png"));
			this.kill2 = ImageIO.read(new File("test-chara2-damage.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainwindow,"�摜�t�@�C�����ǂݍ��߂܂���");
		}
	}
	void start(){//start���\�b�h�̒�`
		Timer t = new Timer();//Timer�N���X�̃C���X�^���X
		t.schedule(new RenderTask(),0,16);// schedule(TimerTask�C���X�^���X,�Ăяo���J�n�~���b,�Ăяo���Ԋu�~���b�@�@�@�@�@�@
	}
	void render(){//Render���\�b�h
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();//getDrawGraphics���\�b�h��Graphics�C���X�^���X���擾
		//g.setBackground(Color.BLACK);//�w�i�F�̐ݒ�
		g.clearRect(0,0,this.mainwindow.getWidth(),this.mainwindow.getHeight());//�E�B���h�E�S�̂��N���A
		//�@�P�̈ړ�
		if(up == true){//��L�[��������Ă���Ƃ���
			cy -= 5;//cy�i�c�j����5����
		}
		else{//��L�[��������Ă��Ȃ��Ƃ���
			cy += 0;//cy�i�c�j��5�𑫂�
		}
		if(down == true){//���L�[��������Ă���Ƃ���
			cy += 5;//cy�i�c�j����5����
		}
		else{//���L�[��������Ă��Ȃ��Ƃ���
			cy -= 0;//cy�i�c�j��5�𑫂�
		}
		if(right == true){//�E�L�[��������Ă���Ƃ���
			cx += 5;//cx�i���j����5����
		}
		else{//��L�[��������Ă��Ȃ��Ƃ���
			cx -= 0;//cx�i���j��5�𑫂�
		}
		if(left == true){//���L�[��������Ă���Ƃ���
			cx -= 5;//cx�i���j����5����
		}
		else{//���L�[��������Ă��Ȃ��Ƃ���
			cx += 0;//cx�i���j��5�𑫂�
		}
		
		//�@�Q�̈ړ�
		if(up2 == true){//��L�[��������Ă���Ƃ���
			cy2 -= 5;//cy�i�c�j����5����
		}
		else{//��L�[��������Ă��Ȃ��Ƃ���
			cy2 += 0;//cy�i�c�j��5�𑫂�
		}
		if(down2 == true){//���L�[��������Ă���Ƃ���
			cy2 += 5;//cy�i�c�j����5����
		}
		else{//���L�[��������Ă��Ȃ��Ƃ���
			cy2 -= 0;//cy�i�c�j��5�𑫂�
		}
		if(right2 == true){//�E�L�[��������Ă���Ƃ���
			cx2 += 5;//cx�i���j����5����
		}
		else{//��L�[��������Ă��Ȃ��Ƃ���
			cx2 -= 0;//cx�i���j��5�𑫂�
		}
		if(left2 == true){//���L�[��������Ă���Ƃ���
			cx2 -= 5;//cx�i���j����5����
		}
		else{//���L�[��������Ă��Ȃ��Ƃ���
			cx2 += 0;//cx�i���j��5�𑫂�
		}
		
		//this.center.x = this.bimage.getWidth()/2;//�摜�̃T�C�Y���璆�S�ʒu�����߂�i���T�C�Y�j
		//this.center.y = this.bimage.getHeight()/2;//�摜�̃T�C�Y���璆�S�ʒu�����߂�i�c�T�C�Y�j
		//AffineTransform oldtr = g.getTransform();//�ό`�ݒ�̑ҋ@�@���݂̕ό`�ҋ@���擾����ɂ�Graphics2D�N���X��getTransform���\�b�h�𗘗p����
		//g.translate(this.position.x,this.position.y);//�摜�̕`��̈ʒu��translate���\�b�h�Ŏw�肷��K�v������܂�
		//g.rotate(this.angle / 180.0 * Math.PI,0,0);//�摜����]����ɂ�Graphics2D�N���X��rotate���\�b�h�𗘗p����
		//g.drawImage(this.bimage,-(int)this.center.x,-(int)this.center.y,null);//drawImage���\�b�h�ŉ摜��`�悷��@�摜�̈ʒu��tarnslate���\�b�h
		//�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�Ŏw�肵�܂����A�����ł�center�t�B�[���h�ɋL�^�������S�ʒu�̕������摜�����炵�Ă��܂�
		g.drawImage(this.hanai,0,0,null);//�w�i�摜 -100,-100
		
		//�@�P�̒e
		if(tama==true&&india==true){
			indox=cx;
			indoy=cy;
		}
		if(india==true)indoy=indoy-20;//�e���̐ݒ�A����
		if(indoy<-50)india=false;//100��艺�ɂȂ����� 100
		if(india==true){
			g.drawImage(this.indo,(int)this.indox,(int)this.indoy,null);
		}
		
		//�@�Q�̒e
		if(tama2==true&&india2==true){
			indox2=cx2;
			indoy2=cy2;
		}
		if(india2==true)indoy2=indoy2+20;//�e���̐ݒ�A����
		if(indoy2>+710)india2=false;//100��艺�ɂȂ����� 610
		if(india2==true){
			g.drawImage(this.indo2,(int)this.indox2,(int)this.indoy2,null);
		}
		
		//�@�P�̈ړ��͈�
		//��ɂ����Ȃ�
		if(cy<380)cy+=5;//+5�͈ړ����ɑ�����+-�O�ɂ��� 280
		//���ɂ����Ȃ�
		if(cy>672)cy-=5; //572
		//���ɂ����Ȃ�
		if(cx<5)cx+=5; //-95
		//�E�ɂ����Ȃ�
		if(cx>750)cx-=5;// 650
		
		//�@�Q�̈ړ��͈�
		//��ɂ����Ȃ�
		if(cy2>20)cy2-=5;// -80
		//���ɂ����Ȃ�
		if(cy2<330)cy2+=5;// 230
		//���ɂ����Ȃ�
		if(cx2<5)cx2+=5;// -95
		//�E�ɂ����Ȃ�
		if(cx2>750)cx2-=5;// 650
		
		//�����蔻��
		//�@�P�̓����蔻��
		if((cy+44 > indoy2+8) && (indoy2+42 > cy+4) && (cx+44 > indox2+8) && (indox2+42 > cx+4)){
			this.bimage = this.kill;
			india2=false;
		}
		
		//�@�Q�̓����蔻��
		if((cy2+44 > indoy+8) && (indoy+42 > cy2+4) && (cx2+44 > indox+8) && (indox+42 > cx2+4)){
			this.bimage2 = this.kill2;
			india=false;
		}
		
		//�e���m�����������Ƃ��̏���
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
		
		
		
		//�@�P�̕\��
		g.drawImage(this.bimage,(int)this.cx,(int)this.cy,null);//�摜�\�� �ϐ�.drawImage(this.�ϐ�,(int)this.X���W+���l,(int)this.Y���W+���l,null);�@�@
		g.drawImage(this.bimage2,(int)this.cx2,(int)this.cy2,null);
		
		
		this.strategy.show();
	}
	class RenderTask extends TimerTask{
		int count = 0;//count�t�B�[���h�̐錾�@������
		
		@Override
		public void run() {
			GameTestMain.this.render();//RenderTask�N���X��run���\�b�h����render���\�b�h���Ăяo��
		}
	}
	class MyKeyAdapter extends KeyAdapter{//�{�^�������Ƃ��̃N���X

		@Override
		public void keyPressed(KeyEvent e) {//�L�[���������Ƃ��̏���
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD5){//5�L�[���������Ƃ��̏���
				GameTestMain.this.up = true;//5�L�[���`�F�b�N���Č���up�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_UP){//��L�[���������Ƃ��̏���
				GameTestMain.this.tama = true;//��L�[���`�F�b�N���Č���tama�ɋL�����鏈��
				GameTestMain.this.india = true;//��L�[���`�F�b�N����india�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD2){//2���������Ƃ��̏���
				GameTestMain.this.down = true;//2�L�[���`�F�b�N���Č���down�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD3){//3���������Ƃ��̏���
				GameTestMain.this.right = true;//3�L�[���`�F�b�N���Č���right�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD1){//1���������Ƃ��̏���
				GameTestMain.this.left = true;//1�L�[���`�F�b�N���Č���left�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_H){//5�L�[���������Ƃ��̏���
				GameTestMain.this.up2 = true;//5�L�[���`�F�b�N���Č���up�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_J){//5�L�[���������Ƃ��̏���
				GameTestMain.this.up2 = true;//5�L�[���`�F�b�N���Č���up�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_Z){//��L�[���������Ƃ��̏���
				GameTestMain.this.tama2 = true;//��L�[���`�F�b�N���Č���tama�ɋL�����鏈��
				GameTestMain.this.india2 = true;//��L�[���`�F�b�N����india�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_N){//2���������Ƃ��̏���
				GameTestMain.this.down2 = true;//2�L�[���`�F�b�N���Č���down�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_M){//3���������Ƃ��̏���
				GameTestMain.this.right2 = true;//3�L�[���`�F�b�N���Č���right�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_B){//1���������Ƃ��̏���
				GameTestMain.this.left2 = true;//1�L�[���`�F�b�N���Č���left�ɋL�����鏈��
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {//�L�[�𗣂����Ƃ��̏���
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD5){//5�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.up = false;//5�L�[���`�F�b�N���Č���up�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD2){//2�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.down = false;//2�L�[���`�F�b�N���Č���down�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD3){//3�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.right = false;//3�L�[���`�F�b�N���Č���right�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_NUMPAD1){//1�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.left = false;//1�L�[���`�F�b�N���Č���left�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_UP){//��L�[���������Ƃ��̏���
				GameTestMain.this.tama = false;//��L�[���`�F�b�N���Č���tama�ɋL�����鏈��
				GameTestMain.this.india = true;//��L�[���`�F�b�N����india�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_H){//5�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.up2 = false;//5�L�[���`�F�b�N���Č���up�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_J){//5�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.up2 = false;//5�L�[���`�F�b�N���Č���up�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_N){//2�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.down2 = false;//2�L�[���`�F�b�N���Č���down�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_M){//3�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.right2 = false;//3�L�[���`�F�b�N���Č���right�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_B){//1�L�[�������ꂽ�Ƃ��̏���
				GameTestMain.this.left2 = false;//1�L�[���`�F�b�N���Č���left�ɋL�����鏈��
			}
			if(e.getKeyCode()==KeyEvent.VK_Z){//��L�[���������Ƃ��̏���
				GameTestMain.this.tama2 = false;//��L�[���`�F�b�N���Č���tama�ɋL�����鏈��
				GameTestMain.this.india2 = true;//��L�[���`�F�b�N����india�ɋL�����鏈��
			}
		}
	}
}








