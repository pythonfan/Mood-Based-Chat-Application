import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class chat_client extends JFrame {
	private static JTextArea msg_area;
	private static JTextArea msg_text;

	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	/**
	 * Launch the application.
	 */
	public static void startchat() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chat_client frame = new chat_client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try{
			s = new Socket("127.0.0.1", 1201);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			String msgin = "";
			while(!msgin.equals("exit"))
			{
				msgin = din.readUTF();
				msg_area.setText(msg_area.getText().trim()+ "\n" + msgin.trim());
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Create the frame.
	 */
	public chat_client() {
		setTitle("Chat Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		msg_area = new JTextArea();
		msg_area.setBounds(10, 11, 414, 180);
		getContentPane().add(msg_area);
		msg_area.setColumns(10);
		
		msg_text = new JTextArea();
		msg_text.setBounds(10, 201, 340, 50);
		getContentPane().add(msg_text);
		msg_text.setColumns(10);
		
		JButton send_btn = new JButton("Send");
		send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msgout = "";
				msgout = msg_text.getText().trim();
				try {
					dout.writeUTF(msgout);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg_text.setText("");

			}
		});
		send_btn.setBounds(359, 202, 65, 49);
		getContentPane().add(send_btn);
	}
}
