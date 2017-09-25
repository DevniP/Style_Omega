package com.example.dev.styleomega;

import android.os.Bundle;
import android.widget.ViewFlipper;

public class MainActivity extends Navigation  {
    ViewFlipper viewFlipper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreation();

        viewFlipper = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
    }
}
