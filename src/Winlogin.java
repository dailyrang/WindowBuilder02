import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class Winlogin extends JDialog {
	private JTextField tfID;
	private JPasswordField tfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Winlogin dialog = new Winlogin();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Winlogin() {
		setTitle("로그인 창");
		setBounds(100, 100, 450, 210);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(33, 40, 70, 15);
		panel.add(lblID);
		
		tfID = new JTextField();
		tfID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPassword.requestFocus();
				}
			}
		});
		tfID.setBounds(134, 37, 116, 21);
		panel.add(tfID);
		tfID.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(33, 97, 70, 15);
		panel.add(lblPassword);
		
		JButton btnLogin = new JButton("로그인...");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 데이터베이스 연결
				connectDB();
			}
		});
		btnLogin.setBounds(271, 36, 97, 76);
		panel.add(btnLogin);
		
		JLabel lblJoinMember = new JLabel(" 회원가입...");
		lblJoinMember.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WinMemberJoin winMemberJoin = new WinMemberJoin();
				winMemberJoin.setModal(true);
				winMemberJoin.setVisible(true);
			}
		});
		lblJoinMember.setBounds(134, 146, 116, 15);
		panel.add(lblJoinMember);
		
		tfPassword = new JPasswordField();
		tfPassword.setBounds(134, 94, 116, 21);
		panel.add(tfPassword);

	}

	protected void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			System.out.println("DB 연결 완료");
			
			String sql = "select * from memberTBL where id='" + tfID.getText() + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				String pw = rs.getString("password");
				System.out.println(pw + " vs. " + new String(tfPassword.getPassword()));
				String sName = rs.getString("name");  // 추가
				
				if(pw.equals(new String(tfPassword.getPassword()))) {
//					dispose();
					WinMain winMain = new WinMain(sName);
					winMain.setModal(true);
					winMain.setVisible(true);	
//					JOptionPane.showMessageDialog(null, "로그인성공");
				}else {
					JOptionPane.showMessageDialog(null, "패스워드를 확인하세요");
					tfPassword.setText("");
					tfPassword.requestFocus();
				}					
			}
			else
				JOptionPane.showMessageDialog(null, "아이디를 확인하세요");
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("DB 문제");
		}
		
	}
}
