package com.ehsan.snws;

public class Main {
	public static void main (String[] argv) {
		Scenario scenario = new FirstScenario();
		scenario.run();
		// test
		
		System.out.println("\\".replaceAll("\\\\", "\\\\\\\\"));
		
	}
}
