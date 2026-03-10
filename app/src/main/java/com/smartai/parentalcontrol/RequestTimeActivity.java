package com.smartai.parentalcontrol;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

public class RequestTimeActivity extends AppCompatActivity {

    private TextInputEditText reasonEditText;
    private Slider minutesSlider;
    private TextView minutesTextView;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time);

        reasonEditText = findViewById(R.id.reasonEditText);
        minutesSlider = findViewById(R.id.minutesSlider);
        minutesTextView = findViewById(R.id.minutesTextView);
        submitButton = findViewById(R.id.submitButton);

        setupSlider();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });
    }

    private void setupSlider() {
        minutesSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                updateMinutesDisplay((int) value);
            }
        });
        
        updateMinutesDisplay((int) minutesSlider.getValue());
    }

    private void updateMinutesDisplay(int minutes) {
        if (minutes >= 60) {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            if (remainingMinutes == 0) {
                minutesTextView.setText(hours + " hour" + (hours > 1 ? "s" : ""));
            } else {
                minutesTextView.setText(hours + "h " + remainingMinutes + "m");
            }
        } else {
            minutesTextView.setText(minutes + " minutes");
        }
    }

    private void handleSubmit() {
        String reason = reasonEditText.getText().toString().trim();
        int extraMinutes = (int) minutesSlider.getValue();

        if (!validateInput(reason)) {
            return;
        }

        // TODO: Send request to parent via Firebase or local database
        submitRequest(reason, extraMinutes);
    }

    private boolean validateInput(String reason) {
        if (TextUtils.isEmpty(reason)) {
            reasonEditText.setError("Please provide a reason");
            reasonEditText.requestFocus();
            return false;
        }

        if (reason.length() < 10) {
            reasonEditText.setError("Please provide a more detailed reason");
            reasonEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void submitRequest(String reason, int extraMinutes) {
        // TODO: Implement actual request submission logic
        String message = String.format("Request submitted: %d minutes for: %s", 
                extraMinutes, reason);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        
        // Close activity after successful submission
        finish();
    }
}
