package com.rebelkeithy.fractals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.rebelkeithy.fractals.sets.BurningShip;
import com.rebelkeithy.fractals.sets.FractalSet;
import com.rebelkeithy.fractals.sets.MandelbrotSet;
import com.rebelkeithy.fractals.sets.Multibrot;

public class Gui implements EventListener
{
	private FractalGame game;
	
	private Stage stage;
	private Skin skin;
	
	private Button buttonH;
	private Button zoomIn;
	private Button zoomOut;
	private Button iterUp;
	private Button iterDown;
	private Label iterCount;
	private Button reset;
	
	private SelectBox<FractalSet> selectFractal;
	
	private Slider quality;
	
	private boolean hide = false;
	
	public Gui(FractalGame game)
	{
		this.game = game;
		
		stage = new Stage();
		//stage.getViewport().setWorldHeight(FractalGame.GuiWidth);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		buttonH = new ToggleButton("(M)s", skin);
		buttonH.setPosition(5, 5);
		buttonH.addListener(this);
		
		reset = new TextButton("R", skin);
		reset.setWidth(reset.getHeight());
		reset.setPosition(Gdx.graphics.getWidth() - reset.getWidth() - 5, 5);
		reset.addListener(this);
		
		zoomIn = new TextButton("+", skin);
		zoomIn.setWidth(zoomIn.getHeight());
		zoomIn.setPosition(reset.getX() - zoomIn.getWidth() - 5, 5);
		zoomIn.addListener(this);
		
		zoomOut = new TextButton("-", skin);
		zoomOut.setWidth(zoomOut.getHeight());
		zoomOut.setPosition(zoomIn.getX() - zoomOut.getWidth() - 5, 5);
		zoomOut.addListener(this);
		
		iterUp = new TextButton("^", skin);
		iterUp.setWidth(zoomOut.getHeight());
		iterUp.setPosition(zoomOut.getX() - iterUp.getWidth() - 7, 5);
		iterUp.addListener(this);
		
		iterCount = new Label("1000", skin);
		iterCount.setPosition(iterUp.getX() - iterCount.getWidth() - 5, 5);
		
		iterDown = new TextButton("v", skin);
		iterDown.setWidth(iterDown.getHeight());
		iterDown.setPosition(iterCount.getX() - iterDown.getWidth() - 5, 5);
		iterDown.addListener(this);
		
		quality = new Slider(0, 2, 1, false, skin);
		quality.setWidth(60);
		quality.setPosition(buttonH.getWidth() + 10, 10);
		quality.addListener(this);
		
		
		selectFractal = new SelectBox<FractalSet>(skin);
		selectFractal.setItems(new MandelbrotSet(), new BurningShip(), new Multibrot(3));
		selectFractal.setWidth(150);
		selectFractal.setPosition(quality.getX() + quality.getWidth() + 5, 5);
		//selectFractal.setPosition(100 + quality.getWidth() + 5, 5);
		selectFractal.addListener(this);
		
		stage.addActor(selectFractal);
		stage.addActor(reset);
		stage.addActor(zoomIn);
		stage.addActor(zoomOut);
		stage.addActor(iterUp);
		stage.addActor(iterCount);
		stage.addActor(iterDown);
		stage.addActor(buttonH);
		stage.addActor(quality);
		
		
		update();
		
	}
	
	public void hide()
	{
		hide = !hide;
	}
	
	public void draw()
	{
		if(!hide)
			stage.draw();
	}

	@Override
	public boolean handle(Event event) 
	{
		if(event instanceof InputEvent)
		{
			InputEvent ievent = (InputEvent)event;
			Actor actor = event.getListenerActor();
			
			if(actor == buttonH && ievent.getType() == Type.touchDown)
				((ToggleButton) buttonH).click();
			
			if((actor == buttonH || actor == zoomIn || actor == zoomOut || actor == quality || actor == reset || actor == iterUp || actor == iterDown ) && ievent.getType() == Type.touchDown)
				return true;
			
			if(actor == buttonH && ievent.getType() == Type.touchUp)
			{
				buttonH.toggle();
				game.getFractal().toggleMultisampling();
			}
			if(actor == zoomIn && ievent.getType() == Type.touchUp)
			{
				game.getFractal().zoom(true);
				update();
			}
			if(actor == zoomOut && ievent.getType() == Type.touchUp)
			{
				game.getFractal().zoom(false);
				update();
			}
			if(actor == reset && ievent.getType() == Type.touchUp)
				game.getFractal().resetView();
			if(actor == iterUp && ievent.getType() == Type.touchUp)
			{
				game.getFractal().changeResolution(true);
				iterCount.setText("i:" + game.getFractal().getMaxIters());
			}
			if(actor == iterDown && ievent.getType() == Type.touchUp)
			{
				game.getFractal().changeResolution(false);
				iterCount.setText("i:" + game.getFractal().getMaxIters());
			}
			if(actor == quality && ievent.getType() == Type.touchUp)
			{
				if(quality.getValue() == 0)
					game.getFractal().setQuality(3);
				else if(quality.getValue() == 1)
					game.getFractal().setQuality(1);
				else if(quality.getValue() == 2)
					game.getFractal().setQuality(1/3.0);
			}
		}
		
		if(event instanceof ChangeEvent)
		{
			Actor actor = event.getListenerActor();
			
			if(actor == selectFractal)
			{
				FractalSet f = selectFractal.getSelected();
				game.getFractal().setFractal(f);
			}
		}
		
		return false;
	}

	public InputProcessor getInputHander() 
	{
		return stage;
	}

	public void resize(int width, int height) 
	{
		stage.getViewport().update(width, height);
	}

	public void act() 
	{
		stage.act();
	}

	public void update() 
	{
		System.out.println("update");
		iterCount.setText("i:" + game.getFractal().getMaxIters());
		
		if(game.getFractal().getQuality() == 3)
			quality.setValue(0);
		else if(game.getFractal().getQuality() == 1)
			quality.setValue(1);
		else
			quality.setValue(2);
	}
	
}
