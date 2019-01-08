package com.jflyg.project_tddc73;

import android.content.Context;
import android.graphics.Color;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.renderscript.ScriptGroup;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *  PassWord Strenth Meter Component.
 *  <br> The UI includes a password input field that takes a password String and gives visual feedback
 *  of the strength of the password, as well as toggleable hints on how to strengthen it.
 */
public class PasswordStrengthMeter extends LinearLayout {

    Context c;
    protected EditText passwordField;
    protected String defaultStrengthText;
    protected TextView hintToggle;
    protected TextView strengthString;
    protected TextView strengthHint;
    protected PasswordStrength theStrength;

    public PasswordStrengthMeter(Context context) {
        super(context);
        this.c=context;
        init();

    }

    private void init() {

        //Set layout parameters
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 40, 0, 40);
        setLayoutParams(lp);


        //initialize text and input field
        passwordField = new EditText(c);
        passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //initialize strength and hints components
        strengthString = new TextView(c);
        strengthHint = new TextView(c);
        hintToggle = new TextView(c);
        hintToggle.setText("Hints (Click to expand)");
        hintToggle.setVisibility(View.GONE);
        hintToggle.setTextColor(Color.BLUE);

        defaultStrengthText = "Please enter a password.";
        strengthString.setText(defaultStrengthText);
        strengthString.setTextColor(Color.GRAY);
        strengthHint.setTextColor(Color.GRAY);
        strengthHint.setVisibility(View.GONE);

        //Add a textchangelistener to check if the input has changed
        passwordField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                String currPass = s.toString();
                theStrength = new PasswordStrength(currPass);
                theStrength.generateStrength();

                //if there are hints to show, toggle hints visible
                if(!(theStrength.getHints()).equals("")){
                    hintToggle.setVisibility(View.VISIBLE);
                }

                //Set the hints and strength string (and color) according to the relevant strength requirements
                strengthHint.setText(theStrength.getHints() +"\n");
                strengthString.setText(theStrength.getStrengthString());
                strengthString.setTextColor(theStrength.getColor());
                passwordField.setTextColor(theStrength.getColor());

                if (theStrength.getStrengh() == theStrength.getMaxStrength()){
                    hintToggle.setVisibility(View.GONE);
                }

                //Hide or show password strengthening hints onClick
                hintToggle.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(strengthHint.getVisibility() == View.GONE){
                            strengthHint.setVisibility(View.VISIBLE);
                            hintToggle.setText("Hide hints");
                        }
                        else{
                            strengthHint.setVisibility(View.GONE);
                            hintToggle.setText("Hints (Click to expand)");
                        }

                    }
                });
            }
        });

        //Add views to layout
        addView(passwordField);
        addView(strengthString);
        addView(hintToggle);
        addView(strengthHint);


    }


    /**
     * Changes the visibility of the characters in the password input field on click.
     * <br> Alternate between standard characters or bullets.
     */
    public void changeVisibility(){
        if(InputType.TYPE_CLASS_TEXT == passwordField.getInputType()){
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        else{
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        System.out.println(passwordField.getInputType());
    }


    /**
     * Get the String value of the current password text
     */
    public String getText(){
        return passwordField.getText().toString();
    }

    /**
     * Get the int value of the current password Strength
     */
    public int getStrength(){
        return theStrength.getStrengh();
    }

    /**
     * Get the passwordField View
     */
    public EditText getPasswordField() {
        return passwordField;
    }
}