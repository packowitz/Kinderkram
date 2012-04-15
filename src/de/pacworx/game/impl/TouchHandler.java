package de.pacworx.game.impl;

import java.util.List;

import android.view.View.OnTouchListener;
import de.pacworx.game.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    
    public int getTouchX(int pointer);
    
    public int getTouchY(int pointer);
    
    public List<TouchEvent> getTouchEvents();
}
