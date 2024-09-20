package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private Button LogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        LogInButton = findViewById(R.id.LogInBTN);

        LogInButton.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, startup_page.class);
            startActivity(intent);
        });
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        // Set up the "Don't have an account? Sign Up" clickable text
        TextView textView = findViewById(R.id.tvSignUp);
        String text = "Don't have an account? Sign Up";

        SpannableString spannableString = new SpannableString(text);

        // Make "Sign Up" clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to SignUpActivity
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true); // Add underline to the "Sign Up"
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color of "Sign Up"
            }
        };

        // Adjust the start and end indices to match "Sign Up"
        spannableString.setSpan(clickableSpan, 23, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        // Set up the "Forgot Password?" clickable text
        TextView forgotPasswordTextView = findViewById(R.id.tvForgotPassword);
        String forgotPasswordText = "Forgot Password?";

        SpannableString spannableForgotPasswordString = new SpannableString(forgotPasswordText);

        // Make "Forgot Password?" clickable
        ClickableSpan forgotPasswordClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to forgot_password class
                Intent intent = new Intent(LogInActivity.this, forgot_password.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true); // add underline
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color
            }
        };

        // Set the clickable span for the whole text
        spannableForgotPasswordString.setSpan(forgotPasswordClickableSpan, 0, forgotPasswordText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forgotPasswordTextView.setText(spannableForgotPasswordString);
        forgotPasswordTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
