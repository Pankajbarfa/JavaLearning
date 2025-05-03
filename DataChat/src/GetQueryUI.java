
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class GetQueryUI {

	private static JFrame frame;
	private JTextField inpQuery;
	private JTable table;

	/**
	 * Launch the application.
	 */

	// sql connection 
	private static String username;
	private static String pass;
	private static String url;
	private static String apiKey;
	private static String schema;
	private static String query;
	
	
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
	
	public static void SetFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetQueryUI window = new GetQueryUI(apiKey, schema, username, pass, url);
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
	public GetQueryUI(String apiKey, String schema, String username2, String pass2, String url2) {	
		this.apiKey=apiKey;
		this.schema=schema;
		this.username=username2;
		this.pass=pass2;
		this.url=url2;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1064, 657);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ConversaDB");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(0, 0, 1050, 59);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ask a Query");
		lblNewLabel_1.setBounds(64, 124, 113, 27);
		frame.getContentPane().add(lblNewLabel_1);
		
		inpQuery = new JTextField();
		inpQuery.setBounds(44, 199, 446, 33);
		frame.getContentPane().add(inpQuery);
		inpQuery.setColumns(10);
		
		JLabel outputQuery = new JLabel("");
		outputQuery.setVerticalAlignment(SwingConstants.TOP);
		outputQuery.setBounds(44, 300, 453, 40);
		frame.getContentPane().add(outputQuery);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(576, 92, 439, 483);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Get query");
		btnNewButton.setBounds(218, 250, 113, 27);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String query = inpQuery.getText();
				Call_API aiObj = new Call_API(apiKey, schema, query);
				String sqlQuery= aiObj.getResponse();
				
				outputQuery.setText("Answere here  "+sqlQuery);
				
			}
			
		});
		frame.getContentPane().add(btnNewButton);
		
		
	}
}
