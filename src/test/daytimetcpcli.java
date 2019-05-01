package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class daytimetcpcli {

	public static void main(String[] args) {
		daytimetcpcli c = new daytimetcpcli();
		c.receiveMessage();
	}

	public void receiveMessage() {
		Socket s = null;

		BufferedReader sbr = null;
		try {
			// 声明客户端socket，同时指定服务器名称与连接端口号（0.5分）
			// 请输入代码
			s = new Socket("127.0.0.1", 28888);

			OutputStream os = s.getOutputStream();
			PrintWriter out = new PrintWriter(os, true);

			InputStream is = s.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			sbr = new BufferedReader(new InputStreamReader(System.in));

			// 客户端打印欢迎信息
			// 请输入代码
			System.out.println("欢迎进入客户端");
			System.out.println("请输入账号");

			String message;
			// 客户端发送用户名到服务器端，同时接收服务器端的确认消息。（0.5分）
			// 客户端发送密码到服务器端，同时接收服务器端的结果消息。（1分）
			// 客户端接收结果信息后，打印结果，并关闭socket。（0.5分）
			int state = 0; // 0初始，1已发送用户名未发送密码，2已发送密码

			while ((message = sbr.readLine()) != null) {
				// 请输入代码
				switch (state) {
				case 0:
					// 向服务器发送账号
					out.println(message);
					System.out.println("请输入密码");
					state = 1;
					break;
				case 1:
					String string = null;
					// 获取服务器返回消息
					if ((string = br.readLine()) != null) {
						// 向服务器发送密码
						out.println(message);
						// 获取服务器返回消息
						if ((string = br.readLine()) != null) {
							// 输出服务器返回消息
							System.out.println(string);
							// 关闭服务器
							s = null;
						}
					}
					break;
				default:
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
