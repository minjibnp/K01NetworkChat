package chat6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

//클라이언트가 입력한 메세지를 서버로 전송해주는 쓰레드 클래스
public class Sender extends Thread {
	
	Socket socket;
	PrintWriter out = null;
	String name;
	
	//생성자에서 output스트림을 생성한다.
	public Sender(Socket socket, String name) {
		this.socket = socket;
		
		/*
		 Socket객체를 기반으로 input 스트림을 생성한다.
		 */
		this.socket = socket;
		try {
			out = new PrintWriter(this.socket.getOutputStream(), true);
			this.name = name;
		}
		catch (Exception e) {
			System.out.println("예외>Sender>생성자:"+e);
		}
	}
	
	/*
	 Thread에서 main()의 역할을 하는 메소드로
	 직접 호출하면 안되고, 반드시 start()를 통해 
	 간접적으로 호출해야한다.
	 */
	@Override
	public void run() {
		Scanner s = new Scanner(System.in);
		
			try {
				//클라이언트의 대화명을 서버로 전송한다.
				out.println(name);
				//그 이후부터는 q를 입력할때까지 대화 지속
				while(out != null) {
					try {
						String s2 = s.nextLine();
						if(s2.equalsIgnoreCase("Q")) {
							break;
						}
						else {
							out.println(s2);
						}
					}
					
					catch (Exception e) {
						System.out.println("예외>Sender>run1:"+e);
					}
					
				}
				//Q를 입력할경우 while 루프를 탈출한후 소켓을 종료한다
				out.close();
				socket.close();
				
			}
			
			catch (Exception e) {
				System.out.println("예외>Sender>run2:"+e);
			}
		
	}
	
}