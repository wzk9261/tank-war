package pers.wengzhengkai.tankwar;

public class Grass extends Picture {

	public Grass(int x, int y) {
		super(x, y, Config.GRASS);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.drawPic(this.x, this.y, Config.GRASS);
	
	}
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 2;
	}

}
