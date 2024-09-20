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
    private Button LogInBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        LogInBTN = findViewById(R.id.LogInBTN);

        LogInBTN.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, startup_page.class);
            startActivity(intent);
        });
        // Set up the "Already have an account? Sign In" clickable text
        TextView textView = findViewById(R.id.tvSignUp);
        String text = "Don't have an account? Sign Up";

        SpannableString spannableString = new SpannableString(text);

        // Make "Sign In" clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to SignInActivity
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true); // Add underline to the "Sign In"
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color of "Sign In"
            }
        };

        // Set the clickable span from "Sign In" position to the end
        spannableString.setSpan(clickableSpan, 23, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

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
