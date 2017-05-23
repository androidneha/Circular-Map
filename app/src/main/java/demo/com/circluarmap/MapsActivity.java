package demo.com.circluarmap;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Timer timer;
    private DonutProgress donutProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean a = false;
                        if (a) {
                            ObjectAnimator anim = ObjectAnimator.ofInt(donutProgress, "progress", 0, 10);
                            anim.setInterpolator(new DecelerateInterpolator());
                            anim.setDuration(1000);
                            anim.start();
                        } else {
                            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MapsActivity.this, R.animator.progress_main);
                            set.setInterpolator(new DecelerateInterpolator());
                            set.setTarget(donutProgress);
                            set.start();
                        }
                    }
                });
            }
        }, 0, 2000);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }
}
