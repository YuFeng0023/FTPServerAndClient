package com.yufeng.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.yufeng.controller.ClientController;
import com.yufeng.model.FileItem;
import com.yufeng.util.PropertyFileReader;

public class ClientView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7535256121609177086L;
	private JTextField textField;
	protected JTree tree;
	private ScrollPane pane;
	protected MutableTreeNode current = null;
	protected DefaultMutableTreeNode root;
	JButton uploadBtn;
	JButton downloadBtn;
	private JButton refresh;
	public ClientView() {
		setLookAndFeel();
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("路径：");
		panel.add(label);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		uploadBtn = new JButton("上传");
		panel.add(uploadBtn);
		
		downloadBtn = new JButton("下载");
		panel.add(downloadBtn);
		
		pane = new ScrollPane();
		getContentPane().add(pane);
		root = new DefaultMutableTreeNode("服务器", true);
		tree = new JTree(root);
		pane.add(tree);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.WEST);
		
		refresh = new JButton("刷新");
		toolBar.add(refresh);
		
		init();
	}
	protected void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}
	public void init(){
		setView();
		setController();
		setListoner();
	}
	protected void setListoner() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.close();
			}
		});
		tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
               
                if (node == null)
                    return;
                onselectedAction(node);
            }
            
        });
		downloadBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.download();
			}
		});
		uploadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.upload();
			}
		});
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.refresh();
			}
		});
	}
	/**
	 * 设置视图组件，待解决
	 */
	public void setView(){
		
	}
	public void setController(){
		this.controller = new Controller();
		this.controller.setClient(this);
		this.controller.init();
	}
	public static void main(String[] args) {
		JFrame test = new ClientView();
		test.setVisible(true);
		test.setSize(400, 500);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void load(FileItem item){
		if(item == null){
			
		}
	}
	@Override
	public Dimension getSize() {
		return new Dimension(400, 500);
	}
	protected void onselectedAction(DefaultMutableTreeNode node){
		current = node;
		FileItem item = (FileItem) node.getUserObject();
        controller.getFileItem( item );
        textField.setText(item.getAbsolute());	
    }
	Controller controller;
}

class Controller{
	ClientView client ;
	ClientController controller ;
	FileItem root;
	Socket socket;
	JFileChooser chooser = new JFileChooser("");
	public void init(){
		/**
		 * 读取配置信息
		 */
		PropertyFileReader reader = new PropertyFileReader();
		try {
			reader.loadFile("../config/config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String address = reader.getValue("address");
		String port = reader.getValue("port");
		/**
		 * 创建套接字
		 */
		try {
			socket = new Socket(InetAddress.getByName(address), Integer.parseInt(port));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * 绑定控制层
		 */
		controller = new ClientController(socket);
		
		login();
		root = new FileItem(FileItem.DIRECTORY, "服务器：", "");
		root.setFid(0);
		root.setId(0);
		client.current = client.root;
		client.root.setUserObject(root);
		getFileItem(root);
		root.setId(-1);
	}
	private void login() {
		String username = JOptionPane.showInputDialog("请输入用户名");
		if(username==null){
			close();
			System.exit(0);
		}
		JPasswordField pw = new JPasswordField();
		JOptionPane.showMessageDialog(null, pw, "Please Input your Password", JOptionPane.PLAIN_MESSAGE);
		char[] pass = pw.getPassword();
		if(pass.length==0){
			close();
			System.exit(0);
		}
		String password = new String(pass);
		boolean flag = controller.login(username, password);
		if(!flag){
			JOptionPane.showMessageDialog(client,controller.getMessage(), "错误信息", JOptionPane.ERROR_MESSAGE);
			login();
		}
	}
	/**
	 * 添加文件节点
	 * @param item 被选中的文件节点
	 */
	public void getFileItem(FileItem item){
		if(item==null||item.getType()==FileItem.FILE||item.getId()==-1){
			return ;
		}
		try{
			List<FileItem> newItem=controller.loadItem(item.getAbsolute());
			System.out.println(newItem);
			addItem(newItem);
		}catch(RuntimeException e){
			JOptionPane.showMessageDialog(client, e.getMessage()+",请刷新！", "错误信息", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void removeAll() {
//		if(client.current!=null){
//			((DefaultMutableTreeNode)client.current).removeAllChildren();
//			((DefaultTreeModel) client.tree.getModel()).reload();
//		}
		int count = client.root.getChildCount();
		for (int i = 0; i < count; i++) {
			TreeNode node = client.root.getChildAt(i);
			((DefaultMutableTreeNode)node).removeAllChildren();
		}
		((DefaultTreeModel) client.tree.getModel()).reload();
	}
	/**
	 * 刷新
	 */
	public void refresh(){
		if(getFileItem(client.current).getId()!=0){
			removeAll();
		}
	}
	/**
	 * 向Client中添加新的文件信息
	 * @param items
	 */
	protected void addItem(List<FileItem> items){
		for (FileItem fileItem : items) {
			addToCurrent(fileItem);
		}
	}
	/**
	 * 关闭连接
	 */
	public void close(){
		if(socket==null) throw new RuntimeException("未给Socket赋值。");
		controller.close();
	}
	protected void addToCurrent(FileItem item) {
		DefaultMutableTreeNode node ;
		if(item.getType()==FileItem.DIRECTORY){
			node = new DefaultMutableTreeNode(item,true);
			FileItem temp = new FileItem(item.getType(),"",item.getAbsolute());
			temp.setId(-1);
			node.add(new DefaultMutableTreeNode(temp) );
		}else{
			node = new DefaultMutableTreeNode(item);
		}
		((DefaultMutableTreeNode) client.current).add(node);
	}
	public void upload(){
		if(client.current==null)return ;
		FileItem item = getFileItem(client.current);
		if(item.getType()==FileItem.FILE){
			JOptionPane.showMessageDialog(client, "请选择文件夹！");
			return ;
		}
		if(item.getId()!=0&&item.getId()!=-1){
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.showOpenDialog(client);
			File file = chooser.getSelectedFile();
			if(file==null||item==null){
				return ;
			}
			controller.uploadFile(item, file);
		}
	}
	/**
	 * 下载
	 */
	public void download(){
		if(client.current==null)return ;
		FileItem item = getFileItem(client.current);
		if(item.getId()!=0&&item.getId()!=-1){
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(client);
			File file = chooser.getSelectedFile();
			if(file==null||item==null){
				return ;
			}
			controller.downloadFile(item, file);
		}
	}
	/**
	 * 将包含FileItem的组件中的FileItem对象读取出来
	 * @param node DefaultMutableTreeNode对象，分装了FileItem对象
	 * @return
	 */
	protected FileItem getFileItem(MutableTreeNode node){
		return (FileItem)((DefaultMutableTreeNode)node).getUserObject();
	}
	public ClientView getClient() {
		return client;
	}
	public void setClient(ClientView client) {
		this.client = client;
	}
} 