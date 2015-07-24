package com.hostabee.nanodegree.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hostabee.nanodegree.R;
import com.hostabee.nanodegree.fragment.TopTenTracksFragment;
import com.hostabee.nanodegree.fragment.TrackPlayerFragment;


@SuppressWarnings({"ALL", "unused"})
public class TopTenTracksActivity extends AppCompatActivity implements TopTenTracksFragment.Callback, TrackPlayerFragment.Callback {

    private static final String TRACKS_LIST_KEY = "tracksListJson";
    private static final String ARTIST_ID = "artistId";
    private static final String ARTIST_PICTURE = "artistPicture";
    private static final String ARTIST_NAME = "artistName";
    private static final String ROW_SELECTED_POSITION = "position";


    private static final String TRACK_PLAYER_TAG ="trackPlayerTag";
    private static final String TOP_TEN_TRACKS_FRAGMENT = "TOP_TEN_TRACKS_FRAGMENT";

    private String mArtistPicture;
    private String mArtistName;
    private String mArtistId;
    private TrackPlayerFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_tracks_one_pane);

        /*Retrieve params from Intent*/
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(ARTIST_ID)) {

            mArtistPicture = intent.getStringExtra(ARTIST_PICTURE);
            mArtistId = intent.getStringExtra(ARTIST_ID);
            mArtistName = intent.getStringExtra(ARTIST_NAME);

            TopTenTracksFragment fragment = TopTenTracksFragment.newInstance(mArtistId, mArtistName, mArtistPicture);

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, fragment,TOP_TEN_TRACKS_FRAGMENT)
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_ten_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrackClicked(int position, String tracksJson, String artistName) {
        Intent intent = new Intent(this, TrackPlayerActivity.class);
        intent.putExtra(ROW_SELECTED_POSITION, position);
        intent.putExtra(TRACKS_LIST_KEY, tracksJson);
        intent.putExtra(ARTIST_NAME, artistName);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        fragment = TrackPlayerFragment.newInstance(position, tracksJson, artistName);
        fragment.show(getSupportFragmentManager(), TRACK_PLAYER_TAG);
    }

    @Override
    public void onTrackChange(int positon) {
        TopTenTracksFragment fragment = (TopTenTracksFragment) getSupportFragmentManager().findFragmentByTag(TOP_TEN_TRACKS_FRAGMENT);
        Log.v("POSITION","Position = " + positon);
        fragment.updateRow(positon);
    }
}
