package pers.wengzhengkai.test;

public class Polymorphism {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Father f = new Son1(); // 非静态方法(method)编译看左边父类中是否存在，运行结果看右边子类，动态绑定
		f.method();
		
		callMethod(new Son2());
		
//		f.speak(); // 其他情况均看左边
		System.out.println(f.name);
		System.out.println(f.age);
	}
	
	// 多态写一个调用函数就够了，不使用多态的话还要根据子类不同多写几个调用函数
	public static void callMethod(Father f) {
		f.method();
	}
	
}

class Father {
	public String name = "father name";
	public int age = 50;
	
	public void method() {
		System.out.println("father");
	}
	
	public static void speak() {
		System.out.println("father speak");
	}
}

class Son1 extends Father{
	public String name = "son1 name";
	public int age = 25;
	
	@Override
	public void method() {
		System.out.println("son1");
	}
	
	public static void speak() {
		System.out.println("son1 speak");
	}
}

class Son2 extends Father {
	public String name = "son2 name";
	public int age = 23;
	
	@Override
	public void method() {
		System.out.println("son2");
	}
	
	public static void speak() {
		System.out.println("son2 speak");
	}
}
