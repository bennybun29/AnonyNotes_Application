package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

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
                ds.setUnderlineText(false); // Add underline to the "Sign In"
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color of "Sign In"
            }
        };

        // Set the clickable span from "Sign In" position to the end
        spannableString.setSpan(clickableSpan, 25, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
