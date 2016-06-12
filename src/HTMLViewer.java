import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.KeyStroke;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
* HTML page viewer
* @author Cristian Henrique (cristianmsbr@gmail.com)
*/

public class HTMLViewer extends JFrame {
	private RSyntaxTextArea textArea;
	private RTextScrollPane scrollBar;
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem open, save, exit, about;
	private JFileChooser jfc;
	private String url;

	private void buildGUI() {
		this.setTitle("HTML Viewer");

		try {
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (Exception ex) { ex.printStackTrace(); }

		addTextArea();
		addMenus();

		this.setResizable(true);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
		this.setVisible(true);
	}

	private void addMenus() {
		menuBar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");

		open = new JMenuItem("Open");
		about = new JMenuItem("About");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");

		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

		open.addActionListener(new OpenListener());
		about.addActionListener(new AboutListener());
		save.addActionListener(new SaveListener());
		exit.addActionListener(new ExitListener());

		file.add(open);
		file.add(save);
		file.add(exit);
		help.add(about);
		menuBar.add(file);
		menuBar.add(help);
		this.setJMenuBar(menuBar);
	}

	private void addTextArea() {
		textArea = new RSyntaxTextArea();
		textArea.setHighlightCurrentLine(true);
		textArea.setAnimateBracketMatching(true);
		textArea.setAntiAliasingEnabled(true);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);

		scrollBar = new RTextScrollPane(textArea);
		scrollBar.setBorder(null);
		scrollBar.setLineNumbersEnabled(true);
		scrollBar.setViewportView(textArea);

		this.getContentPane().add(scrollBar);
	}

	private void openSite(String url) {
		try {
			URL site = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(site.openStream()));
			String line;
			textArea.setText("");

			while ((line = br.readLine()) != null) {
				textArea.append(line + "\n");
			}
			br.close();
			textArea.discardAllEdits();
			this.setTitle(url + " - HTML Viewer");
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			url = JOptionPane.showInputDialog(null, "URL (E.g. https://www.google.com/)");

			openSite(url);
		}
	}

	class AboutListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			JOptionPane.showMessageDialog(null, "Developed by Cristian Henrique\ncristianmsbr@gmail.com");
		}
	}

	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			jfc = new JFileChooser();

			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter fw = new FileWriter(jfc.getSelectedFile());
					fw.write(textArea.getText());
					fw.close();
				} catch (Exception ex) { ex.printStackTrace(); }
			}
		}
	}

	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.exit(0);
		}
	}

	public static void main (String[] args) {
		new HTMLViewer().buildGUI();
	}
}