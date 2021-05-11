package android.bignerdranch.androidsudoku;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/*Instrumentation tests are for the parts of your code that are dependent on the Android framework
but that do not require the UI. These need an emulator or physical device to run because of this dependency.
You are using the architecture component ViewModel, which requires mocking the MainLooper to test, so you will use
an instrumentation test for this. These tests go in a app/src/androidTest/ directory with the same package structure
as your project.
 */


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("android.bignerdranch.androidsudoku", appContext.getPackageName());
    }
}