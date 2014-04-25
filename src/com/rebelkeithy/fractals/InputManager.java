package com.rebelkeithy.fractals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor
{
	private FractalGame game;
	
	public InputManager(FractalGame game)
	{
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
		{
			if(keycode == Keys.UP)
			{
				game.getFractal().zoom(true);
				game.getGui().update();
			}
			if(keycode == Keys.DOWN)
			{
				game.getFractal().zoom(false);
				game.getGui().update();
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Keys.ALT_RIGHT))
		{
			if(keycode == Keys.UP)
			{
				game.getFractal().changeResolution(true);
				game.getGui().update();
			}
			if(keycode == Keys.DOWN)
			{
				game.getFractal().changeResolution(false);
				game.getGui().update();
			}
		}
		else
		{
			if(keycode == Keys.UP)
			{
				game.getFractal().move(Direction.UP);
			}
			if(keycode == Keys.DOWN)
			{
				game.getFractal().move(Direction.DOWN);
			}
			if(keycode == Keys.LEFT)
			{
				game.getFractal().move(Direction.LEFT);
			}
			if(keycode == Keys.RIGHT)
			{
				game.getFractal().move(Direction.RIGHT);
			}
		}
		
		if(keycode == Keys.Q)
		{
			game.getFractal().changeQuality(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) | Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT));
			game.getGui().update();
		}
		if(keycode == Keys.M)
		{
			game.getFractal().toggleMultisampling();
			game.getGui().update();
		}
		if(keycode == Keys.F1)
		{
			game.getGui().hide();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		System.out.println("touch " + screenY);
		if(screenY < Gdx.graphics.getHeight() - FractalGame.GuiWidth)
		{
			game.getFractal().click(screenX, screenY);
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
