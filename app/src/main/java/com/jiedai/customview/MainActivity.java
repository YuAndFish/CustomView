package com.jiedai.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jiedai.customview.activity.CircleViewActivity;
import com.jiedai.customview.activity.CustomImageViewActivity;
import com.jiedai.customview.activity.CustomTextViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button circle_view = null;
    private Button custom_text_view = null;
    private Button custom_image_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle_view = findViewById(R.id.circle_view);
        custom_text_view = findViewById(R.id.custom_text_view);
        custom_image_view = findViewById(R.id.custom_image_view);
        initListener();
    }

    private void initListener() {
        circle_view.setOnClickListener(this);
        custom_text_view.setOnClickListener(this);
        custom_image_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_view:
                startActivity(new Intent(this, CircleViewActivity.class));
                break;
            case R.id.custom_text_view:
                startActivity(new Intent(this, CustomTextViewActivity.class));
                break;
            case R.id.custom_image_view:
                startActivity(new Intent(this, CustomImageViewActivity.class));
                break;
        }
    }

}
