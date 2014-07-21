package de.appplant.cordova.plugin.localnotification;

import android.test.AndroidTestCase;
import junit.framework.Assert;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class OptionsTest extends AndroidTestCase {

    public void testVibrationPatternSpecified() throws JSONException {
        Options options = parseOptions("{ \"vibrationPattern\" : [10, 50, 100] }");

        assertTrue(Arrays.equals(new long[]{ 10, 50, 100 }, options.getVibrationPattern()));
    }

    public void testVibrationPatternNotSpecified() {
        Options options = parseOptions("{ }");

        assertTrue(Arrays.equals(new long[]{0, 0}, options.getVibrationPattern()));
    }

    public void testInvalidPatternSpecified() {
        Options options = parseOptions("{ \"vibrationPattern\" : [\"a\", 50, 100] }");

        try {
            options.getVibrationPattern();
            Assert.fail("Should have thrown a parsing exception");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof JSONException);
        }
    }

    public void testNoLedSpecified() {
        Options options = parseOptions("{ }");

        assertNull(options.getColor());
    }

    public void testLedSpecified() {
        Options options = parseOptions("{ \"led\": \"A0FF05\" }");
        assertEquals(-6226171, (int)options.getColor());
    }

    public void testNoLedNull() {
        Options options = parseOptions("{ \"led\": null }");

        assertNull(options.getColor());
    }

    private Options parseOptions(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return new Options(getContext()).parse(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}