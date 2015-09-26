package com.github.stackapp;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivityTestUI extends ActivityInstrumentationTestCase2<MainActivity>{

	private MainActivity MainActivity;
    private TextView FirstTestText;
    private Button pushButtonTest;
    private Button popButtonTest;
    private Button clearButtonTest;
    private TextView InfoTextView;
    private Bundle bundle = new Bundle();
    private Spinner spinner;
    private Instrumentation mInstrumentation;

	
	public MainActivityTestUI() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	/*
	 * sets up the test environment
	 */
	@Before
	public void setUp() throws Exception {
        super.setUp();
        MainActivity = new MainActivity();
        MainActivity = getActivity();
        mInstrumentation = getInstrumentation();
        Thread.sleep(3000);
        FirstTestText = (TextView) MainActivity.findViewById(R.id.activity_main_stack_textview);
        setActivityInitialTouchMode(true);
        pushButtonTest = (Button) MainActivity.findViewById(R.id.activity_main_push_button);
        popButtonTest = (Button) MainActivity.findViewById(R.id.activity_main_pop_button);
        clearButtonTest = (Button) MainActivity.findViewById(R.id.activity_main_clear_button);
        InfoTextView = (TextView) MainActivity.findViewById(R.id.activity_main_stack_textview);
    }	
	/*
	 * method tears down the existing application
	 */
	@After
	public void tearDown() throws Exception {
		MainActivity = null;
		super.tearDown();
	}

	/*
	 * Verify the button layout parameters
	 */
	@Test
	public void testEPushClickButton_layout() throws InterruptedException {
		Thread.sleep(3000);
	  
	  MainActivity.runOnUiThread(new Runnable() {
		  public void run() {
			  final View decorView = MainActivity.getWindow().getDecorView();
				
			  ViewAsserts.assertOnScreen(decorView, pushButtonTest);
			
			  final ViewGroup.LayoutParams layoutParams = pushButtonTest.getLayoutParams();
			  assertNotNull(layoutParams);
			  assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
			  assertEquals(layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
		  }
	  });
	  mInstrumentation.waitForIdleSync();
	 
	}
	/*
	 * test the pop button layout
	 */
	@Test
	public void testFPopClickButton_layout() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
			   final View decorView = MainActivity.getWindow().getDecorView();
		
				    ViewAsserts.assertOnScreen(decorView, popButtonTest);
		
			  final ViewGroup.LayoutParams layoutParams = popButtonTest.getLayoutParams();
			   assertNotNull(layoutParams);
			  assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
			  assertEquals(layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
			  }
		});
		mInstrumentation.waitForIdleSync();
	}
	
	/*
	 * test the clear button layout
	 */
	@Test
	public void testGClearClickButton_layout() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
			  final View decorView = MainActivity.getWindow().getDecorView();
		
			  ViewAsserts.assertOnScreen(decorView, popButtonTest);
		
			  final ViewGroup.LayoutParams layoutParams = clearButtonTest.getLayoutParams();
			  assertNotNull(layoutParams);
			  assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
			  assertEquals(layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
			  }
		});
		mInstrumentation.waitForIdleSync();
	}
	
	/*
	 * test the textView Parameters
	 */
	@Test
	public void testHInfoTextView_layout() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				final View decorView = MainActivity.getWindow().getDecorView();
				ViewAsserts.assertOnScreen(decorView, InfoTextView);
				assertTrue(View.VISIBLE == InfoTextView.getVisibility());
			}
		});
		mInstrumentation.waitForIdleSync();
	}
	
	/*
	 * test text view for push button
	 */
	@Test
	public void testIClickPushButton_clickButtonAndExpectInfoText() throws InterruptedException {
		Thread.sleep(3000);
		
		TouchUtils.clickView(this, pushButtonTest);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				pushButtonTest.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						String expectedInfoText = MainActivity.getString(R.string.action_stack_push);
						Log.v("Push", "Push");
						assertTrue(View.VISIBLE == InfoTextView.getVisibility());
						assertEquals(expectedInfoText, InfoTextView.getText());
						
					}
				});
			}
		});
		mInstrumentation.waitForIdleSync();
	}
	
	
	/*
	 * tests text view for pop button
	 */
	@Test
	public void testJClickPopButton_clickButtonAndExpectInfoText() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				popButtonTest.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						String expectedInfoText = MainActivity.getString(R.string.action_stack_pop);
						Log.v("Pop", "Pop");
						assertTrue(View.VISIBLE == InfoTextView.getVisibility());
						assertEquals(expectedInfoText, InfoTextView.getText());
						
					}
				});
				
				
				
			}
		});
		mInstrumentation.waitForIdleSync();
	}
	/*
	 * tests text view for clear button
	 */
	@Test
	public void testKClickClearButton_clickButtonAndExpectInfoText() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				clearButtonTest.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						String expectedInfoText = MainActivity.getString(R.string.action_stack_clear);
						Log.v("Clear", "Clear");
						assertTrue(View.VISIBLE == InfoTextView.getVisibility());
						assertEquals(expectedInfoText, InfoTextView.getText());
						
					}
				});
			}
		});
	  
	  mInstrumentation.waitForIdleSync();
	}
	
	/*
	 * test the validateInput method
	 */
	@Test
	public void testAValidateInput() throws Throwable
	{
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
		
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MainActivity.testMethod();
				Assert.assertFalse(MainActivity.pushStack("-1"));
				Assert.assertFalse(MainActivity.pushStack("10")); 
				Assert.assertFalse(MainActivity.pushStack("nothing"));
				Assert.assertFalse(MainActivity.pushStack(""));
				MainActivity.endTestMethod();
			}
		});
		mInstrumentation.waitForIdleSync();

	}
	/*
	 * test the pushStack method
	 */
	@Test
	public void testBPushStack() throws Exception
	{
		
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				MainActivity.testMethod();
				MainActivity.pushStack("1");
				MainActivity.pushStack("2");
				MainActivity.pushStack("3");
				Assert.assertEquals(1, MainActivity.get(0));
				Assert.assertEquals(2, MainActivity.get(1));
				Assert.assertEquals(3, MainActivity.get(2));
			//	Assert.assertNotEquals(3, MainActivity.get(0));
			//	Assert.assertNotEquals(2, MainActivity.get(2));
				assertTrue(MainActivity.get(0) != 3);
				assertTrue(MainActivity.get(2) != 2);
				MainActivity.endTestMethod();
			}
		});
		mInstrumentation.waitForIdleSync();

		
	}
	
	/*
	 * test the popStack() methods parameters
	 */
	public void testCPopStack() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				MainActivity.testMethod();
				MainActivity.pushStack("1");
				MainActivity.pushStack("2");
				MainActivity.pushStack("3");
				Assert.assertEquals(3, MainActivity.get((MainActivity.getListSize()) - 1));
				MainActivity.popStack();
				Assert.assertEquals(2, MainActivity.get((MainActivity.getListSize()) - 1));
				MainActivity.popStack();
				Assert.assertEquals(1, MainActivity.get((MainActivity.getListSize()) - 1));
				MainActivity.popStack();
				assertTrue(MainActivity.isListEmpty());
			}
		});
		mInstrumentation.waitForIdleSync();
	}
	
	/*
	 * test the clearStack() method parameters
	 */
	public void testDClearStack() throws InterruptedException {
		Thread.sleep(3000);
		MainActivity.runOnUiThread(new Runnable() {
			public void run() {
				MainActivity.testMethod();
				MainActivity.pushStack("1");
				MainActivity.pushStack("2");
				MainActivity.pushStack("3");
				MainActivity.clearStack(false);
				assertTrue(MainActivity.isListEmpty());
			}
		});
		mInstrumentation.waitForIdleSync();
	}

}
