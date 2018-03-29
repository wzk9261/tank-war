package pers.wengzhengkai.tankwar;

public class Water extends Picture implements Blockable {

	public Water(int x, int y) {
		super(x, y, Config.WATER);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.drawPic(this.x, this.y, Config.WATER);
	
	}

}
