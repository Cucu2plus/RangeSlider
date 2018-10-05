package rangeSlider;

import javax.swing.*;



public class RangeSlider extends JSlider
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int val_min;

	public RangeSlider(int pmin, int pmax)
	{
		this.setMinimum(pmin);
		this.setMaximum(pmax);
		//C'est joli en vertical sinon
		this.setOrientation(HORIZONTAL);
		this.setValue(pmax);
		this.val_min = pmin;
	}
	
	//Necessaire pour utiliser la nouvelle UI, pour en faire un range slider
	@Override
    public void updateUI() {
        setUI(new SliderUI(this));
    }
	

}
