package com.rebelkeithy.fractals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rebelkeithy.fractals.sets.FractalSet;

public class Fractal extends Actor
{
	private FractalSet set;
	
    private double screenW = 800;
    private double screenH = 800;
    private double screenScale = 3;
    private double centerX = 0;
    private double centerY = 0;
    private double size = 2;
    private int maxIters;
    private int originalMaxIters;
	private boolean supersampling = false;

	private Pixmap pixmap;
	private Texture texture;
	
	private static final int numColors = 4;
	private Color[] pallet;

    
    public Fractal(FractalSet set, int maxIters)
    {
    	this.set = set;
    	
        this.maxIters = maxIters;
        this.originalMaxIters = maxIters;
		/*
        pallet = new Color[numColors];
        int r = 0, g = 0, b = 0;
        for(int i = 0; i < numColors; i++)
        {
        	System.out.println(r + " " + b);
        	if(r == 0)
        	{
        		b += 1;
        		if(b > 10)
        		{
        			b = 10;
        			r += 1;
        			g += 1;
        		}
        	}
        	else if(b == 10 && r < 10)
        	{
        		r += 1;
    			g += 1;
        	}
        	else if(b > 0)
        	{
        		b -= 1;
        	}
        	else
        	{
        		r -= 1;
    			g -= 1;
        	}
        	
        	pallet[i] = new Color(r/10f, g/10f, b/10f, 1);
        }*/
        
        pallet = new Color[numColors];
        pallet[0] = new Color(0, 0, 1, 1);
        pallet[1] = new Color(1, 1, 1, 1);
        pallet[2] = new Color(1, 1, 0, 1);
        pallet[3] = new Color(0.2f, 0, 0.3f, 1);
        
		pixmap = new Pixmap((int)screenW, (int)screenH, Format.RGBA8888);

		redraw();
    }
    
    public void redraw()
    {
    	long start = System.currentTimeMillis();
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, (int)screenW, (int)screenH);
        
        double sizeH = size * screenH/screenW;
		for(int a = 0; a < screenW; a++)
		{
			for(int b = 0; b < screenH; b++)
			{
				//int color = set.contains(centerX + (a-screenW/2f)/(screenW/(2*size)), centerY + (b-screenH/2f)/(screenH/(2*size)), maxIters);
				float i = set.contains(centerX + size*(2*a/screenW - 1), centerY + sizeH*(2*b/screenH - 1), maxIters);
				if(supersampling)
				{
					float i2 = set.contains(centerX + size*(2*(a+0.25f)/screenW - 1), centerY + sizeH*(2*(b+0.25f)/screenH - 1), maxIters);
					float i3 = set.contains(centerX + size*(2*(a+0.25f)/screenW - 1), centerY + sizeH*(2*(b-0.25f)/screenH - 1), maxIters);
					float i4 = set.contains(centerX + size*(2*(a-0.25f)/screenW - 1), centerY + sizeH*(2*(b+0.25f)/screenH - 1), maxIters);
					float i5 = set.contains(centerX + size*(2*(a-0.25f)/screenW - 1), centerY + sizeH*(2*(b-0.25f)/screenH - 1), maxIters);
					
					i = (i+i2+i3+i4+i5)/5;
				}
				
				

				if(i > 0)
				{
					Color color = new Color(0, 0, 0, 1);
					if(i < maxIters)
					{
						i /= 10.0;
						//i = i*1000;
						
						int i_whole = (int) i;
						float i_frac = i - i_whole;
						
						Color color2 = pallet[i_whole%numColors];
						Color color1 = pallet[(i_whole+1)%numColors];
						
						color = new Color(color1.r * i_frac + color2.r * (1-i_frac), color1.g * i_frac + color2.g * (1-i_frac), color1.b * i_frac + color2.b * (1-i_frac), 1);
						
						//color = new Color(0*i * 1/(float)maxIters, 0.2f*i * 1/(float)maxIters, i * 1/(float)maxIters, 1);
						//color = pallet[i%numColors];
					}
					
					//if(i < maxIters)
					//	color = new Color(1 * i/maxIters, 1 * i/maxIters, 1 * i/maxIters, 1);
					//	color = new Color((10 - i)/10, (10- i)/10, (10 - i)/10, 1);

					//color = new Color(1 * ((int)i)/(float)maxIters, 1 * ((int)i)/(float)maxIters, 1 * ((int)i)/(float)maxIters, 1);
					
					pixmap.setColor(color);
					pixmap.drawPixel(a, b);
				}				
			}
		}
		
        texture = new Texture(pixmap, Format.RGB888, false);
        
        System.out.println("Render Time: " + (System.currentTimeMillis() - start) + "ms");
    }
    
    public void changeResolution(boolean increase)
    {
    	if(increase)
    	{
    		maxIters++;
    	}
    	else
    	{
    		maxIters--;
    	}
    	
    	redraw();
    }
    
    public void zoom(boolean in)
    {
    	System.out.println("zoom");
    	if(in)
    	{
    		size *= 0.85f;
    		if(size < 1)
    			maxIters+=1;
    	}
    	else
    	{
    		size *= 1.15f;
    		if(maxIters > 10)
    			maxIters-=1;
    	}
    	
    	
    	
    	redraw();
    }

	public void move(Direction dir)
	{
		if(dir == Direction.UP)
		{
			this.centerY -= 0.1*size;
		}
		else if(dir == Direction.DOWN)
		{
			centerY += 0.1*size;
		}
		else if(dir == Direction.LEFT)
		{
			centerX -= 0.1*size;
		}
		else if(dir == Direction.RIGHT)
		{
			centerX += 0.1*size;
		}
		
		redraw();
	}
    
    public void draw(SpriteBatch batch)
    {
		//batch.draw(texture, -Gdx.graphics.getWidth()/2f, -Gdx.graphics.getHeight()/2f);
    	batch.draw(texture, 0 - (float)(screenScale*screenW)/2f, 0 - (float)(screenScale*screenH)/2f, (float)(screenScale*screenW), (float)(screenScale*screenH));
    	
    	//batch.draw(texture, 0 - (float)(screenW)/2f, 0 - (float)(screenH)/2f);
        
    }

	public void resize(int width, int height)
	{
		//height -= FractalGame.GuiWidth;
	    screenW = width/screenScale;
	    screenH = height/screenScale;
		pixmap = new Pixmap((int)(screenW), (int)(screenH), Format.RGBA8888);
		
		/*
	    screenW = width;
	    screenH = height;
		pixmap = new Pixmap((int)(screenW), (int)(screenH), Format.RGBA8888);
		*/
	    redraw();
	}

	public void changeQuality(boolean down)
	{
		if(screenScale == 3)
		{
			if(down)
				screenScale = 1/3.0;
			else
				screenScale = 1;
		}
		else if(screenScale == 1)
		{
			if(down)
				screenScale = 3;
			else
				screenScale = 1/3.0;
		}
		else 
		{
			if(down)
				screenScale = 1/3.0;
			else
				screenScale = 3;
		}
		
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void toggleMultisampling()
	{
		supersampling = !supersampling;
		
		redraw();
	}

	public void setQuality(double quality) 
	{
		screenScale = quality;

		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public double getQuality() 
	{
		return screenScale;
	}

	public void resetView() 
	{
		centerX = 0;
		centerY = 0;
		size = 2;
		maxIters = originalMaxIters;
		
		redraw();
	}

	public void setFractal(FractalSet f) 
	{
		System.out.println("changing fractal to " + f);
		this.set = f;
		this.maxIters = f.defaultMaxIters();
		this.originalMaxIters = f.defaultMaxIters();
		resetView();
	}

	public int getMaxIters() 
	{
		return maxIters;
	}

	public void click(int screenX, int screenY) 
	{
		double x = screenX/(double)Gdx.graphics.getWidth();
		double y = screenY/(double)Gdx.graphics.getHeight();
		
		x = x * size * 2;
		y = y * size * 2;
		
		x = x - size;
		y = y - size;
		
		centerX += x;
		centerY += y;
		
		redraw();
	}
}
