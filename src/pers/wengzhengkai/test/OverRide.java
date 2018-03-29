package pers.wengzhengkai.test;

public class OverRide {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Father1 f1 = new Father1();
		f1.method();
		
		Father1 f2 = new Son3();
		f2.method();
	}

}

class Father1 {
	public Father1 method() {
		System.out.println("Father1 method");
		return new Father1();
	}
}

class Son3 extends Father1 {
	@Override
	public Son3 method() {
		System.out.println("Son3 method");
		return new Son3();
	}
}
