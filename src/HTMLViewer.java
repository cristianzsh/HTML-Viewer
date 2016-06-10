import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class HTMLViewer extends JFrame {
	private RSyntaxTextArea textArea;
	private RTextScrollPane scrollBar;
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem open, about;

	private void buildGUI() {
		this.setTitle("HTML Viewer");

		addTextArea();
		addMenus();

		this.setResizable(true);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void addMenus() {
		menuBar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");

		open = new JMenuItem("Open");
		about = new JMenuItem("About");

		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

		open.addActionListener(new OpenListener());
		about.addActionListener(new AboutListener());

		file.add(open);
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
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String url = JOptionPane.showInputDialog(null, "URL (E.g. https://www.google.com/)");

			openSite(url);
		}
	}

	class AboutListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			JOptionPane.showMessageDialog(null, "Developed by Cristian Henrique\ncristianmsbr@gmail.com");
		}
	}

	public static void main (String[] args) {
		new HTMLViewer().buildGUI();
	}
}