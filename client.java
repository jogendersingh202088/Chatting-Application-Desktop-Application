package chattingapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class client  implements ActionListener {

	JLabel i2,i8,vc,ac,mr,name,status;
	static JLabel time;
	static JLabel output;
	JTextField message;
	JButton send;
	static JPanel a1;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;
	static JFrame f = new JFrame();
	
	client(){
		f.setSize(450,700);
		f.setLocation(800,70);
		f.setLayout(null);
		
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);
		p1.setLayout(null);
		
			ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
			Image i3 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
			ImageIcon i4 = new ImageIcon(i3);
		   i2 = new JLabel(i4);
			i2.setBounds(5,20,25,25);
			
			f.add(p1);
		p1.add(i2);
		
			i2.addMouseListener(new MouseAdapter(){
				
				
				public void mouseClicked(MouseEvent e ) {
			
					f.setVisible(false);
				}
				});
		
	
			ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
			Image i6 = i5.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			ImageIcon i7 = new ImageIcon(i6);
		   i8 = new JLabel(i7);
			i8.setBounds(40,10,50,50);
				p1.add(i8);
		
				ImageIcon vedio = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
				Image vedio1 = vedio.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
				ImageIcon vedio2 = new ImageIcon(vedio1);
			   vc = new JLabel(vedio2);
				vc.setBounds(290,22,30,30);
					p1.add(vc);
	
					ImageIcon audio = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
					Image audio1 = audio.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
					ImageIcon audio2 = new ImageIcon(audio1);
				   ac = new JLabel(audio2);
					ac.setBounds(350,22,35,30);
						p1.add(ac);
						
						ImageIcon more = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
						Image more1 = more.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
						ImageIcon more2 = new ImageIcon(more1);
					   mr = new JLabel(more2);
						mr.setBounds(410,22,10,25);
							p1.add(mr);
							
						name = new JLabel("jogi");
						name.setBounds(110,23,100,21);
						name.setForeground(Color.white);
					name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
						p1.add(name);
					
						
						status = new JLabel("Active now");
						status.setBounds(110,43,100,20);
						status.setForeground(Color.white);
						status.setFont(new Font("SAN_SERIF",Font.PLAIN,10));
						p1.add(status);
						
						 a1 = new JPanel();
						a1.setBounds(5,75,440,570);
						f.add(a1);
						
						
						message = new JTextField();
						message.setBounds(5,655,310,40);
						message.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
						f.add(message);
						
						send = new JButton("Send");
						send.setBounds(320,655,123,40);
						send.setBackground(new Color(7,94,84));
						send.setForeground(Color.white);
						send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
						
						send.addActionListener(this);
						f.add(send);
						
	    
						f.getContentPane().setBackground(Color.WHITE);
						f.setUndecorated(true);
						f.setVisible(true);
		
		
		
	
		
		
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		String out = message.getText();
	
		
		JPanel p2 =  formatLabel(out);
	
	
		
        		a1.setLayout(new BorderLayout());
        		
        		
        		JPanel right = new JPanel(new BorderLayout());
        		right.add(p2,BorderLayout.LINE_END);
        		vertical.add(right);
        		vertical.add(Box.createVerticalStrut(15));
        		a1.add(vertical,BorderLayout.PAGE_START);
        		
        		try {
					dout.writeUTF(out);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		message.setText("");
        		f.repaint();
        		f.invalidate();
        		f.validate();
	}
	
public static JPanel formatLabel(String out) {
	
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	
	output = new JLabel("<html><p style=\"width : 150px\">"+out+" </p></html>");
	
	output.setFont(new Font("Tahoma",Font.PLAIN,16));
	output.setForeground(Color.black);
	output.setBackground(new Color (37,211,102));
	output.setOpaque(true);
	output.setBorder(new EmptyBorder(15,15,15,50));
	panel.add(output);
	
	
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	 time = new JLabel();
	
	time.setText(sdf.format(cal.getTime()));
	panel.add(time);
	
	
	return panel;
	
	
	
}
public static void main(String[]args) {
	new client();
	
	
	try {
		Socket s = new Socket("127.0.0.1",6001);
		DataInputStream din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		
		while(true) {
			a1.setLayout(new BorderLayout());
			String msg = din.readUTF();
			JPanel panel = formatLabel(msg);
			JPanel left = new JPanel(new BorderLayout());
			left.add(panel,BorderLayout.LINE_START);
			vertical.add(left);
			
			vertical.add(Box.createVerticalStrut(15));
			a1.add(vertical,BorderLayout.PAGE_START);
			f.validate();
			
		}
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
}
	
}
