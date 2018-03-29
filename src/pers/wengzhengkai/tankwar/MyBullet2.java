package pers.wengzhengkai.tankwar;

import utils.CollsionUtils;

public class MyBullet2 extends Picture implements Attackable, Destroyable, Hitable {
	
	private Direction direction;
	private final int speed = 8;
	
	public MyBullet2(MyTank2 myTank) {
		super(Config.BULLET_UP);
		this.direction = myTank.getDirection();
		// TODO Auto-generated constructor stub		
		switch (myTank.getDirection()) {
		case UP:
			this.x = myTank.getX() + (myTank.getWidth() / 2 - this.width / 2);
			this.y = myTank.getY() - this.height / 2;
			break;
			
		case DOWN:
			this.x = myTank.getX() + (myTank.getWidth() / 2 - this.width / 2);
			this.y = myTank.getY() + myTank.getHeight() - this.height / 2;
			break;
			
		case LEFT:
			this.x = myTank.getX() - this.width / 2;
			this.y = myTank.getY() + myTank.getHeight() / 2 - this.height / 2;
			break;
			
		case RIGHT:
			this.x = myTank.getX() + myTank.getWidth() - this.width/2;
			this.y = myTank.getY() + myTank.getHeight() / 2 - this.height / 2;
			break;

		default:
			break;
		
		}
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		switch (this.direction) {
		case UP:
			this.y -= speed;
			super.drawPic(this.x, this.y, Config.BULLET_UP);
			break;
		
		case DOWN:
			this.y += speed;
			super.drawPic(this.x, this.y, Config.BULLET_DOWN);
			break;
			
		case LEFT:
			this.x -= speed;
			super.drawPic(this.x, this.y, Config.BULLET_LEFT);
			break;
			
		case RIGHT:
			this.x += speed;
			super.drawPic(this.x, this.y, Config.BULLET_RIGHT);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return (this.x <= 0 || this.y <= 0 || 
				this.x >= Config.MAP_WIDTH - this.width || this.y >= Config.MAP_HEIGHT - this.height);
	}

	@Override
	public Blast showBigBlast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkHit(Hitable h) {
		// TODO Auto-generated method stub
		if (h instanceof MyTank2 || h instanceof MyBullet2) {
			return false;
		}
		Picture p = (Picture) h;
		return CollsionUtils.isCollsionWithRect(p.x, p.y, p.width, p.height, 
				this.x, this.y, this.width, this.height);

	}
	
	@Override
	public Blast showBlast() {
		// TODO Auto-generated method stub
		Blast blast = new Blast(this, false);
		return blast;
	}

}
