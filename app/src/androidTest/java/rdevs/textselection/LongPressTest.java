package rdevs.textselection;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.robotium.solo.Solo;

/**
 * Created by Kandeparker on 27-Jun-16.
 */
public class LongPressTest extends ActivityInstrumentationTestCase2<MainActivity> {


    Solo solo ;
    String test_val = "" ;


    public LongPressTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
       solo.finishOpenedActivities();
        super.tearDown();
    }

    public void test_longPressTest() throws Exception
    {
        solo.unlockScreen();
        final TextView tv = (TextView) solo.getView(R.id.tv);
        final TextView tvResult = (TextView) solo.getView(R.id.tvResult);
        String text = "This" ;
        solo.clickLongOnText(text);
        solo.clickOnMenuItem("Cut");

        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    test_val = tvResult.getText().toString();
                    Toast.makeText(getActivity(),test_val,Toast.LENGTH_LONG).show();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        assertEquals("STRING IS text",test_val);
        Log.e("<-- STRING VALUE IS -->", test_val+"");
        // creating a pull request for this change. lol
        solo.sleep(2000);

    }

}
