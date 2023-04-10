import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class WinMemberJoin extends JDialog {
	private JTextField tfid;
	private JTextField tfpw;
	private JTextField tfname;
	private JTextField tfemail;
	private JTextField tfmobile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinMemberJoin dialog = new WinMemberJoin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMemberJoin() {
		setTitle("회원 가입창");
		setBounds(100, 100, 450, 465);
		getContentPane().setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("id : ");
			lblNewLabel.setBounds(38, 44, 57, 15);
			getContentPane().add(lblNewLabel);
		}
		
		tfid = new JTextField();
		tfid.setBounds(131, 41, 116, 21);
		getContentPane().add(tfid);
		tfid.setColumns(10);
		
		JLabel lblPw = new JLabel("pw : ");
		lblPw.setBounds(38, 84, 57, 15);
		getContentPane().add(lblPw);
		
		tfpw = new JTextField();
		tfpw.setColumns(10);
		tfpw.setBounds(131, 81, 116, 21);
		getContentPane().add(tfpw);
		
		JLabel lblName = new JLabel("name : ");
		lblName.setBounds(38, 124, 57, 15);
		getContentPane().add(lblName);
		
		tfname = new JTextField();
		tfname.setColumns(10);
		tfname.setBounds(131, 121, 116, 21);
		getContentPane().add(tfname);
		
		JLabel lblemail = new JLabel("email : ");
		lblemail.setBounds(38, 167, 57, 15);
		getContentPane().add(lblemail);
		
		tfemail = new JTextField();
		tfemail.setColumns(10);
		tfemail.setBounds(131, 164, 266, 21);
		getContentPane().add(tfemail);
		
		JLabel lblmobile = new JLabel("mobile");
		lblmobile.setBounds(38, 210, 57, 15);
		getContentPane().add(lblmobile);
		
		tfmobile = new JTextField();
		tfmobile.setColumns(10);
		tfmobile.setBounds(131, 207, 116, 21);
		getContentPane().add(tfmobile);
		
		JButton btnJoin = new JButton("가입");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqldb","root","1234");
//					Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.102:3306/sqldb","student","1234");
					System.out.println("DB 연결 완료");
					Statement stmt = con.createStatement();
					String sql = "insert into membertbl values('" + tfid.getText()+"','";
					sql = sql + tfpw.getText() + "','" + tfname.getText() + "','";
					sql = sql + tfemail.getText() + "','" + tfmobile.getText() + "')";
					
					if(stmt.executeUpdate(sql) <= 0)
						System.out.println("삽입 오류 발생");
					
// 					System.out.println(sql);

				} catch (ClassNotFoundException | SQLException e1) {

					System.out.println("DB 문제");
				}
			}
		});
		btnJoin.setBounds(300, 206, 97, 23);
		getContentPane().add(btnJoin);
	}

}
