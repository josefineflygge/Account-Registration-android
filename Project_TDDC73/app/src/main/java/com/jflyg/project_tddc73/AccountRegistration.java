package com.jflyg.project_tddc73;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Observer;

/**
 *  Account Registration Component.
 *  <br> The UI represents a user Registration form, including input for first name, last name, username, e-mail and password.
 *  The account creation is possible when the mandatory fields (*) has been filled in.
 *  The component also contains written and graphical feedback related to the requirements of the input fields.
 *
 */

public class AccountRegistration extends LinearLayout {

    Context c;
    protected LinearLayout linLay;
    protected ScrollView scrollView;

    protected TextView infoText;
    protected TextView password;
    protected  TextView confirmPass;
    protected PasswordStrengthMeter PasswordField;
    protected EditText confirmPassField;
    protected TextView passWarning;

    protected Button showPassword;
    protected Button registerButton;

    public AccountRegistration(Context context) {
        super(context);
        this.c=context;
        init();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {

        //set layout parameters
        linLay = new LinearLayout(c);
        linLay.setOrientation(LinearLayout.VERTICAL);

        scrollView = new ScrollView(c);
        scrollView.setBackgroundColor(android.R.color.transparent);
        scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 40);
        linLay.setLayoutParams(lp);

        scrollView.addView(linLay);
        addView(scrollView);

    }

    /**
     * Checks if the form is complete: are all the required fields filled in?
     * @param list a list of booleans, which are true if the form at position i is filled in correctly
     */
    // Function used for checking if the form is complete
    public boolean checkIfReady(boolean[] list){
        for(int i = 0; i < list.length; i++){
            if(list[i] == false){
                return false;
            }
        }
        return true;
    }

    //Function used for adding a generic InputField
    public void addField(String title, String type, boolean mandatory){
        InputFieldComponent newField = new InputFieldComponent(c, title, type, mandatory);
        linLay.addView(newField);
    }

    //Overloaded function used for adding a generic InputField with or without emailcheck
    public void addField(String title, String type, boolean mandatory, boolean checkemail){
        InputFieldComponent newField = new InputFieldComponent(c, title, type, mandatory, checkemail);
        linLay.addView(newField);
    }

    //Function used for adding a password field
    public void addPasswordField(){
        PasswordStrengthMeter PSM = new PasswordStrengthMeter(c);
        linLay.addView(PSM);
    }

    //Function used for adding a register button
    public void addRegisterButton(){

        //Register Account Button
        registerButton = new Button(c);
        registerButton.setText("Register Account");
        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean allReady = true;
                int count = linLay.getChildCount();
                for (int i = 0; i < count; i++) {
                    View v = linLay.getChildAt(i);
                    if (v instanceof InputFieldComponent) {
                        InputFieldComponent temp = (InputFieldComponent)v;
                        if(!temp.isComplete())
                            allReady = false;
                    }
                    else if(v instanceof PasswordStrengthMeter){
                        PasswordStrengthMeter temp = (PasswordStrengthMeter)v;
                        if(!temp.isComplete())
                            allReady = false;
                    }
                }

                if(allReady){
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);

                    builder.setCancelable(true);
                    builder.setTitle("Done");
                    builder.setMessage("Welcome, you successfully registered an account!");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);

                    builder.setCancelable(true);
                    builder.setTitle("Failure");
                    builder.setMessage("Please fill in all required fields correctly!");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }

            }
        });

        // InfoText
        infoText = new TextView(c);
        infoText.setText("All required fields are marked with *");
        infoText.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        //Add views to layout
        linLay.addView(registerButton);
        linLay.addView(infoText);
    }

}