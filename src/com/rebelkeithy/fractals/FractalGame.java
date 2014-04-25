package com.rebelkeithy.fractals;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.fractals.sets.MandelbrotSet;

public class FractalGame implements ApplicationListener 
{
	public static int GuiWidth = 50;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private float h;
	private float w;

	private Fractal fractal;
	
	private Gui gui;
	
	@Override
	public void create() 
	{		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();

		fractal = new Fractal(new MandelbrotSet(), 50);
		gui = new Gui(this);
		
		InputMultiplexer inputs = new InputMultiplexer(gui.getInputHander(), new InputManager(this));
		
		Gdx.input.setInputProcessor(inputs);
		
	}

	@Override
	public void dispose() 
	{
		batch.dispose();
	}

	@Override
	public void render() 
	{		
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gui.act();
	    
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		fractal.draw(batch);
		batch.end();
		
		gui.draw();
	}

	@Override
	public void resize(int width, int height) 
	{
		camera = new OrthographicCamera(width, height);
		fractal.resize(width, height);
		gui.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public Fractal getFractal()
	{
		return fractal;
	}
	
	public Gui getGui()
	{
		return gui;
	}
}
