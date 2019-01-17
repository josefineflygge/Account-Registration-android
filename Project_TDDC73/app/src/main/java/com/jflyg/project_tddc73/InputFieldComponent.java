package com.jflyg.project_tddc73;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static android.text.InputType.TYPE_TEXT_VARIATION_NORMAL;

/**
 *  Input Field Component.
 *  <br> A class for an InputField which consists of a Title Field and an Input Field.
 *  When instantiating an InputFieldComponent the user can customize the field by choosing a title,
 *  inputType and if it's a mandatory field for registration.
 *
 */

public class InputFieldComponent extends LinearLayout {

    Context c;
    protected LinearLayout linearLayout;
    private String title;
    private String inputType;
    private String placeholder;
    private boolean mandatory;
    private EditText inputField;
    private TextView titleField;
    private boolean confirmEmail;
    private boolean isComplete;
    private TextView warningField;

    // Auxilery
    private TextView confirmText;
    private EditText confirmField;


    private String[] listOfRegex ={
            "[a-zA-Z_0-9 ]+",               // [0] Username;
            ".+[@].*[.].+",                 // [1] Email
            "[a-zA-Zåäöü ]+"                // [2] Name, including some unusual characters
    };

    private String[] listOfWarnings = {
            "Invalid email address",                                    // [0] Email
            "Invalid username, valid characters: a-Z, _ , 0-9",         // [1] Username
            "Invalid name, must only include alphabetic-characters",    // [2] Name
            "Invalid input",                                             // [3] default
            "Emails doesn't match!"                                     //[4] email match
    };


    public InputFieldComponent(Context context, String title, String inputType, boolean mandatory){
        super(context);
        this.c = context;
        this.title = title;
        this.inputType = inputType;
        this.mandatory = mandatory;
        this.confirmEmail = true;
        init();
    }

    public InputFieldComponent(Context context, String title, String inputType, boolean mandatory, boolean confirmEmail){
        super(context);
        this.c = context;
        this.title = title;
        this.inputType = inputType;
        this.mandatory = mandatory;
        this.confirmEmail = confirmEmail;
        init();
    }

    private void init(){

        // Setting a linear layout for this component
        setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 40);
        setLayoutParams(lp);

        //not right input
        if(mandatory)
            isComplete = false;
        else
            isComplete = true;

        // Setting the title for the input field;
        titleField = new TextView(c);
        if(mandatory)
            titleField.setText(title + "*");
        else
            titleField.setText(title);

        // Setting the input type of the editText;
        inputField = new EditText(c);
        inputField.setInputType(getInputType());

        //
        warningField = new TextView(c);
        warningField.setTextColor(Color.RED);
        warningField.setVisibility(View.GONE);
        warningField.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        if(inputType.toLowerCase().equals("email") && confirmEmail == true){

            confirmText = new TextView(c);

            if(mandatory)
            confirmText.setText("Confirm email *");
            else
            confirmText.setText("Confirm email");

            // Setting the input type of the editText;
            confirmField = new EditText(c);
            confirmField.setInputType(getInputType());

            confirmField.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {

                    if(confirmField.getText().toString().matches(getRegex())){
                        if(confirmField.getText().toString().length() > 0 && confirmField.getText().toString().equals(inputField.getText().toString())){
                            confirmField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                            isComplete = true;
                            warningField.setVisibility(View.GONE);
                        }
                        else{
                            confirmField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            warningField.setText("The email addresses don't match!");
                            warningField.setVisibility(View.VISIBLE);
                            isComplete = false;
                        }
                    }
                    else{
                        warningField.setText(getWarning());
                        warningField.setVisibility(View.VISIBLE);
                        isComplete = false;
                    }

            }});

        }


        inputField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                if(mandatory){
                    if(inputField.getText().toString().matches(getRegex()) && inputField.getText().toString().length() > 0 ){
                        inputField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                        isComplete = true;
                        warningField.setVisibility(View.GONE);
                    }
                    else{
                        inputField.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        isComplete = false;
                        warningField.setText(getWarning());
                        warningField.setVisibility(View.VISIBLE);
                    }
                }
                else
                    isComplete = true;
            }
        });


        addView(titleField);
        addView(inputField);
        if(inputType.toLowerCase().equals("email") && confirmEmail == true){
            addView(confirmText);
            addView(confirmField);
        }
        addView(warningField);

    }

    // Returns keyboard layout for email-address or normal
    private int getInputType(){

        if(inputType.toLowerCase().equals("email") )
            return TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        else
            return TYPE_TEXT_VARIATION_NORMAL;
    }

    // Returns type of regular expression
    private String getRegex(){
        if(inputType.toLowerCase().equals("email"))
            return listOfRegex[1];
        else if(inputType.toLowerCase().equals("username"))
            return listOfRegex[0];
        else if(inputType.toLowerCase().equals("name"))
            return listOfRegex[2];
        else
            return "(.*?)";
    }

    //Get the warning text if wrong input
    private String getWarning(){
        if(inputType.toLowerCase().equals("email"))
            return listOfWarnings[0];
        else if(inputType.toLowerCase().equals("username")){
            return listOfWarnings[1];
        }
        else if(inputType.toLowerCase().equals("name")){
            return listOfWarnings[2];
        }

        else
            return listOfWarnings[3];
    }

    public boolean isComplete(){
        return isComplete;
    }

}
