package com.kolly.process;

import java.util.Scanner;

/**
 * Created by kolly on 2017/10/30.
 */
public class CreateProcess {

	public static void main(String[] args) throws Exception {

		//http://www.cnblogs.com/dolphin0520/p/3913517.html
		//通过ProcessBuilder创建进程
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "node -v");
		Process process1 = pb.start();
		Scanner scanner1 = new Scanner(process1.getInputStream());

		while (scanner1.hasNextLine()) {
			System.out.println(scanner1.nextLine());
		}
		scanner1.close();

		//通过Runtime的exec方法来创建进程
		String cmd = "cmd "+"/c "+"dir";
		Process process2 = Runtime.getRuntime().exec(cmd);
		Scanner scanner2 = new Scanner(process2.getInputStream());

		while(scanner2.hasNextLine()){
			System.out.println(scanner2.nextLine());
		}
		scanner2.close();
	}

}
