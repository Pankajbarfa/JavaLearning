package JavaGUI;

import java.sql.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

// test class for connect to mysql db
public class JavaGUI {

	private JFrame frame;
	private JTextField inpFirstName;
	private JTextField inpLastName;
	private JTextField inpAddress;
	private JTextField inpCity;

	
	private JTable table;
		
	private JTextField inpPersonID;
	
	// sql connection 
	private String username="root";
	private String pass="Pankaj@123";
	private String url="jdbc:mysql://localhost:3306/projectjava";
	private String query="Select * from persons";
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con =DriverManager.getConnection(url, username, pass);	
		} 
		catch (ClassNotFoundException | SQLException e) {
			System.out.print(e);
			e.printStackTrace();
		}
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaGUI window = new JavaGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaGUI() {
		initialize();
		connect();
		loadTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 999, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		inpFirstName = new JTextField();
		inpFirstName.setBounds(187, 147, 173, 29);
		frame.getContentPane().add(inpFirstName);
		inpFirstName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(48, 147, 108, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddress.setBounds(48, 217, 108, 35);
		frame.getContentPane().add(lblAddress);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLastName.setBounds(48, 182, 108, 35);
		frame.getContentPane().add(lblLastName);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCity.setBounds(48, 252, 108, 35);
		frame.getContentPane().add(lblCity);
		
		JLabel lblPersonid = new JLabel("PersonID");
		lblPersonid.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPersonid.setBounds(48, 287, 108, 35);
		frame.getContentPane().add(lblPersonid);
		
		inpLastName = new JTextField();
		inpLastName.setColumns(10);
		inpLastName.setBounds(187, 187, 173, 29);
		frame.getContentPane().add(inpLastName);
		
		inpAddress = new JTextField();
		inpAddress.setColumns(10);
		inpAddress.setBounds(187, 222, 173, 29);
		frame.getContentPane().add(inpAddress);
		
		inpCity = new JTextField();
		inpCity.setColumns(10);
		inpCity.setBounds(187, 257, 173, 29);
		frame.getContentPane().add(inpCity);
		
		inpPersonID = new JTextField();
		inpPersonID.setColumns(10);
		inpPersonID.setBounds(187, 292, 173, 29);
		frame.getContentPane().add(inpPersonID);
		
		JButton btnSubmitData = new JButton("Submit");
		btnSubmitData.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				String firstName, lastName, id, city , address;
				firstName = inpFirstName.getText();
				lastName= inpLastName.getText();
				id=inpPersonID.getText();
				city= inpCity.getText();
				address= inpAddress.getText();
				
				try {
					pst = con.prepareStatement("insert into persons values(?,?,?,?,?)");
					pst.setString(1, id);
					pst.setString(2, lastName);
					pst.setString(3, firstName);
					pst.setString(4, address);
					pst.setString(5, city);
					JOptionPane.showMessageDialog(null, "Record Added !....");
					
					pst.executeUpdate();
					loadTable();
					
					inpFirstName.setText("");
					inpLastName.setText("");
					inpPersonID.setText("");
					inpCity.setText("");
					inpAddress.setText("");
					
					inpFirstName.requestFocus();
				}catch(Exception e1){
					
				}
				
			}
		});
		
		
		btnSubmitData.setBounds(139, 333, 89, 23);
		frame.getContentPane().add(btnSubmitData);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(460, 85, 494, 271);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("Register");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(77, 11, 283, 35);
		frame.getContentPane().add(lblNewLabel_1);
	}
	
	public void loadTable() {
		try {
			pst=con.prepareStatement(query);
			rs= pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
}
