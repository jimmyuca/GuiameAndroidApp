package edu.dami.guiameapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import edu.dami.guiameapp.data.IPointsSource;
import edu.dami.guiameapp.data.PointsAssetSource;
import edu.dami.guiameapp.data.UserConfig;
import edu.dami.guiameapp.models.PointModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserConfigTest {

    @Test
    public void dado_esPrimeraVez_sinValor_obtenerFalso() {
        UserConfig userCfg = new UserConfig(getAppContext());
        boolean isFirstTime = userCfg.isFirstTime();
        assertFalse(isFirstTime);
    }

    @Test
    public void dado_esPrimeraVez_conValorTrue_guardarOk() {
        UserConfig userCfg = new UserConfig(getAppContext());
        boolean firstTimeResponse = userCfg.setIsFirstTime(true);
        assertTrue(firstTimeResponse);
        boolean isFirstTime = userCfg.isFirstTime();
        assertTrue(isFirstTime);
    }

    @Test
    public void dado_esPrimeraVez_conValorFalse_guardarOk() {
        UserConfig userCfg = new UserConfig(getAppContext());
        boolean firstTimeResponse = userCfg.setIsFirstTime(false);
        assertTrue(firstTimeResponse);
        boolean isFirstTime = userCfg.isFirstTime();
        assertFalse(isFirstTime);
    }

    //TODO: podria usarse Parameters para reemplazar 2 tests anteriores pero demasiado complicado para algo basico
    //ref: https://github.com/mobiniustechnologies/android-testing/blob/master/runner/AndroidJunitRunnerSample/app/src/androidTest/java/com/example/android/testing/androidjunitrunnersample/CalculatorAddParameterizedTest.java

    private Context getAppContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
