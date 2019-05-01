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
			// �����ͻ���socket��ͬʱָ�����������������Ӷ˿ںţ�0.5�֣�
			// ���������
			s = new Socket("127.0.0.1", 28888);

			OutputStream os = s.getOutputStream();
			PrintWriter out = new PrintWriter(os, true);

			InputStream is = s.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			sbr = new BufferedReader(new InputStreamReader(System.in));

			// �ͻ��˴�ӡ��ӭ��Ϣ
			// ���������
			System.out.println("��ӭ����ͻ���");
			System.out.println("�������˺�");

			String message;
			// �ͻ��˷����û������������ˣ�ͬʱ���շ������˵�ȷ����Ϣ����0.5�֣�
			// �ͻ��˷������뵽�������ˣ�ͬʱ���շ������˵Ľ����Ϣ����1�֣�
			// �ͻ��˽��ս����Ϣ�󣬴�ӡ��������ر�socket����0.5�֣�
			int state = 0; // 0��ʼ��1�ѷ����û���δ�������룬2�ѷ�������

			while ((message = sbr.readLine()) != null) {
				// ���������
				switch (state) {
				case 0:
					// ������������˺�
					out.println(message);
					System.out.println("����������");
					state = 1;
					break;
				case 1:
					String string = null;
					// ��ȡ������������Ϣ
					if ((string = br.readLine()) != null) {
						// ���������������
						out.println(message);
						// ��ȡ������������Ϣ
						if ((string = br.readLine()) != null) {
							// ���������������Ϣ
							System.out.println(string);
							// �رշ�����
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
