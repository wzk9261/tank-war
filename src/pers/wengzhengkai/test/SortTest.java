package pers.wengzhengkai.test;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SortTest {
	private static CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {65, 967, 42, 2, 33};
		
		for (int i : arr) {
			list.add(i);			
		}
		
		System.out.println(list);
		
		list.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1 - o2;
			}
		});
		
		System.out.println(list);
		
	}

}
