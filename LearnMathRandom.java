package com.testleaf.testcases;

public class LearnMathRandom {

	public static void main(String[] args) {
		double random = Math.random();
		System.out.println(random);
		
		int random1 = (int)(Math.random());
		System.out.println(random1);
		
		int random2 = (int)(Math.random()*999+1000);
		System.out.println(random2);
	}

}

