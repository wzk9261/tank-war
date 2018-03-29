package pers.wengzhengkai.test;

public class OverLoad {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(sum(4, 5));
		System.out.println(sum(4, 5, 6));
		System.out.println(sum(4.0, 5));
		System.out.println(sum(4.0, 5.8));
	}
	
	public static int sum(int a, int b) {
		return a + b;
	}
	
//	public static double sum(int a, int b) {
//		return a + b;
//	}
	
	public static int sum(int a, int b, int c) {
		return a + b + c;
	}
	
	public static double sum(double a, int b) {
		return a + b;
	}
	
	public static double sum(double a, double b) {
		return a + b;
	}

}
