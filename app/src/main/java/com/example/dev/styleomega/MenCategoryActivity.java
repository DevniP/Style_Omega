package com.example.dev.styleomega;

import android.os.Bundle;
import android.widget.ViewFlipper;

public class MenCategoryActivity extends Navigation {

    ViewFlipper viewFlipper;
    ViewFlipper viewFlipper1;
    ViewFlipper viewFlipper2;
    ViewFlipper viewFlipper3;
    ViewFlipper viewFlipper4;
    ViewFlipper viewFlipper5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_category);
        onCreation();

        viewFlipper = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper);
        viewFlipper1 = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper1);
        viewFlipper2 = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper2);
        viewFlipper3 = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper3);
        viewFlipper4 = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper4);
        viewFlipper5 = (ViewFlipper) this.findViewById(R.id.backgroundViewFlipper5);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.startFlipping();

        viewFlipper1.setAutoStart(true);
        viewFlipper1.setFlipInterval(4000);
        viewFlipper1.startFlipping();

        viewFlipper2.setAutoStart(true);
        viewFlipper2.setFlipInterval(4000);
        viewFlipper2.startFlipping();

        viewFlipper3.setAutoStart(true);
        viewFlipper3.setFlipInterval(4000);
        viewFlipper3.startFlipping();

        viewFlipper4.setAutoStart(true);
        viewFlipper4.setFlipInterval(4000);
        viewFlipper4.startFlipping();

        viewFlipper5.setAutoStart(true);
        viewFlipper5.setFlipInterval(4000);
        viewFlipper5.startFlipping();
    }
}
