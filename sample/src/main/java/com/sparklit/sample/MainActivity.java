package com.sparklit.sample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.transition.TransitionManager;
import android.app.Activity;


import com.sparklit.adbutler.AdButler;
import com.sparklit.adbutler.Placement;
import com.sparklit.adbutler.PlacementRequestConfig;
import com.sparklit.adbutler.PlacementResponse;
import com.sparklit.adbutler.PlacementResponseListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //ViewGroup activity_mainLayout;

    @Overri de
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        activity_mainLayout = findViewById(R.id.activity_mainLayout);
        activity_mainLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        moveImage();
                        return true;
                    }
                }
        );
        */
        Button needHelpB = findViewById(R.id.needHelpB);
        final ImageView adButlerPicture = findViewById(R.id.adButlerPicture);

        needHelpB.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v){
                        adButlerPicture.setVisibility(View.VISIBLE);
                        moveImage();
                    }
                }
        );
    }

    public void moveImage(){
        View adButlerPicture = findViewById(R.id.adButlerPicture);

        //TransitionManager.beginDelayedTransition(activity_mainLayout);

        RelativeLayout.LayoutParams positionRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        positionRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        positionRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        adButlerPicture.setLayoutParams(positionRules);
    }

    public void requestPlacement(View view) {
        String[] keywords = {"sample2"};
        Set<String> keywordSet = new HashSet<>();
        Collections.addAll(keywordSet, keywords);
        PlacementRequestConfig config = new PlacementRequestConfig.Builder(153105, 214764, 300, 250)
                .setKeywords(keywordSet)
                .build();
        AdButler adbutler = new AdButler();
        adbutler.requestPlacement(config, new PlacementResponseListener() {
            @Override
            public void success(PlacementResponse response) {
                System.out.println(response.getStatus());
                for (Placement placement : response.getPlacements()) {
                    System.out.println(placement.getBannerId());
                }

                if (response.getPlacements().size() > 0) {
                    final Placement placement = response.getPlacements().get(0);
                    if (placement != null) {
                        ImageView imageView = (ImageView) findViewById(R.id.adButlerPicture);

                        imageView.setVisibility(View.VISIBLE);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(placement.getRedirectUrl()));
                                startActivity(intent);
                            }
                        });

                        Picasso.with(getBaseContext())
                                .load(placement.getImageUrl())
                                .resize(placement.getWidth(), placement.getHeight())
                                .into(imageView);

                        placement.recordImpression();
                    }
                }
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }

    public void requestPlacements(View view) {
        PlacementRequestConfig config1 = new PlacementRequestConfig.Builder(153105, 214764, 300, 250).build();

        String[] keywords = {"sample2"};
        Set<String> keywordSet = new HashSet<>();
        Collections.addAll(keywordSet, keywords);
        PlacementRequestConfig config2 = new PlacementRequestConfig.Builder(153105, 214764, 300, 250)
                .setKeywords(keywordSet)
                .build();

        List<PlacementRequestConfig> configs = new ArrayList<>();
        configs.add(config1);
        configs.add(config2);

        AdButler adbutler = new AdButler();
        adbutler.requestPlacements(configs, new PlacementResponseListener() {
            @Override
            public void success(PlacementResponse response) {
                System.out.println(response.getStatus());
                for (Placement placement : response.getPlacements()) {
                    System.out.println(placement.getBannerId());
                }
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }

    public void requestPixel(View view) {
        AdButler adbutler = new AdButler();
        adbutler.requestPixel("https://servedbyadbutler.com/default_banner.gif");
    }

    public void recordImpression(View view) {
        String[] keywords = {"sample2"};
        Set<String> keywordSet = new HashSet<>();
        Collections.addAll(keywordSet, keywords);
        PlacementRequestConfig config = new PlacementRequestConfig.Builder(153105, 214764, 300, 250)
                .setKeywords(keywordSet)
                .build();

        AdButler adbutler = new AdButler();
        adbutler.requestPlacement(config, new PlacementResponseListener() {
            @Override
            public void success(PlacementResponse response) {
                System.out.println(response.getStatus());
                for (Placement placement : response.getPlacements()) {
                    placement.recordImpression();
                }
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }

    public void recordClick(View view) {
        String[] keywords = {"sample2"};
        Set<String> keywordSet = new HashSet<>();
        Collections.addAll(keywordSet, keywords);
        PlacementRequestConfig config = new PlacementRequestConfig.Builder(153105, 214764, 300, 250)
                .setKeywords(keywordSet)
                .build();

        AdButler adbutler = new AdButler();
        adbutler.requestPlacement(config, new PlacementResponseListener() {
            @Override
            public void success(PlacementResponse response) {
                System.out.println(response.getStatus());
                for (Placement placement : response.getPlacements()) {
                    placement.recordClick();
                }
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }
}
