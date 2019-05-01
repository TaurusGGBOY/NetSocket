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

	// ��ʼ���û����������
	public static void main(String[] args) {
		idPassword.put("Alice", "aaaaaaaaaa");
		idPassword.put("Bob", "bbbbbbbbb");
		idPassword.put("Cindy", "cccccccccc");
		idPassword.put("David", "ddddddddd");
		idPassword.put("Eve", "eeeeeeeeee");
		idPassword.put("Frank", "ffffffffff");
		idPassword.put("George", "gggggggggg");

		daytimetcpser s = new daytimetcpser();
		// ָ���������������Ķ˿ں� port��0.5�֣�
		s.startServer(28888);
	}

	public void startServer(int port) {
		ServerSocket ss = null;
		Socket socket = null;
		try {
			// �����������˵�socket��ͬʱ���ݶ˿ںţ�0.5�֣�
			// ���������
			ss = new ServerSocket(28888);

			while (true) {
				try {
					System.out.println("Listening...");
					// ��������socket��ʼ������0.5�֣�
					// ���������
					socket = ss.accept();

					InputStream is = socket.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

					String str = null;

					// �������˽��տͻ��˴��ݵ��û�����������ȷ����Ϣ����0.5�֣�
					// �������˽��տͻ��˴��ݵ����룬�����û���Ϣ���ж��Ƿ�Ϸ�����1�֣�
					// ����������ͻ��˷����жϽ����Ϣ�����ر�socket������������һ���ͻ������󡣣�0.5�֣�
					int state = 0; // 0��ʼ����1���յ��˺�û�յ�����
					String id = null;
					while ((str = br.readLine()) != null) {
						System.out.println("�յ���" + str);
						// ���������
						if (str != null) {
							switch (state) {
							case 0:
								// ��ͻ��˷�����Ϣ
								out.println("����������");
								// �����˺�
								id = str;
								state = 1;
								break;

							case 1:
								// ��֤�˺������Ƿ���ȷ
								if (idPassword.get(id).equals(str)) {
									// ��֤ͨ������ͻ��˷�����Ϣ
									out.println("��½�ɹ�");
									socket = null;
								} else {
									// ��֤��ͨ������ͻ��˷�����Ϣ
									out.println("�������");
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