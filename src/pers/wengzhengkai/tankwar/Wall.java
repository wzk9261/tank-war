package pers.wengzhengkai.tankwar;

public class Wall extends Picture implements Blockable, Hitable, Destroyable {

	private int blood = 10;

	public Wall(int x, int y) {
		super(x, y, Config.WALL);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.drawPic(this.x, this.y, Config.WALL);
	
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

}
