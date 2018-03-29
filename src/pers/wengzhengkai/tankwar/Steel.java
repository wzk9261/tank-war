package pers.wengzhengkai.tankwar;

public class Steel extends Picture implements Blockable, Hitable, Destroyable {

	private int blood = 20;

	public Steel(int x, int y) {
		super(x, y, Config.STEEL);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.drawPic(this.x, this.y, Config.STEEL);
	
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
