package pers.wengzhengkai.tankwar;

public class TankWar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameWindow gw = new GameWindow(Config.TITLE, Config.MAP_WIDTH, Config.MAP_HEIGHT, Config.FPS);
		gw.start();
	}

}
