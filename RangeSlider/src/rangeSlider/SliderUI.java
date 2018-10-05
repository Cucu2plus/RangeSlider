package rangeSlider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class SliderUI extends BasicSliderUI
{

	protected Rectangle thumbRect_min = null;

	boolean dragMax = false;
	boolean dragMin = false;
	boolean dragInt = false;

	int save_int = -1;

	int x_prev;

	RangeSlider Rslider = null;

	public SliderUI(RangeSlider b)
	{
		super(b);
		Rslider = b;
	}

	@Override
	public void installUI(JComponent c)
	{
		thumbRect_min = new Rectangle();
		super.installUI(c);
	}

	@Override
	public void calculateThumbSize()
	{
		this.thumbRect_min.setSize(this.thumbRect.width, this.thumbRect.height);
		super.calculateThumbSize();
	}

	// Pour peindre le nouveau rectangle, j'arrive pas a trouver la bonne couleur
	@Override
	public void paint(Graphics graph, JComponent comp)
	{
		super.paint(graph, comp);
		graph.setColor(Color.lightGray);
		graph.fillRect(thumbRect_min.x, thumbRect_min.y, thumbRect_min.width, thumbRect_min.height);
		graph.draw3DRect(thumbRect_min.x, thumbRect_min.y, thumbRect_min.width, thumbRect_min.height, true);
		graph.dispose();
	}

	public void setThumbMin(int x, int y)
	{
		thumbRect_min.setLocation(x, y);
		this.slider.repaint();
	}

	protected TrackListener createTrackListener(JSlider slider)
	{
		return new RangeListener();
	}

	public class RangeListener extends TrackListener
	{
		public void mousePressed(MouseEvent e)
		{
			// On recup la positions de la souris, et on check pour voir dans quel cas on
			// est
			int x = e.getX();
			int y = e.getY();

			if (thumbRect_min.contains(x, y))
			{
				dragMin = true;
			} else if (thumbRect.contains(x, y))
			{
				dragMax = true;

			} else if (trackRect.contains(x, y) && x < thumbRect_min.x)
			{
				if (Rslider.val_min != slider.getMinimum())
				{
					Rslider.val_min--;
					setThumbMin(xPositionForValue(Rslider.val_min), thumbRect.y);
					slider.setValue(slider.getValue()-1);

				}

			} else if (trackRect.contains(x, y) && x > thumbRect.x)
			{
				if (slider.getValue() != slider.getMinimum())
				{
					Rslider.val_min ++;
					setThumbMin(xPositionForValue(Rslider.val_min), thumbRect.y);
					slider.setValue(slider.getValue()+1);

				}

			} else if (trackRect.contains(x, y))
			{
				dragInt = true;
				save_int = valueForXPosition(x);
			}

		}

		public void mouseReleased(MouseEvent e)
		{
			dragMax = false;
			dragMin = false;
			dragInt = false;
		}

		public void mouseDragged(MouseEvent e)
		{
			// Recup de la pos de la souris, plus les pos de l'intervalle
			int x = e.getX();
			int track_max = trackRect.x + trackRect.width;
			int track_min = trackRect.x;

			// Cas ou on fait bouger le bouton du max
			if (dragMax)
			{
				if (x < track_max && x > (thumbRect_min.x + thumbRect_min.width))
				{
					if (valueForXPosition(x) <= Rslider.val_min)
					{
						slider.setValue(Rslider.val_min + 1);
					} else
					{
						slider.setValue(valueForXPosition(x));
					}
				}
			}

			// Cas ou on fait bouger le bouton du min
			if (dragMin)
			{
				if (x > track_min && x < (thumbRect.x))
				{
					if (valueForXPosition(x) >= slider.getValue())
					{
						Rslider.val_min = slider.getValue() - 1;
					} else
					{

						Rslider.val_min = valueForXPosition(x);
					}
					setThumbMin(xPositionForValue(Rslider.val_min), thumbRect.y);
				}
			}

			// Cas ou on fait bouger l'intervalle en entier
			if (dragInt)
			{
				int intPourPos = valueForXPosition(x);

				if (intPourPos != save_int)
				{
					int diff = intPourPos - save_int;

					if ((slider.getValue() + diff) <= slider.getMaximum()
							&& (Rslider.val_min + diff) >= slider.getMinimum())
					{
						save_int = intPourPos;
						slider.setValue(slider.getValue() + diff);
						Rslider.val_min = Rslider.val_min + diff;
						setThumbMin(xPositionForValue(Rslider.val_min), thumbRect.y);
					}

				}
			}

		}

	}

}
