/**
 * 
 */
package it.magicorp.helpgrid.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import it.magicorp.helpgrid.SoundUtils;

/**
 * @author Magical
 *
 */
public class GridUI extends JFrame implements KeyListener
{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -676719832492120358L;
	
	public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	protected ArrayList<JButton> buttons = new ArrayList<JButton>();
	private ArrayList<SimpleEntry<JButton, SimpleEntry<Integer, Integer>>> grid = new ArrayList<SimpleEntry<JButton, SimpleEntry<Integer, Integer>>>();
	protected JPanel bgPane;
	protected JPanel hoverTop;
	protected JPanel hoverLeft;
	protected JPanel hoverRight;
	protected JPanel hoverBottom;
	protected JPanel hoverPane;
	protected JTextPane messageArea;
	
	private enum Language
	{
		ENGLISH, ITALIAN
	}
	
	public static GridUI instance = new GridUI();
	
	public boolean isDarkMode = true;
	
	private void setProperties()
	{
		this.bgPane.setBackground(this.isDarkMode ? new Color(33, 33, 40) : new Color(200, 200, 200));
		for (JButton b : this.buttons)
		{
			b.setBorder(BorderFactory.createLineBorder(this.isDarkMode ? Color.WHITE : Color.BLACK, 2));
			b.setBackground(this.isDarkMode ? new Color(22, 22, 30) : new Color(200, 200, 200));
			b.setForeground(this.isDarkMode ? new Color(200, 200, 200) : new Color(33, 33, 40));
			if (b.getText().contains("Dark Mode"))
				b.setText("Dark Mode " + (GridUI.this.isDarkMode ? "[ON]" : "[OFF]"));
		}
		
		this.messageArea.setBackground(this.isDarkMode ? new Color(33, 33, 40) : new Color(200, 200, 200));
	}
	
	private int bw = 200;
	
	private int col = 0, row = 0, maxRow = 0;
	
	private Language lang = Language.ITALIAN;
	
	private HashMap<String, String> names = new HashMap<String, String>();
	
	private void addButtons()
	{
		JButton helpBtn = new JButton(this.lang == Language.ENGLISH ? "Help" : "Aiuto", new ImageIcon("Icons/help.png"));
		helpBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						if (SoundUtils.isPlaying)
							return;
						SoundUtils.play(0);
						messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Help" : "Aiuto");
					}
				}).start();
			}
		});
		this.buttons.add(helpBtn);
		this.names.put("Help", "Aiuto");
	
		JButton darkModeBtn = new JButton("Dark Mode " + (this.isDarkMode ? "[ON]" : "[OFF]"), new ImageIcon("Icons/nightmode.png"));
		
		darkModeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GridUI.this.isDarkMode = !GridUI.this.isDarkMode;
				GridUI.this.setProperties();
			}
		});
		this.buttons.add(darkModeBtn);
		
		JButton yesBtn = new JButton(this.lang == Language.ENGLISH ? "Yes" : "Si", new ImageIcon("Icons/thumbup.png"));
		yesBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Yes" : "Si");
			}
		});
		this.buttons.add(yesBtn);
		this.names.put("Yes", "Si");
		
		JButton noBtn = new JButton("No", new ImageIcon("Icons/thumbdown.png"));
		noBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GridUI.this.messageArea.setText("No");
			}
		});
		this.buttons.add(noBtn);
		
		JButton bathroomBtn = new JButton(this.lang == Language.ENGLISH ? "Bathroom" : "Bagno", new ImageIcon("Icons/bathroom.png"));
		bathroomBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SoundUtils.play(0);
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Bathroom" : "Bagno");
			}
		});
		this.buttons.add(bathroomBtn);
		this.names.put("Bathroom", "Bagno");
		
		JButton foodBtn = new JButton(this.lang == Language.ENGLISH ? "Food" : "Cibo", new ImageIcon("Icons/food.png"));
		foodBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SoundUtils.play(1);
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "I'm hungry" : "Ho fame");
			}
		});
		this.buttons.add(foodBtn);
		this.names.put("Food", "Cibo");
		
		JButton drinkBtn = new JButton(this.lang == Language.ENGLISH ? "Drink" : "Acqua", new ImageIcon("Icons/water.png"));
		drinkBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SoundUtils.play(1);
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "I'm thirsty" : "Ho sete");
			}
		});
		this.buttons.add(drinkBtn);
		this.names.put("Drink", "Acqua");
		
		JButton tooHot = new JButton(this.lang == Language.ENGLISH ? "Too Hot" : "Troppo Caldo", new ImageIcon("Icons/toohot.png"));
		tooHot.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Too Hot!" : "Troppo Caldo!");
				SoundUtils.play(1);
			}
		});
		this.buttons.add(tooHot);
		this.names.put("Too Hot", "Troppo Caldo");
		
		JButton tooCold = new JButton(this.lang == Language.ENGLISH ? "Too Cold" : "Troppo Freddo", new ImageIcon("Icons/toocold.png"));
		tooCold.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Too Cold!" : "Troppo Freddo!");
				SoundUtils.play(1);
			}
		});
		this.buttons.add(tooCold);
		this.names.put("Too Cold", "Troppo Freddo");
		
		JButton lightOn = new JButton(this.lang == Language.ENGLISH ? "Light ON" : "Accendi Luce", new ImageIcon("Icons/lighton.png"));
		lightOn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Turn the light ON" : "Accendi la Luce!");
				SoundUtils.play(1);
			}
		});
		this.buttons.add(lightOn);
		this.names.put("Light ON", "Accendi Luce");
		
		JButton lightOff = new JButton(this.lang == Language.ENGLISH ? "Light OFF" : "Spegni Luce", new ImageIcon("Icons/lightoff.png"));
		lightOff.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GridUI.this.messageArea.setText(GridUI.this.lang == Language.ENGLISH ? "Turn the light OFF" : "Spegni la Luce!");
				SoundUtils.play(1);
			}
		});
		this.buttons.add(lightOff);
		this.names.put("Light OFF", "Spegni Luce");
		
		JButton langBtn = new JButton(this.lang == Language.ENGLISH ? "Cambia a ITA" : "Change to ENG", new ImageIcon("Icons/italianflag.png"));
		langBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (JButton b : GridUI.this.buttons)
				{
					if (GridUI.this.lang == Language.ENGLISH)
					{
						for (String n : GridUI.this.names.keySet())	
							if (b.getText().equals(n))
								b.setText(GridUI.this.names.get(n));
						langBtn.setIcon(new ImageIcon("Icons/italianflag.png"));
					}
					else if (GridUI.this.lang == Language.ITALIAN)
					{
						for (String n : GridUI.this.names.values())
							if (b.getText().equals(n))
								for (String k : GridUI.this.names.keySet())
									if (GridUI.this.names.get(k).equals(n))
										b.setText(k);
						langBtn.setIcon(new ImageIcon("Icons/englishflag.png"));
					}
				}
				GridUI.this.lang = GridUI.this.lang == Language.ENGLISH ? Language.ITALIAN : Language.ENGLISH;
				GridUI.this.messageArea.setText("");
			}
		});
		this.buttons.add(langBtn);
		this.names.put("Cambia a ITA", "Change to ENG");
		
		/*
		 * Buttons property setter
		 */
		for (int i = 0; i < this.buttons.size(); i++)
		{
			if (bw * row + 10 * row > this.getWidth() - bw - 10)
			{
				col++;
				maxRow = row;
				row = 0;
			}
			
			this.grid.add(new SimpleEntry<JButton, SimpleEntry<Integer, Integer>>(this.buttons.get(i), new SimpleEntry<Integer, Integer>(row, col)));
				
			JButton b = this.buttons.get(i);
			b.setBorder(BorderFactory.createLineBorder(this.isDarkMode ? Color.WHITE : Color.BLACK, 2));
			b.setBackground(this.isDarkMode ? new Color(22, 22, 30) : new Color(200, 200, 200));
			b.setForeground(GridUI.this.isDarkMode ? new Color(200, 200, 200) : new Color(33, 33, 40));
			b.setBounds(10 + row * bw + row * 10, 10 + col * bw + col * 10, bw, bw);
			if (b.getText().contains("Dark Mode"))
				b.setText("Dark Mode " + (this.isDarkMode ? "[ON]" : "[OFF]"));

			row++;
		}
		
		
	}
	
	private GridUI()
	{
		this.bgPane = new JPanel();
		
		this.messageArea = new JTextPane();
		StyledDocument doc = this.messageArea.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		this.messageArea.setDocument(doc);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    this.setMaximizedBounds(env.getMaximumWindowBounds());
	    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		GridUI.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(GridUI.EXIT_ON_CLOSE);
		this.setBounds(0, 0, screen.width, screen.height);
		this.setFocusable(true);
		this.setUndecorated(true);
		this.addKeyListener(this);
		
		this.bgPane.setBackground(this.isDarkMode ? new Color(33, 33, 40) : new Color(200, 200, 200));
		this.bgPane.setLayout(null);
		
		this.hoverPane = new JPanel();
		this.hoverPane.setBounds(10, 10, this.bw, this.bw);
		this.hoverPane.setOpaque(false);

		this.hoverTop = new JPanel();
		this.hoverTop.setBounds(10, 5, this.bw, 5);
		this.hoverTop.setBackground(Color.RED);

		this.hoverBottom = new JPanel();
		this.hoverBottom.setBounds(5, 5, 5, this.bw + 5);
		this.hoverBottom.setBackground(Color.RED);

		this.hoverLeft = new JPanel();
		this.hoverLeft.setBounds(5, this.bw + 10, this.bw + 10, 5);
		this.hoverLeft.setBackground(Color.RED);

		this.hoverRight = new JPanel();
		this.hoverRight.setBounds(this.bw + 10, 5, 5, this.bw + 5);
		this.hoverRight.setBackground(Color.RED);
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					GridUI.this.hoverTop.setBounds(GridUI.this.hoverPane.getX(), GridUI.this.hoverPane.getY() - 5, GridUI.this.bw, 5);
					GridUI.this.hoverLeft.setBounds(GridUI.this.hoverPane.getX() - 5, GridUI.this.hoverPane.getY() - 5, 5, GridUI.this.bw + 10);
					GridUI.this.hoverRight.setBounds(GridUI.this.hoverPane.getX() + GridUI.this.bw, GridUI.this.hoverPane.getY() - 5, 5, GridUI.this.bw + 10);
					GridUI.this.hoverBottom.setBounds(GridUI.this.hoverPane.getX(), GridUI.this.hoverPane.getY() + GridUI.this.bw, GridUI.this.bw, 5);
				}
			}
		}).start();
		
		this.addButtons();
		
		this.messageArea.setBounds(0, this.getHeight() / 2 + 50, this.getWidth(), this.getHeight() / 2 - 200);
		this.messageArea.setBackground(this.isDarkMode ? new Color(33, 33, 40) : new Color(200, 200, 200));
		this.messageArea.setForeground(new Color(255, 87, 33));
		this.messageArea.setFont(this.messageArea.getFont().deriveFont(128f));

		this.bgPane.add(this.hoverPane);
		this.bgPane.add(this.hoverTop);
		this.bgPane.add(this.hoverLeft);
		this.bgPane.add(this.hoverRight);
		this.bgPane.add(this.hoverBottom);
		
		for (JButton b : this.buttons)
			this.bgPane.add(b);
		
		this.bgPane.add(this.messageArea);
		
		this.setContentPane(this.bgPane);
	}
	
	public void showUI()
	{
		this.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			for (JButton b : this.buttons)
				if (b.getX() == this.hoverPane.getX() && b.getY() == this.hoverPane.getY())
					b.doClick();
		
		if (e.getKeyCode() == KeyEvent.VK_UP)
			if (this.hoverPane.getY() > 10)
				this.hoverPane.setBounds(this.hoverPane.getX(), this.hoverPane.getY() - (this.bw + 10), this.bw, this.bw);

		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			if (this.hoverPane.getX() > 10)
				this.hoverPane.setBounds(this.hoverPane.getX() - (this.bw + 10), this.hoverPane.getY(), this.bw, this.bw);
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			if (this.hoverPane.getY() / (this.bw + 10) < this.col && !(this.hoverPane.getY() / (this.bw + 10) + 1 == this.col && this.hoverPane.getX() / (this.bw + 10) >= this.row))
				this.hoverPane.setBounds(this.hoverPane.getX(), this.hoverPane.getY() + (this.bw + 10), this.bw, this.bw);
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			if ((this.hoverPane.getY() / (this.bw + 10) < this.col && this.hoverPane.getX() / (this.bw + 10) < this.maxRow - 1) || (this.hoverPane.getY() / (this.bw + 10) == this.col && this.hoverPane.getX() / (this.bw + 10) < this.row - 1))
				this.hoverPane.setBounds(this.hoverPane.getX() + (this.bw + 10), this.hoverPane.getY(), this.bw, this.bw);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
