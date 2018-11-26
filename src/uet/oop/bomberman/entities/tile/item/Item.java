package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Tile {
	protected int _duration = -1; // vòng đời
	protected boolean _active = false;
	protected int _level;

	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public abstract void setValues();

	public void removeLive() {
		if(_duration > 0)
			_duration--;

		if(_duration == 0)
			_active = false;
	}

	public int getDuration() {
		return _duration;
	}

	public int getLevel() {
		return _level;
	}

	public void setDuration(int duration) {
		this._duration = duration;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		this._active = active;
	}
}
