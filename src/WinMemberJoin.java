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
import javax.swing.JPasswordField;

public class WinMemberJoin extends JDialog {
	private JTextField tfid;
	private JTextField tfname;
	private JTextField tfemail;
	private JTextField tfmobile;
	private JPasswordField tfPw;

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
		
		JLabel lblName = new JLabel("name : ");
		lblName.setBounds(38, 124, 57, 15);
		getContentPane().add(lblName);
		
		tfname = new JTextField();
		tfname.setEnabled(false);
		tfname.setColumns(10);
		tfname.setBounds(131, 121, 116, 21);
		getContentPane().add(tfname);
		
		JLabel lblemail = new JLabel("email : ");
		lblemail.setBounds(38, 167, 57, 15);
		getContentPane().add(lblemail);
		
		tfemail = new JTextField();
		tfemail.setEnabled(false);
		tfemail.setColumns(10);
		tfemail.setBounds(131, 164, 266, 21);
		getContentPane().add(tfemail);
		
		JLabel lblmobile = new JLabel("mobile");
		lblmobile.setBounds(38, 210, 57, 15);
		getContentPane().add(lblmobile);
		
		tfmobile = new JTextField();
		tfmobile.setEnabled(false);
		tfmobile.setColumns(10);
		tfmobile.setBounds(131, 207, 116, 21);
		getContentPane().add(tfmobile);
		
		JButton btnJoin = new JButton("가입");
		btnJoin.setEnabled(false);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqldb","root","1234");
//					Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.102:3306/sqldb","student","1234");
					System.out.println("DB 연결 완료");
					Statement stmt = con.createStatement();
					
					String temp = tfmobile.getText(); // 01011112222 => 010-1111-2222
					if(temp.length() ==11)
						temp = temp.substring(0, 3)+"="+temp.substring(3,7)+"-"+temp.substring(7);
					else
						temp = "000-0000-0000";
					
//					temp = temp.substring(0, 3)+"="+temp.substring(3,7)+"-"+temp.substring(7); 
//					System.out.println(temp);
					
					String sql = "insert into membertbl values('" + tfid.getText()+"','";
					sql = sql + tfPw.getPassword().toString() + "','" + tfname.getText() + "','";
					sql = sql + tfemail.getText() + "','" + temp + "')";
					
					if(stmt.executeUpdate(sql) <= 0)
						System.out.println("삽입 오류 발생");
					
// 					System.out.println(sql);

				}catch (ClassNotFoundException | SQLException e1) {
					
					System.out.println("DB 문제1");
				}
			}
		});
		btnJoin.setBounds(300, 195, 97, 34);
		getContentPane().add(btnJoin);
		
		tfPw = new JPasswordField();
		tfPw.setEnabled(false);
		tfPw.setBounds(131, 81, 116, 21);
		getContentPane().add(tfPw);
		
		JButton btnDup = new JButton("중복확인");
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqldb","root","1234");
					System.out.println("DB 연결 완료");
					Statement stmt = con.createStatement();
					String sql = "select * from membertbl where id ='" + tfid.getText() +  "'";
					ResultSet rs = stmt.executeQuery(sql);
					if(!rs.next()) {
						enableEdit();
					}else
						tfid.setSelectionStart(0); //블록 시작
						tfid.setSelectionEnd(tfid.getText().length()); //블록 끝
						tfid.requestFocus();

				} catch (ClassNotFoundException | SQLException e1) {

					System.out.println("DB 문제1");
				}
			}

			private void enableEdit() {
			btnJoin.setEnabled(true);
			tfPw.setEnabled(true);
			tfname.setEnabled(true);
			tfemail.setEnabled(true);
			tfmobile.setEnabled(true);
			}
		});

		btnDup.setBounds(271, 40, 97, 23);
		getContentPane().add(btnDup);
	}
}
