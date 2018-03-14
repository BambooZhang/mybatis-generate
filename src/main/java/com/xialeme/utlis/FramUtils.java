package com.xialeme.utlis;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FramUtils {

	
	
	public static void main(String[] args) throws AWTException, InterruptedException {
		JFrame jframe=new JFrame("屏幕监控");
		
		jframe.setSize(600, 600);
		jframe.setVisible(true);
		jframe.setAlwaysOnTop(true);
		
		 //创建Toolkit对象  
		Toolkit t=Toolkit.getDefaultToolkit();  
		//获取当前屏幕的尺寸  
		Dimension d=t.getScreenSize();  
		double width=d.getWidth();//获取屏幕长  
		double height=d.getHeight();//获取屏幕宽  
//		int x=(int)(width-300)/2;
//		int y=(int)(height-400)/2;
//		//设置窗体位置  
		//jframe.setLocation(x, y);  
		
		
		
		//创建屏幕显示区域
		JLabel imgLabel=new JLabel();
		jframe.add(imgLabel);
		
		
		//创建机器人
		Robot robot=new Robot();
		
		
		for (int i = 0; i < 1000; i++) {
			//指定坐标空间区域
			Rectangle rec=new  Rectangle(jframe.getWidth(),0,(int)d.getWidth()-jframe.getWidth(),(int)d.getHeight());
			BufferedImage  bufImg=robot.createScreenCapture(rec);
			
			//显示屏幕到图像中
			
			imgLabel.setIcon(new ImageIcon(bufImg));
			
			//Thread.sleep(50);
		}
		
		
		
		
		
	}
	
	
	
}
