package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class daytimetcpser {

	static Map<String, String> idPassword = new HashMap<>();

	// 初始化用户名，密码表
	public static void main(String[] args) {
		idPassword.put("Alice", "aaaaaaaaaa");
		idPassword.put("Bob", "bbbbbbbbb");
		idPassword.put("Cindy", "cccccccccc");
		idPassword.put("David", "ddddddddd");
		idPassword.put("Eve", "eeeeeeeeee");
		idPassword.put("Frank", "ffffffffff");
		idPassword.put("George", "gggggggggg");

		daytimetcpser s = new daytimetcpser();
		// 指定服务器端侦听的端口号 port（0.5分）
		s.startServer(28888);
	}

	public void startServer(int port) {
		ServerSocket ss = null;
		Socket socket = null;
		try {
			// 声明服务器端的socket，同时传递端口号（0.5分）
			// 请输入代码
			ss = new ServerSocket(28888);

			while (true) {
				try {
					System.out.println("Listening...");
					// 服务器端socket开始侦听（0.5分）
					// 请输入代码
					socket = ss.accept();

					InputStream is = socket.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

					String str = null;

					// 服务器端接收客户端传递的用户名，并返回确认消息。（0.5分）
					// 服务器端接收客户端传递的密码，根据用户信息表，判断是否合法。（1分）
					// 服务器端向客户端返回判断结果信息，并关闭socket，继续侦听下一个客户端请求。（0.5分）
					int state = 0; // 0初始化，1已收到账号没收到密码
					String id = null;
					while ((str = br.readLine()) != null) {
						System.out.println("收到了" + str);
						// 请输入代码
						if (str != null) {
							switch (state) {
							case 0:
								// 向客户端发送信息
								out.println("请输入密码");
								// 保存账号
								id = str;
								state = 1;
								break;

							case 1:
								// 验证账号密码是否正确
								if (idPassword.get(id).equals(str)) {
									// 验证通过，向客户端发送消息
									out.println("登陆成功");
									socket = null;
								} else {
									// 验证不通过，向客户端发送消息
									out.println("密码错误");
								}
								break;
							}

						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (socket != null) {
						socket.close();
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (ss != null)
				try {
					ss.close();
				} catch (IOException e) {

					e.printStackTrace();
				}

		}
	}

}