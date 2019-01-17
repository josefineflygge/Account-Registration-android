package com.jflyg.project_tddc73;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.renderscript.ScriptGroup;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    protected TextView passwordText;
    protected TextView strengthString;
    protected TextView strengthHint;
    protected TextView confirmText;
    protected EditText confirmField;
    protected PasswordStrength theStrength;
    protected TextView passWarning;
    protected boolean isComplete = false;
    protected Button showPassword;

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
        passwordText = new TextView(c);
        passwordText.setText("Password *");
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

        // Confirm password
        confirmText = new TextView(c);
        confirmText.setText("Repeat password");

        confirmField= new EditText(c);
        confirmField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        confirmField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(passwordField.getText().toString().equals(confirmField.getText().toString())){
                    if(theStrength.getStrengh() > 2){
                        isComplete = true;
                        passWarning.setVisibility(View.GONE);
                        confirmField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                        passwordField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                    }
                    else{
                        isComplete = false;
                        passWarning.setText("Your password is too weak!");
                        passWarning.setVisibility(View.VISIBLE);
                        confirmField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        passwordField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    }
                }
                else{
                    isComplete = false;
                    passWarning.setText("Passwords does not match!");
                    passWarning.setVisibility(View.VISIBLE);
                    confirmField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    passwordField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                }

            }
        });


        passWarning = new TextView(c);
        passWarning.setText("");
        passWarning.setTextColor(Color.RED);
        passWarning.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        passWarning.setVisibility(View.GONE);

        // Show/Hide password button
        showPassword = new Button(c);
        showPassword.setText("Show Password"); //setInputType
        showPassword.setOnClickListener(new View.OnClickListener() {


            //  show/hide plaintext
            public void onClick(View v){

                changeVisibility();
                if(showPassword.getText() == "Show Password"){
                    showPassword.setText("Hide Password");
                    confirmField.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else{
                    showPassword.setText("Show Password");
                    confirmField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });

        //Add views to layout
        addView(passwordText);
        addView(passwordField);
        addView(strengthString);
        addView(hintToggle);
        addView(strengthHint);
        addView(confirmText);
        addView(confirmField);
        addView(passWarning);
        addView(showPassword);



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
     * Get the passwordField View
     */
    public EditText getPasswordField() {
        return passwordField;
    }

    public boolean isComplete(){
        return isComplete;
    }
}