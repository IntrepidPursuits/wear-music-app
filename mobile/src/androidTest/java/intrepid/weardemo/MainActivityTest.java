package intrepid.weardemo;

import android.test.ActivityInstrumentationTestCase2;

import intrepid.weardemo.activities.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testName() throws Exception {
        Thread.sleep(3000);
    }
}
