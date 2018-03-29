package pers.wengzhengkai.tankwar;

import utils.CollsionUtils;

public class MyTank2 extends Picture implements Movable, Hitable, Destroyable, Blockable {

	private Direction direction = Direction.UP;
	private Direction badDirection;
	private int speed = 27;
	private int seed;
	private int blood = 30;
	private long last;
	private boolean isWeng;

	public MyTank2(int x, int y, boolean isWeng) {
		super(x, y, Config.TANK_UP);
		this.isWeng = isWeng;
	}
	
	public Direction getDirection() {
		return this.direction;
	}

	public Direction getBadDirection() {
		return this.badDirection;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (isWeng) {
			switch (this.direction) {
			case UP:
				drawPic(this.x, this.y, Config.TANK_UP);
				break;
				
			case DOWN:
				drawPic(this.x, this.y, Config.TANK_DOWN);
				break;
				
			case LEFT:
				drawPic(this.x, this.y, Config.TANK_LEFT);
				break;
				
			case RIGHT:
				drawPic(this.x, this.y, Config.TANK_RIGHT);
				break;
				
			default:
				break;
			}
		} else {
			switch (this.direction) {
			case UP:
				drawPic(this.x, this.y, Config.GF_TANK_UP);
				break;
				
			case DOWN:
				drawPic(this.x, this.y, Config.GF_TANK_DOWN);
				break;
				
			case LEFT:
				drawPic(this.x, this.y, Config.GF_TANK_LEFT);
				break;
				
			case RIGHT:
				drawPic(this.x, this.y, Config.GF_TANK_RIGHT);
				break;
				
			default:
				break;
			}
		}
	}
	
	@Override
	public boolean checkHit(Blockable b) {
		// TODO Auto-generated method stub
		if (b == this) {
			return false;
		}
		
		Picture p = (Picture) b;
		int x1 = p.x;
		int y1 = p.y;
		int w1 = p.width;
		int h1 = p.height;
		
		int x2 = this.x;
		int y2 = this.y;
		int w2 = this.width;
		int h2 = this.height;
		
		switch (this.direction) {
		case UP:
			y2 -= speed;
			break;
			
		case DOWN:
			y2 += speed;
			break;
			
		case LEFT:
			x2 -= speed;
			break;
			
		case RIGHT:
			x2 += speed;
			break;

		default:
			break;
		}
		
		boolean isHit = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2);
		
		if (isHit) {
			this.badDirection = this.direction;
			
			if (b instanceof NPCTank) {
				this.seed = 0;
				
			} else {
				switch (this.badDirection) {
				case UP:
					this.seed = this.y - y1 - h1;
					break;
					
				case DOWN:
					this.seed = y1 - this.y - this.height;
					break;
					
				case LEFT:
					this.seed = this.x - x1 - w1;
					break;
					
				case RIGHT:
					this.seed = x1 - this.x - this.width;
					break;
					
				default:
					break;
					
				}
				
			}
				
		} else {
			this.badDirection = null;
			this.seed = 0;
			
		}
		
		return isHit;
	
	}
	
	@Override
	public Blast showBlast() {
		// TODO Auto-generated method stub
		this.blood--;
		Blast blast = new Blast(this, false);
		return blast;
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return this.blood <= 0;
	}

	@Override
	public Blast showBigBlast() {
		// TODO Auto-generated method stub
		Blast blast = new Blast(this, true);
		return blast;
	}

	public void move(Direction direction) {
		if (this.badDirection != null && this.badDirection == direction) {
			switch (this.badDirection) {
			case UP:
				y -= this.seed;
				break;
				
			case DOWN:
				y += this.seed;
				break;
				
			case LEFT:
				x -= this.seed;
				break;
				
			case RIGHT:
				x += this.seed;
				break;

			default:
				break;
			}
			
			return;
		}
		
		if (this.direction != direction) {
			this.direction = direction;
			return;
		}
			
		switch (this.direction) {
		case UP:
			y -= speed;
			break;

		case DOWN:
			y += speed;
			break;

		case LEFT:
			x -= speed;
			break;

		case RIGHT:
			x += speed;
			break;

		default:
			break;
		}
		
		x = x <= 0? 0: x;
		y = y <= 0? 0: y;
		x = x >= Config.MAP_WIDTH - this.width? Config.MAP_WIDTH - this.width: x;
		y = y >= Config.MAP_HEIGHT - this.height? Config.MAP_HEIGHT - this.height: y;
	}

	public MyBullet2 shot() {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		
		if (now - this.last < 300) {
			return null;
		} else {
			this.last = now;
		}
		
		MyBullet2 bullet = new MyBullet2(this);
		
		return bullet;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
