package com.jflyg.project_tddc73;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;


/**
 * Test program for AccountRegistration.java and PasswordStrengthMeter.java
 * Project by Josefine Flügge & Axel Kollberg for course TDDC73, Linköping University.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout Layout = new LinearLayout(this);
        Layout.setOrientation(LinearLayout.VERTICAL);

        AccountRegistration SignUpComp = new AccountRegistration(this);

        Layout.addView(SignUpComp);
        setContentView(Layout);
    }
}
