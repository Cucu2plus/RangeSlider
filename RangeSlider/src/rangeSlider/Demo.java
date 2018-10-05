package rangeSlider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class Demo extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Notre slider, min = 1, max = 7
	private RangeSlider slider = new RangeSlider(1, 7);
	private JLabel label_slider = new JLabel();

	public Demo()
	{


		
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		};
		addWindowListener(l);

		this.setTitle("Range Slider");

		// Timer qui permet d'afficher les valeurs du slider
		Timer t = new javax.swing.Timer(1000 / 60, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				label_slider.setText("Valeur min : " + slider.val_min+" | Valeur max : " + slider.getValue() );
			}
		});
		t.start();

		JPanel panel = new JPanel();
		panel.add(slider);
		panel.add(label_slider);

		this.setContentPane(panel);
		this.setSize(400, 100);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new Demo();
			}
		});
	}

}
