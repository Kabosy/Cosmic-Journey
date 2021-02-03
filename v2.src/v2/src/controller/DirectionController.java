package v2.src.controller;

import v2.src.modele.Engine;

public class DirectionController {
	Engine model;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	public DirectionController(Engine engine) {
		model = engine;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}
}
