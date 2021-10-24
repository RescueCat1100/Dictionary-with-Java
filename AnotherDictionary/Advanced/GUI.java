package Advanced;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import Control.Control;
import Control.Dict;
import Ultility.Word;

public class GUI {
    
    private final Control control;
	private final Dict dict;

	private final JFrame application = new JFrame("Từ điển Anh - Việt");
	
	private final JDialog add = new JDialog(application, "Thêm từ mới", true);
	private final JDialog delete = new JDialog(application, "Xóa từ", true);
	private final JDialog edit = new JDialog(application, "Chỉnh sửa từ", true); 
	
	private final JPanel app = new JPanel(new GridBagLayout()); 
	private final JPanel ulti = new JPanel(new GridLayout(1, 0, 5, 0)); 
	private final JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 0));
	private final JPanel divideColumn = new JPanel(new GridLayout(0, 2));
	private final JPanel rightColumn = new JPanel();
	private final JPanel leftColumn = new JPanel();
	private final JPanel searchBox = new JPanel();
	private final JPanel suggestionBox = new JPanel();


	private final JButton addButton = new JButton();
	private final JButton deleteButton = new JButton();
	private final JButton editButton = new JButton();
	private final JButton ttsButton = new JButton();

	private final JLabel editTarget = new JLabel("Từ: ");

	JTextField wordSearch = new JTextField("");
	JTextArea definition = new JTextArea("");
	JList<String> wordSuggest = new JList<>();
	JScrollPane leftScroll = new JScrollPane(wordSuggest);
	JScrollPane rightScroll = new JScrollPane(definition);


	public GUI(Control control, Dict dict) {
		this.control = control;
		this.dict = dict;
	}


	public void gridConfig(GridBagConstraints c, int gridx, int gridy, int weightx, int weighty) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.weightx = weightx;
		c.weighty = weighty;
	}

	public void addBorder(JTextArea tA) {
		tA.setBorder(BorderFactory.createLineBorder(new Color(204, 255, 255)));
	}

	public void setButton(JButton button, String string) {
		ImageIcon icon = new ImageIcon(string);
		Image img = icon.getImage();
		button.setIcon(new ImageIcon(img.getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
	}
	
	public void addAction() {
		addButton.addActionListener(e -> add.setVisible(true));

		deleteButton.addActionListener(e -> {
			if (wordSuggest.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(application, "Vui lòng chọn một từ để bắt đầu xóa");
			} else delete.setVisible(true);
		});

		editButton.addActionListener(e -> {
			if (wordSuggest.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(application, "Vui lòng chọn một từ để bắt đầu chỉnh sửa");
			} else {
				editTarget.setText(wordSuggest.getSelectedValue());
				edit.setVisible(true);
			}
		});

		wordSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			public void changedUpdate(DocumentEvent e) {}
            
			public void insertUpdate(DocumentEvent e) {
				String[] list = control.inputSearch(wordSearch.getText(), dict.getDict());
				if(wordSearch.getText().isEmpty()) {
					list = new String[dict.getDict().limitWord()];
					for (int i = 0; i < dict.getDict().limitWord(); ++i) {
						list[i] = dict.getDict().indexWord(i).getinput();
					}
				}		
				wordSuggest.setListData(list);
				if(control.outputSearch(wordSearch.getText(), dict.getDict()) != "") {
					definition.setText(control.outputSearch(wordSearch.getText(), dict.getDict()));
				} else {
					API tran = new API();
					try {
						definition.setText(tran.translate("en", "vi", wordSearch.getText()));
					} catch (IOException ex) {}
				}
			}
			public void removeUpdate(DocumentEvent e) {}
		});

		wordSuggest.addListSelectionListener(e -> {
			if(e.getValueIsAdjusting()) {
				return;
			}
			definition.setText(control.outputSearch(wordSuggest.getSelectedValue(), dict.getDict()));
		});
	}


	public void add() {
		setButton(addButton, "./Pic/add.png");
        add.setSize(300, 300);
        add.setLocationRelativeTo(null);
        add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel main = new JPanel(new GridBagLayout());
        JPanel input = new JPanel(new GridLayout(2, 0));
        JPanel pronounce  = new JPanel(new GridLayout(2, 0));
        JPanel output = new JPanel(new GridBagLayout());

        JLabel targ = new JLabel("Từ:");
        JLabel pron = new JLabel("Phát âm: ");
        JLabel expl = new JLabel("Giải nghĩa:");

        JTextField inputField = new JTextField("");
        JTextField pronField = new JTextField("");
        JTextArea outputField = new JTextArea("");

        addBorder(outputField);
        outputField.setLineWrap(true);
        outputField.setWrapStyleWord(true);

        JButton addConfirm = new JButton("Xác nhận");
        addConfirm.setBackground(new Color(204, 255, 255));

        input.add(targ);
        input.add(inputField);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        gridConfig(c, 0, 3, 0, 0);
        main.add(addConfirm, c);

        gridConfig(c, 0, 1, 1, 1);
        input.add(inputField, c);

        gridConfig(c, 0, 0, 1, 0);
        main.add(input, c);

        pronounce .add(pron);
        pronounce .add(pronField);

        gridConfig(c, 0, 1, 1, 0);
        main.add(pronounce , c);

        gridConfig(c, 0, 0, 1, 0);
        output.add(expl, c);

        gridConfig(c, 0, 1, 1, 1);
        output.add(outputField, c);

        gridConfig(c, 0, 2, 1, 1);
        main.add(output, c);

        add.add(main);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pronField.requestFocus();
                }
            }
        });

        pronField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    outputField.requestFocus();
                }
            }
        });

        addConfirm.addActionListener(e -> {
            if (inputField.getText().length() == 0) {
                return;
            }

            if (outputField.getText().length() == 0 && pronField.getText().length() == 0) {
                return;
            }

            String target = inputField.getText();
            String explain = "/" + pronField.getText() + "/\t" + outputField.getText();
            for (int i = explain.length() - 1; i >= 0; --i) {
                if (explain.charAt(i) == '\n') {
                    explain = explain.substring(0, explain.length() - 1);
                } else {
                    break;
                }
            }
            explain+="\n";

            Word word = new Word(target, explain);

            if (dict.getDict().existed(word)) {
                JOptionPane.showMessageDialog(add, "Từ này đã tồn tại");
                return;
            }

            dict.getDict().addWord(word);
            dict.add(target, explain);
            inputField.setText("");
            pronField.setText("");
            outputField.setText("");
        });
    }

	/**
	 * Remove word feature.
	 */
	public void delete() {
		setButton(deleteButton,"./Pic/delete.png");

		delete.setSize(280, 75);
		delete.setResizable(false);
		delete.setLocationRelativeTo(null);
		delete.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel main = new JPanel();
		main.setLayout(null);
		main.setPreferredSize(new Dimension(280, 75));

		JLabel msg = new JLabel("Xác nhận xóa từ ?", SwingConstants.CENTER);
		JPanel msgPanel = new JPanel(new GridLayout(0, 1));
		JButton yes = new JButton("Có");
		JButton no = new JButton("Không");

		msgPanel.setBounds(0, 0, 280, 40);
		yes.setBounds(35, 40, 100, 25);
		no.setBounds(155, 40, 100, 25);

		msgPanel.add(msg);
		main.add(msgPanel);
		main.add(yes);
		main.add(no);
		delete.add(main);
		delete.pack();

		yes.addActionListener(e -> {			
			dict.remove(wordSuggest.getSelectedValue(), definition.getText());
			delete.dispose();
		});

		no.addActionListener(e -> delete.dispose());
	}
	
	/**
	 * Edit a specific word window.
	 */
	public void edit() {
		setButton(editButton, "./Pic/edit.png");

		edit.setSize(300, 300);
		edit.setLocationRelativeTo(null);
		edit.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel main = new JPanel(new GridBagLayout());
		JPanel pronounce  = new JPanel(new GridLayout(2, 0));
		JPanel output = new JPanel(new GridBagLayout());

		JLabel pron = new JLabel(" Phát âm: ");
		JLabel expl = new JLabel(" Giải nghĩa:");

		JTextField pronField = new JTextField();
		JTextArea outputField = new JTextArea();

		addBorder(outputField);
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);

		JButton updateConfirm = new JButton("Xác nhận");

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		gridConfig(c, 0, 3, 0, 0);
		main.add(updateConfirm, c);

		gridConfig(c, 0, 0, 1, 0);
		main.add(editTarget, c);

		pronounce .add(pron);
		pronounce .add(pronField);

		gridConfig(c, 0, 1, 1, 0);
		main.add(pronounce , c);

		gridConfig(c, 0, 0, 1, 0);
		output.add(expl, c);

		gridConfig(c, 0, 1, 1, 1);
		output.add(outputField, c);

		gridConfig(c, 0, 2, 1, 1);
		main.add(output, c);

		edit.add(main);

		pronField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					outputField.requestFocus();
				}
			}
		});

		updateConfirm.addActionListener(e -> {
			if (outputField.getText().length() == 0 && pronField.getText().length() == 0) {
				return;
			}

			String explain = "/" + pronField.getText() + "/\t" + outputField.getText();
			int index = -1;
			for (int i = 0; i < dict.getDict().limitWord(); ++i) {
				if (dict.getDict().indexWord(i).getinput().equals(wordSuggest.getSelectedValue())) {
					index = i;
					break;
				}
			}
			dict.getDict().indexWord(index).setoutput(explain);
			dict.edit(wordSuggest.getSelectedValue(), definition.getText(), explain);
			pronField.setText("");
			outputField.setText("");
		});
	}

	public void setTtsButton() {
		setButton(ttsButton, "./Pic/audio.png");
		ttsButton.addActionListener(e -> {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		    try {
				Voice voice;
				VoiceManager vm = VoiceManager.getInstance();
				voice = vm.getVoice("kevin16");
				voice.allocate();
		    	if(wordSuggest.getSelectedValue() != null){
					try{
						voice.speak(wordSuggest.getSelectedValue());
					} catch(Exception Exception){}
				} else{
					try{
						voice.speak(wordSearch.getText());
					} catch(Exception Exception){}
				}
			} catch (Exception ev) {
		    	ev.printStackTrace();
			}
        });
	}

	public void deploy() {

		add();
		delete();
		edit();
		setTtsButton();

		ImageIcon icon = new ImageIcon("./Pic/find.png");
		Image img = icon.getImage();
		icon = new ImageIcon(img.getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		JLabel findLabel = new JLabel(icon, JLabel.CENTER);

		definition.setLineWrap(true);
		definition.setWrapStyleWord(true);
		GridBagConstraints c = new GridBagConstraints();

		leftScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rightScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		definition.setEditable(false);

		addAction();

		divideColumn.add(leftColumn);
		divideColumn.add(rightColumn);
		buttonPanel.add(addButton);
		buttonPanel.add(ttsButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);

		ttsButton.setBackground(new Color(204, 255, 255));
		addButton.setBackground(new Color(204, 255, 255));
		editButton.setBackground(new Color(204, 255, 255));
		deleteButton.setBackground(new Color(204, 255, 255));

		rightColumn.setLayout(new GridBagLayout());
		leftColumn.setLayout(new GridBagLayout());
		searchBox.setLayout(new GridBagLayout());
		suggestionBox.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		gridConfig(c, 0, 0, 0, 0);
		c.gridy = 2;   
		rightColumn.add(buttonPanel, c);

		gridConfig(c, 0, 1, 1, 1);
		rightColumn.add(rightScroll, c);

		gridConfig(c, 0, 0, 1, 1);
		searchBox.add(wordSearch, c);

		gridConfig(c, 1, 0, 0, 1);
		searchBox.add(findLabel, c);

		gridConfig(c, 0, 0, 1, 0);
		leftColumn.add(searchBox, c);

		gridConfig(c, 0, 1, 1, 1);
		leftColumn.add(suggestionBox, c);

		gridConfig(c, 1, 1, 1, 1);
		suggestionBox.add(leftScroll, c);

		gridConfig(c, 0, 0, 1, 0);
		app.add(ulti, c);

		gridConfig(c, 0, 1, 1, 1);
		app.add(divideColumn, c);

		application.setSize(700, 700);
		try {
			application.setIconImage(ImageIO.read(new File("./Pic/icon.png")));
		} catch (Exception e) {}
		application.setLocationRelativeTo(null);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setBackground(Color.YELLOW);
		application.add(app);
		application.setVisible(true);
	}
}
