package com.util;

public class Constants {
	
	public static final float CAMERA_WIDTH = 480; 
	public static final float CAMERA_HEIGHT = 800; 
	public static final String FILE_SAVE_DATA = "MySavedData";
	public static final String FACEBOOK_LOGIN = "FacebookLogin"; 
	public static final int WIDTH_PICKER_CELL = 400; 
	public static final int HEIGHT_PICKER_CELL = 120; 
	public static final float CENTER_X = CAMERA_WIDTH * 0.5f; 
	public static final float CENTER_Y = CAMERA_HEIGHT * 0.5f; 
	public static String MAX_TIP = "                                                                                 " +
	"                                                                                                              " +
	"                                                                                                              "; 
	
	public static final int INITIAL_SCORE = 20000; 
	public static final int SCORE_LOSE_BY_TIP = 1500;
	
	private Constants () {
		throw new AssertionError();
	}
}
