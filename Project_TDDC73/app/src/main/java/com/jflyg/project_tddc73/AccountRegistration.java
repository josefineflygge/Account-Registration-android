package com.jflyg.project_tddc73;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
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

    protected EditText firstNameField;
    protected EditText lastNameField;
    protected EditText userNameField;
    protected EditText emailField;
    protected EditText confirmemailField;
    protected EditText confirmPassField;

    protected PasswordStrengthMeter PasswordField;

    protected TextView firstName;
    protected TextView lastName;
    protected TextView userName;
    protected TextView email;
    protected TextView confirmEmail;
    protected TextView password;
    protected TextView confirmPass;
    protected TextView infoText;
    protected TextView emailMatch;
    protected TextView typeEmail;
    protected TextView userNameValid;
    protected TextView passWarning;

    protected Button showPassword;
    protected Button registerButton;
    protected boolean[] completeness;


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

        //Initialization
        //Completeness: keep track of the completed inputs
        completeness = new boolean[4];
        Arrays.fill(completeness, false);

        //First Name
        firstName = new TextView(c);
        firstName.setText("First Name*");
        firstNameField = new EditText(c);
        firstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(firstNameField.getText().toString().matches("[a-zA-Zåäöü ]+")){
                    completeness[0] = true;
                    firstNameField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                }
                else{
                    completeness[0] = false;
                    firstNameField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                }
                // Check if form is completely filled
                if(checkIfReady(completeness)){
                    registerButton.setTextColor(Color.BLACK);
                    registerButton.setClickable(true);
                }
                else{
                    registerButton.setTextColor(Color.LTGRAY);
                    registerButton.setClickable(false);
                }
            }
        });

        //Last Name
        lastName = new TextView(c);
        lastName.setText("Last Name");
        lastNameField = new EditText(c);

        // UserName
        userName = new TextView(c);
        userName.setText("Username * ");
        userNameField = new EditText(c);
        userNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(userNameField.getText().toString().matches("[a-zA-Z_0-9 ]+")){
                    completeness[1] = true;
                    userNameField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                    userNameValid.setVisibility(View.GONE);
                }
                else{
                    completeness[1] = false;
                    userNameField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    userNameValid.setVisibility(View.VISIBLE);
                }

                // Check if form is completely filled
                if(checkIfReady(completeness)){
                    registerButton.setTextColor(Color.BLACK);
                    registerButton.setClickable(true);
                }
                else{
                    registerButton.setTextColor(Color.LTGRAY);
                    registerButton.setClickable(false);
                }
            }
        });

        // Infotext if username is (in)valid
        userNameValid = new TextView(c);
        userNameValid.setText("Invalid username, valid characters: a-Z, _ , 0-9");
        userNameValid.setTextColor(Color.RED);
        userNameValid.setVisibility(View.GONE);
        userNameValid.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        //Email
        email = new TextView(c);
        email.setText("E-mail *");
        emailField = new EditText(c);
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emailField.getText().toString().matches(".+[@].*[.].+")){
                    typeEmail.setVisibility(View.GONE);
                    emailField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                }
                else{
                    typeEmail.setVisibility(View.VISIBLE);
                    emailField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                }

                if(confirmemailField.getText().toString().length() > 0 && !emailField.getText().toString().equals(confirmemailField.getText().toString())){
                    completeness[2] = false;
                    confirmemailField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    emailMatch.setVisibility(View.VISIBLE);
                }

                if(confirmemailField.getText().toString().length() > 0 && emailField.getText().toString().equals(confirmemailField.getText().toString())){
                    completeness[2] = true;
                    confirmemailField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                    emailMatch.setVisibility(View.VISIBLE);
                }


                // Check if form is completely filled
                if(checkIfReady(completeness)){
                    registerButton.setTextColor(Color.BLACK);
                    registerButton.setClickable(true);
                }
                else{
                    registerButton.setTextColor(Color.LTGRAY);
                    registerButton.setClickable(false);
                }
            }
        });

        //Validate email, is the text actually an email?
        typeEmail = new TextView(c);
        typeEmail.setText("Invalid email address.");
        typeEmail.setTextColor(Color.RED);
        typeEmail.setVisibility(View.GONE);
        typeEmail.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        //Confirm email
        confirmEmail = new TextView(c);
        confirmEmail.setText("Confirm E-mail *");
        confirmemailField = new EditText(c);
        confirmemailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        confirmemailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emailField.getText().toString().length() > 2 && emailField.getText().toString().equals(confirmemailField.getText().toString())){
                    completeness[2] = true;
                    confirmemailField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                    emailMatch.setVisibility(View.GONE);
                }
                else{
                    completeness[2] = false;
                    confirmemailField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    emailMatch.setVisibility(View.VISIBLE);
                }

                // Check if form is completely filled
                if(checkIfReady(completeness)){
                    registerButton.setTextColor(Color.BLACK);
                    registerButton.setClickable(true);
                }
                else{
                    registerButton.setTextColor(Color.LTGRAY);
                    registerButton.setClickable(false);
                }

            }
        });

        // Info text if email addresses does not match
        emailMatch = new TextView(c);
        emailMatch.setText("Email addresses does not match!");
        emailMatch.setTextColor(Color.RED);
        emailMatch.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        emailMatch.setVisibility(View.GONE);


        //Password
        password = new TextView(c);
        password.setText("Password *");
        PasswordField = new PasswordStrengthMeter(c);

        // Confirm password
        confirmPass = new TextView(c);
        confirmPass.setText("Repeat password");

        confirmPassField = new EditText(c);
        confirmPassField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmPassField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(PasswordField.getText().equals(confirmPassField.getText().toString())){
                    if(PasswordField.getStrength() > 2){
                        completeness[3] = true;
                        passWarning.setVisibility(View.GONE);
                        confirmPassField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                        PasswordField.getPasswordField().getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                    }
                    else{
                        completeness[3] = false;
                        passWarning.setText("Your password is too weak,");
                        passWarning.setVisibility(View.VISIBLE);
                        confirmPassField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        PasswordField.getPasswordField().getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    }
                }
                else{
                    completeness[3]= false;
                    passWarning.setText("Passwords does not match");
                    passWarning.setVisibility(View.VISIBLE);
                    confirmPassField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    PasswordField.getPasswordField().getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                }

                // Check if form is completely filled
                if(checkIfReady(completeness)){
                    registerButton.setTextColor(Color.BLACK);
                    registerButton.setClickable(true);
                }
                else{
                    registerButton.setTextColor(Color.LTGRAY);
                    registerButton.setClickable(false);
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

                PasswordField.changeVisibility();
                if(showPassword.getText() == "Show Password"){
                    showPassword.setText("Hide Password");
                    confirmPassField.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else{
                    showPassword.setText("Show Password");
                    confirmPassField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });

        //Register Account Button
        registerButton = new Button(c);
        registerButton.setText("Register Account");
        registerButton.setTextColor(Color.LTGRAY);
        registerButton.setClickable(false   );
        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);

                builder.setCancelable(true);
                builder.setTitle("Done");
                builder.setMessage("Welcome " + userNameField.getText().toString() + ", You successfully registered an account!");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

        // InfoText
        infoText = new TextView(c);
        infoText.setText("All required fields are marked with *");
        infoText.setTextAlignment(TEXT_ALIGNMENT_CENTER);


        //Add views to layout
        linLay.addView(firstName);
        linLay.addView(firstNameField);
        linLay.addView(lastName);
        linLay.addView(lastNameField);
        linLay.addView(userName);
        linLay.addView(userNameField);
        linLay.addView(userNameValid);
        linLay.addView(email);
        linLay.addView(emailField);
        linLay.addView(typeEmail);
        linLay.addView(confirmEmail);
        linLay.addView(confirmemailField);
        linLay.addView(emailMatch);
        linLay.addView(password);
        linLay.addView(PasswordField);
        linLay.addView(confirmPass);
        linLay.addView(confirmPassField);
        linLay.addView(passWarning);
        linLay.addView(showPassword);
        linLay.addView(registerButton);
        linLay.addView(infoText);
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

}