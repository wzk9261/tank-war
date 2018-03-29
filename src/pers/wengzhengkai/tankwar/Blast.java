package pers.wengzhengkai.tankwar;

public class Blast extends Picture implements Destroyable {
	
	private String[] picArr = {Config.BLAST1, Config.BLAST2, Config.BLAST3, Config.BLAST4};
	private int index;
	private boolean isDestroyed;

	public Blast(Hitable h, boolean isLastShot) {
		// TODO Auto-generated constructor stub
		super(Config.BLAST1);
		
		Picture p = (Picture) h;
		this.x = p.x - (this.width / 2 - p.width / 2);
		this.y = p.y - (this.height / 2 - p.height / 2);
		
		if (isLastShot) {
			picArr = new String[] {Config.BLAST1, Config.BLAST2, Config.BLAST3, Config.BLAST4,
					Config.BLAST5, Config.BLAST6, Config.BLAST7, Config.BLAST8};
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (index < picArr.length) {
			super.drawPic(this.x, this.y, picArr[index]);
			index++;			
		} else {
			isDestroyed = true;
		}
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return isDestroyed;
	}

	@Override
	public Blast showBigBlast() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 3;
	}
	
}
