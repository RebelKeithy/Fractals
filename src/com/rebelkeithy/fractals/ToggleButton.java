package com.rebelkeithy.fractals;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ToggleButton extends TextButton
{
	private boolean isPressed;

	public ToggleButton(String text, Skin skin) 
	{
		super(text, skin);
	}
	
	public void click()
	{
		isPressed = !isPressed;
	}
	
	@Override
	public boolean isPressed()
	{
		return isPressed;
	}

}
