/**
 * @author Rodrigo Duarte Louro
 * @dateJul 23, 2014
 */
package com.scenes.test;

import java.io.IOException;

import org.andengine.engine.Engine;

import android.test.ActivityInstrumentationTestCase2;

import com.activities.GameActivity;
import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.DataInMemory;

public class DataInMemoryTest extends ActivityInstrumentationTestCase2<GameActivity> {
		
	private GameActivity gA; 
	private Engine e; 
	private DataInMemory data; 
	
	public DataInMemoryTest() {
		super(GameActivity.class); 
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.gA = getActivity(); 
		this.e = this.gA.getEngine();
		data = new DataInMemory(this.gA, Constants.FILE_SAVE_DATA);
	}
	
//	public void testResourseManagerIsCreated() {
//		assertNotNull(ResourcesManager.getInstance());
//	}
//	
//	public void testResourseManagerCameraIsInit() {
//		assertNotNull(ResourcesManager.getInstance().camera);
//	}
//	
//	public void testResourseManagerEngineIsInit() {
//		assertNotNull(ResourcesManager.getInstance().engine);
//	}
	
	public void testString() {
		String key = "ChaveString"; 
		String value = "String";
		data.saveData(key, value); 
		assertEquals(value, data.readStringData(key)); 
	}
	
	public void testBoolean() { 
		String key = "ChaveBoolean"; 
		Boolean value = true;  
		data.saveData(key, value); 
		assertTrue(data.readBooleanData(key)); 
	}
	
	public void testInt() {
		String key = "ChaveInt"; 
		int value = 12;  
		data.saveData(key, value); 
		assertEquals(value, data.readIntData(key));
	}
	
	public void testLong() {
		String key = "ChaveLong"; 
		long value = 3212132;  
		data.saveData(key, value); 
		assertEquals(value, data.readLongData(key));
	}
	
	public void testFloat() {
		String key = "ChaveFloat"; 
		float value = 2.0f;  
		data.saveData(key, value); 
		assertEquals(value, data.readFloatData(key));
	}
	
	@Override
	public void tearDown() throws Exception { 
		super.tearDown();
		gA = null;
		System.gc(); 
	}
	
	
}
