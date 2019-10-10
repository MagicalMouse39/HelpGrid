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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	protected JPanel hoverOver;
	protected JPanel hoverPane;
	
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
	}
	
	private int bw = 100;
	
	private int col = 0, row = 0, maxRow = 0;
	
	protected JLabel request = new JLabel();
	
	private void addButtons()
	{
		JButton helpBtn = new JButton("Help");
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
						request = new JLabel();
						request.setText("Help");
						request.setForeground(Color.RED);
						request.setBounds(0, 0, GridUI.this.getWidth(), GridUI.this.getHeight());
						GridUI.this.add(request);
					}
				}).start();
			}
		});
		this.buttons.add(helpBtn);
	
		JButton darkModeBtn = new JButton("Dark Mode " + (this.isDarkMode ? "[ON]" : "[OFF]")/*, new ImageIcon("darkMode.png")*/);
		
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
	
	public GridUI()
	{
		this.bgPane = new JPanel();
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    this.setMaximizedBounds(env.getMaximumWindowBounds());
	    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		GridUI.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(GridUI.EXIT_ON_CLOSE);
		this.setBounds(0, 0, screen.width, screen.height);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		this.bgPane.setBackground(this.isDarkMode ? new Color(33, 33, 40) : new Color(200, 200, 200));
		this.bgPane.setLayout(null);
		
		this.hoverPane = new JPanel();
		this.hoverPane.setBounds(10, 10, this.bw, this.bw);
		this.hoverPane.setOpaque(false);
		
		this.hoverOver = new JPanel();
		this.hoverOver.setBounds(10, 5, this.bw, 5);
		this.hoverOver.setBackground(Color.RED);
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
					GridUI.this.hoverOver.setBounds(GridUI.this.hoverPane.getX(), GridUI.this.hoverPane.getY() - 5, GridUI.this.bw, 5);
			}
		}).start();
		
		this.setContentPane(this.bgPane);
		
		this.addButtons();

		this.bgPane.add(this.hoverPane);
		this.bgPane.add(this.hoverOver);
		
		for (JButton b : this.buttons)
			this.bgPane.add(b);
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
				this.hoverPane.setBounds(this.hoverPane.getX(), this.hoverPane.getY() - 110, this.bw, this.bw);

		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			if (this.hoverPane.getX() > 10)
				this.hoverPane.setBounds(this.hoverPane.getX() - 110, this.hoverPane.getY(), this.bw, this.bw);
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			if (this.hoverPane.getY() / 110 < this.col && !(this.hoverPane.getY() / 110 + 1 == this.col && this.hoverPane.getX() / 110 >= this.row))
				this.hoverPane.setBounds(this.hoverPane.getX(), this.hoverPane.getY() + 110, this.bw, this.bw);
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			if ((this.hoverPane.getY() / 110 < this.col && this.hoverPane.getX() / 110 < this.maxRow - 1) || (this.hoverPane.getY() / 110 == this.col && this.hoverPane.getX() / 110 < this.row - 1))
				this.hoverPane.setBounds(this.hoverPane.getX() + 110, this.hoverPane.getY(), this.bw, this.bw);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
