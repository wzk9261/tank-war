package pers.wengzhengkai.tankwar;

import java.io.IOException;

import utils.DrawUtils;

public abstract class Picture {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Direction direction;
	
	// 初始位置由其他图片决定时使用
	public Picture(String picPath) {
		getPicSize(picPath);
	}
	
	public Picture(int x, int y, String picPath) {
		this.x = x;
		this.y = y;
		
		getPicSize(picPath);	
	}

	protected void getPicSize(String picPath) {
		int[] size;
		try {
			size = DrawUtils.getSize(picPath);
			this.width = size[0];
			this.height = size[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void drawPic(int x, int y, String picPath) {
		try {
			DrawUtils.draw(picPath, x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public abstract void draw();

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
