package pers.wengzhengkai.tankwar;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import utils.Window;

public class GameWindow extends Window {

	private MyTank wengTank;
	private MyTank2 wenTank;
	private MyBullet myBullet;
	private MyBullet2 myBullet2;
	private CopyOnWriteArrayList<Picture> list = new CopyOnWriteArrayList<>();
	private CopyOnWriteArrayList<Picture> npcList = new CopyOnWriteArrayList<>();

	public GameWindow(String title, int width, int height, int fps) {
		super(title, width, height, fps);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate() {
		wengTank = new MyTank(64 * 16, 256, true);
		addSortPics(wengTank);
		
		wenTank = new MyTank2(64, 256, false);
		addSortPics(wenTank);
		
		
		for (int i = 1; i < 17; i++) {
			Wall wall = new Wall(64 * i, 64 * 2);
			addSortPics(wall);
		}
		
		for (int i = 4; i < 14; i++) {
			Wall wall = new Wall(64 * i, 64 * 7);
			addSortPics(wall);
		}
		
		for (int i = 2; i < 16; i++) {
			Water water = new Water(64 * i, 64 * 6);
			addSortPics(water);
		}
		
		for (int i = 0; i < 18; i++) {
			Grass grass = new Grass(64 * i, 64 * 8);
			addSortPics(grass);
		}
		
		for (int i = 0; i < 18; i++) {
			Grass grass = new Grass(64 * i, 64 * 9);
			addSortPics(grass);
		}
		
		for (int i = 3; i < 15; i++) {
			Steel steel = new Steel(64 * i, 64 * 4);
			addSortPics(steel);
		}
		
		for (int i = 0; i < 10; i++) {
			NPCTank tank = new NPCTank(64 * i * 2, 0);
			addSortPics(tank);
			npcList.add(tank);
		}

	}

	private void addSortPics(Picture pic) {
		// TODO Auto-generated method stub
		list.add(pic);
		
		list.sort(new Comparator<Picture>() {

			@Override
			public int compare(Picture o1, Picture o2) {
				// TODO Auto-generated method stub
				return o1.getOrder() - o2.getOrder();
			}
		});
	}

	@Override
	protected void onMouseEvent(int key, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onKeyEvent(int key) {
		// TODO Auto-generated method stub	
		switch (key) {
		case Keyboard.KEY_UP:
			wengTank.move(Direction.UP);
			break;
			
		case Keyboard.KEY_DOWN:
			wengTank.move(Direction.DOWN);
			break;
			
		case Keyboard.KEY_LEFT:
			wengTank.move(Direction.LEFT);
			break;
			
		case Keyboard.KEY_RIGHT:
			wengTank.move(Direction.RIGHT);
			break;

		case Keyboard.KEY_RETURN:
			myBullet = wengTank.shot();
			if (myBullet != null) {
				addSortPics(myBullet);
			}
			break;
			
		case Keyboard.KEY_W:
			wenTank.move(Direction.UP);
			break;
			
		case Keyboard.KEY_S:
			wenTank.move(Direction.DOWN);
			break;
			
		case Keyboard.KEY_A:
			wenTank.move(Direction.LEFT);
			break;
			
		case Keyboard.KEY_D:
			wenTank.move(Direction.RIGHT);
			break;

		case Keyboard.KEY_SPACE:
			myBullet2 = wenTank.shot();
			if (myBullet2 != null) {
				addSortPics(myBullet2);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDisplayUpdate() {
		// TODO Auto-generated method stub
		if (wenTank.isDestroyed() || wengTank.isDestroyed() || npcList.size() == 0) {
			if (wenTank.isDestroyed()) {
				list.clear();
				Picture.drawPic(0, 0, Config.LOSE);
			} else if (wengTank.isDestroyed()) {
				list.clear();
				Picture.drawPic(0, 0, Config.WIN);
			} else {
				list.clear();
				Picture.drawPic(0, 0, Config.WIN);
			}
			
			launchNewThread(3000);
		}
		
		// 在极短的刷新时间下，不断地画出所有对象。
		for (Picture p: list) {
			p.draw();
		}
		
		// 我方坦克不断发射子弹。
//		if (list.contains(myTank)) {
//			myBullet = myTank.shot();
//			if (myBullet != null) {
//				addSortPics(myBullet);
//			}
//		}
		
		// 敌方坦克不断发射子弹。
		for (Picture p : list) {
			if (p instanceof NPCTank) {
				NPCBullet bullet = ((NPCTank) p).shot();
				if (bullet != null) {
					addSortPics(bullet); // 这一步是为了在for循环的p.draw()中将其画出来。
				}
			}
		}

		// 判断整张地图中移动对象（敌我双方坦克）和阻挡对象（墙水草铁、敌方坦克）是否相撞。
		// 若敌方坦克之间相互碰撞，则掉头。
		for (Picture p1 : list) {
			for (Picture p2 : list) {
				if (p1 instanceof Movable && p2 instanceof Blockable) {
					if (((Movable) p1).checkHit((Blockable) p2)) {
						break; // 某个移动对象只需要碰到第一个与之碰撞的阻挡对象就停止检测，然后跳出内层for循环，接着下一个移动对象开始碰撞检测。
						// break去掉的话该移动对象会与全图的阻挡对象发生一次碰撞检测。但是坦克图片很小，最多只会与全图一个阻挡对象发生碰撞，
						// 当与其他阻挡对象碰撞检测时，不发生碰撞，badDirection会被设置为null，那么在move方法中不会进行关于badDirection和seed的判断，
						// 所以此时坦克即使撞上了阻挡对象也会继续前进。
					}
				}
			}
		}
		
		// 判断整张地图中攻击对象（敌我双方子弹）和挨打对象（墙铁、敌我双方坦克及子弹）是否相撞。
		// 若发生碰撞，则移除攻击对象；若挨打对象是敌我双方子弹，则将挨打对象移除。
		// 敌方子弹不与敌方坦克或自身对象发生碰撞检测，我方子弹不与我方坦克或自身对象发生碰撞检测。
		// 在碰撞发生时，挨打对象不停产生爆炸。
		for (Picture p1 : list) {
			for (Picture p2 : list) {
				if (p1 instanceof Attackable && p2 instanceof Hitable) {
					if (((Attackable) p1).checkHit((Hitable) p2)) {
						list.remove(p1);
						
						// 若挨打对象是子弹，不移除应该也是可以的，因为子弹既是挨打对象也是攻击对象，而攻击对象在上一步就被移除了。
						if (p2 instanceof MyBullet || p2 instanceof MyBullet2 || 
								p2 instanceof NPCBullet) {
							list.remove(p2);
						}
						
						Blast blast = ((Hitable) p2).showBlast();
						addSortPics(blast);
						
						break;
					}
				}
			}
		}
		
		// 判断整张地图中的毁灭对象是否已经销毁，若销毁，则产生大爆炸。并在爆炸产生后将其移除。
		for (Picture p : list) {
			if (p instanceof Destroyable) {
				if (((Destroyable) p).isDestroyed()) {
					Blast blast = ((Destroyable) p).showBigBlast();
					
					// 敌我方子弹的showBigBlast方法的返回值都是null，即子弹销毁时不发生大爆炸。
					if (blast != null) {
						addSortPics(blast);						
					}
					
					list.remove(p);
					if (p instanceof NPCTank) {
						npcList.remove(p);
					}
				}
			}
		}
		
	}

	public static void launchNewThread(int waitTime) {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				System.exit(0);
			}
		}.start();
	}

}
