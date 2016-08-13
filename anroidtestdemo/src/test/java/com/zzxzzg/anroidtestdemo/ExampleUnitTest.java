package com.zzxzzg.anroidtestdemo;

import org.junit.Test;
import org.junit.rules.Verifier;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.model.Statement;
import org.junit.validator.ValidateWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(Parameterized.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test(expected = NullPointerException.class)
    public void testException(){
        TestUtils tester= new TestUtils();
        String expected = "Hello JUnit";
        String result = tester.printWithDanger(0);
        assertEquals(expected,result);
    }


}