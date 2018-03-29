package pers.wengzhengkai.tankwar;

import utils.CollsionUtils;

public class NPCBullet extends Picture implements Attackable, Destroyable, Hitable {
	
	private Direction direction;
	private int speed = 8;
	
	public NPCBullet(NPCTank npcTank) {
		super(Config.BULLET_UP);
		// TODO Auto-generated constructor stub		
		this.direction = npcTank.getDirection();
		
		switch (npcTank.getDirection()) {
		case UP:
			this.x = npcTank.getX() + npcTank.getWidth() / 2 - this.width / 2;
			this.y = npcTank.getY() - this.height / 2;
			break;
			
		case DOWN:
			this.x = npcTank.getX() + npcTank.getWidth() / 2 - this.width / 2;
			this.y = npcTank.getY() + npcTank.getHeight() - this.height / 2;
			break;
			
		case LEFT:
			this.x = npcTank.getX() - this.width / 2;
			this.y = npcTank.getY() + npcTank.getHeight() / 2 - this.height / 2;
			break;
			
		case RIGHT:
			this.x = npcTank.getX() + npcTank.getWidth() - this.width/2;
			this.y = npcTank.getY() + npcTank.getHeight() / 2 - this.height / 2;
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
		if (h instanceof NPCTank || h instanceof NPCBullet) {
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
