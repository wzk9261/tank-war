package pers.wengzhengkai.tankwar;

import java.util.Random;

import utils.CollsionUtils;

public class NPCTank extends Picture implements Movable, Hitable, Destroyable, Blockable {
	private int speed = 3;
	private int blood = 5;
	private Direction direction = Direction.UP;
	private Direction badDirection;
	private int seed;
	private long last1;
	private long last2;
	private long last3;
	private boolean isCrashTank;
	
	public NPCTank(int x, int y) {
		super(x, y, Config.NPCTANK_UP);
		// TODO Auto-generated constructor stub
	}

	public Direction getBadDirection() {
		// TODO Auto-generated method stub
		return this.badDirection;
	}
	

	public Direction getDirection() {
		// TODO Auto-generated method stub
		return this.direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void randomizeDirection() {
		int rand = new Random().nextInt(4) + 1;//4
		long now = System.currentTimeMillis();
		
		if (now - this.last1 < 400) {
			return;
		}else {
			this.last1 = now;
		}
		
		switch (rand) {
		case 1:
			this.direction = Direction.UP;
			break;
			
		case 2:
			this.direction = Direction.DOWN;
			break;
			
		case 3:
			this.direction = Direction.LEFT;
			break;
			
		case 4:
			this.direction = Direction.RIGHT;
			break;

		default:
			break;
		}
		
	}
	
	public void move() {
		randomizeDirection();
		
		if (this.badDirection != null) {
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
			
			if (!isCrashTank) {
				return;
			}
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

	public NPCBullet shot() {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		
		if (now - this.last2 < 300) {
			return null;
		} else {
			this.last2 = now;
		}
		
		NPCBullet bullet = new NPCBullet(this);
		return bullet;
	
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		switch (this.direction) {
		case UP:
			drawPic(this.x, this.y, Config.NPCTANK_UP);
			break;
		
		case DOWN:
			drawPic(this.x, this.y, Config.NPCTANK_DOWN);
			break;
			
		case LEFT:
			drawPic(this.x, this.y, Config.NPCTANK_LEFT);
			break;
			
		case RIGHT:
			drawPic(this.x, this.y, Config.NPCTANK_RIGHT);
			break;

		default:
			break;
		}
		
		this.move();
	
	}
	
	public Direction getReverseDirection(Direction direction) {
		switch (direction) {
		case UP:
			return Direction.DOWN;
			
		case DOWN:
			return Direction.UP;
			
		case LEFT:
			return Direction.RIGHT;
			
		case RIGHT:
			return Direction.LEFT;
			
		default:
			return null;
		}
		
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
			
			if (b instanceof NPCTank || b instanceof MyTank) {
				isCrashTank  = true;
				this.seed = 0;
				
			} else {
				isCrashTank = false;
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
		
		// 敌方坦克之间相撞，则掉头；敌方坦克与其他阻挡对象相撞，则将前进方向改变为与相撞方向不同的其他三个随机方向。
		if (isHit) {
			if (b instanceof NPCTank || b instanceof MyTank) {
				this.direction = this.getReverseDirection(this.badDirection);
			} else {
				this.turnAside(this.badDirection);
			}
		}
		
		return isHit;
	
	}
	
	private void turnAside(Direction direction) {
		// TODO Auto-generated method stub
		int rand = new Random().nextInt(2);
		long now = System.currentTimeMillis();
		
		if (now - this.last3 < 400) {
			return;
		} else {
			this.last3 = now;
		}
		
		switch (direction) {
		case UP:
		case DOWN:
			switch (rand) {
			case 0:
				this.direction = Direction.LEFT;
				break;
			case 1:
				this.direction = Direction.RIGHT;
				
			default:
				break;
			}
			break;
			
		case LEFT:
		case RIGHT:
			switch (rand) {
			case 0:
				this.direction = Direction.UP;
				break;
			case 1:
				this.direction = Direction.DOWN;
				
			default:
				break;
			}
			break;

		default:
			break;
		}
	
	}
	
}
