package com.cs542.project.gui.modifiedswingui;

import javax.swing.JButton;

public class Router extends JButton {

	protected int id;

	public Router(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
