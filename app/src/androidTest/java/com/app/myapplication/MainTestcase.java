package com.app.myapplication;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.myapplication.activities.MainActivity;
import com.app.myapplication.activities.SecondActivity;
import com.app.myapplication.activities.ThirdActivity;

import java.util.ArrayList;
import java.util.List;

public class MainTestcase extends InstrumentationTestCase
{
    private List<String> testCart = new ArrayList<>();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.out.println("setUp");
    }

    @Override
    protected void tearDown() throws Exception {
        //super.tearDown();
        System.out.println("tearDown");
    }

    @MediumTest
    public void test(){
        Instrumentation instrumentation = getInstrumentation();

        // Register we are interested in the main activity
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(MainActivity.class.getName(), null, false);

        // Start the main activity as the first activity
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), MainActivity.class.getName());
        instrumentation.startActivitySync(intent);

        // Wait for it to start...
        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);

        RecyclerView recyclerView = (RecyclerView) currentActivity.findViewById(R.id.recycler_view);
        final View item1 = recyclerView.getChildAt(0);
        final View item2 = recyclerView.getChildAt(1);
        final View item3 = recyclerView.getChildAt(2);

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {

                item1.performClick();
                delay();
                item1.performClick();
                delay();
                item2.performClick();
                delay();
                item3.performClick();
            }
        });

        instrumentation.waitForIdleSync();

        TextView text1 = (TextView) item1.findViewById(R.id.title);
        TextView text2 = (TextView) item2.findViewById(R.id.title);
        TextView text3 = (TextView) item3.findViewById(R.id.title);

        testCart.add(text1.getText().toString());
        testCart.add(text1.getText().toString());
        testCart.add(text2.getText().toString());
        testCart.add(text3.getText().toString());

        instrumentation.waitForIdleSync();

        final TextView cartCount = (TextView) currentActivity.findViewById(R.id.cart);
        assertEquals(cartCount.getText().toString(), testCart.size() + "");
        instrumentation.waitForIdleSync();

        // Register we are interested in the second activity...
        // this has to be done before we do something that will send us to that
        // activity...
        instrumentation.removeMonitor(monitor);
        monitor = instrumentation.addMonitor(SecondActivity.class.getName(), null, false);
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                cartCount.performClick();
            }
        });

        // Wait for the second activity to start...
        currentActivity = instrumentation.waitForMonitorWithTimeout(monitor, 3000);

        instrumentation.waitForIdleSync();
        final Button button = (Button) currentActivity.findViewById(R.id.button);

        // Register we are interested in the third activity...
        // this has to be done before we do something that will send us to that
        // activity...
        instrumentation.removeMonitor(monitor);
        monitor = instrumentation.addMonitor(ThirdActivity.class.getName(), null, false);

        delay();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        currentActivity = instrumentation.waitForMonitorWithTimeout(monitor, 3000);

        instrumentation.waitForIdleSync();
        RecyclerView cartItemsView = (RecyclerView) currentActivity.findViewById(R.id.cartItems);

        assertEquals(cartItemsView.getChildCount(), testCart.size());

        final View cartItem1 = cartItemsView.getChildAt(0);
        final View cartItem2 = cartItemsView.getChildAt(1);
        final View cartItem3 = cartItemsView.getChildAt(2);
        final View cartItem4 = cartItemsView.getChildAt(3);


        TextView cartItemText1 = (TextView) cartItem1.findViewById(R.id.title);
        TextView cartItemText2 = (TextView) cartItem2.findViewById(R.id.title);
        TextView cartItemText3 = (TextView) cartItem3.findViewById(R.id.title);
        TextView cartItemText4 = (TextView) cartItem4.findViewById(R.id.title);

        assertEquals(cartItemText1.getText().toString(), testCart.get(0));
        assertEquals(cartItemText2.getText().toString(), testCart.get(1));
        assertEquals(cartItemText3.getText().toString(), testCart.get(2));
        assertEquals(cartItemText4.getText().toString(), testCart.get(3));
        delay();
    }

    private void delay(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
