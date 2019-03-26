//package com.baims.app.presentation.video;
//
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MotionEvent;
//import android.view.View;
//import android.webkit.URLUtil;
//import android.widget.MediaController;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//import com.baims.app.R;
//import com.baims.app.data.repositories.BaimsRemoteDataSource;
//import com.baims.app.data.repositories.BaimsRepository;
//import com.baims.app.data.repository.BaimsCacheDataSource;
//import com.baims.app.data.utils.schedulers.SchedulerProvider;
//import com.baims.app.presentation.common.Constants;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class VideoActivity extends AppCompatActivity {
//
//    @BindView(R.id.vv_lecture)
//    VideoView mVideoView;
//    VideoPresenter presenter;
//
//    String videoURL;
//    //        "https://developers.google.com/training/images/tacoma_narrows.mp4";
//
//    String link;
//
//    @BindView(R.id.buffering_textview)
//    TextView mBufferingTextView;
//
//    // Current playback position (in milliseconds).
//    private int mCurrentPosition = 0;
//
//    // Tag for the instance state bundle.
//    private static final String PLAYBACK_TIME = "play_time";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
//
//        ButterKnife.bind(this);
//
//        presenter = new VideoPresenter(SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
//
//        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
//        }
//
//        // Set up the media controller widget and attach it to the video view.
//        MediaController controller = new MediaController(this);
//        controller.setMediaPlayer(mVideoView);
//        mVideoView.setMediaController(controller);
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        videoURL = getIntent().getStringExtra(Constants.KEY_VIDEO);
//        link = getIntent().getStringExtra(Constants.KEY_LECTURE_LINK);
//        // Load the media each time onStart() is called.
//        initializePlayer();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // In Android versions less than N (7.0, API 24), onPause() is the
//        // end of the visual lifecycle of the app.  Pausing the video here
//        // prevents the sound from continuing to play even after the app
//        // disappears.
//        //
//        // This is not a problem for more recent versions of Android because
//        // onStop() is now the end of the visual lifecycle, and that is where
//        // most of the app teardown should take place.
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            mVideoView.pause();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        // Media playback takes a lot of resources, so everything should be
//        // stopped and released at this time.
//        releasePlayer();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        // Save the current playback position (in milliseconds) to the
//        // instance state bundle.
//        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
//    }
//
//    private void initializePlayer() {
//        // Show the "Buffering..." message while the video loads.
//        mBufferingTextView.setVisibility(VideoView.VISIBLE);
//
//
//        // Buffer and decode the video sample.
//        Uri videoUri = getMedia(videoURL);
//        mVideoView.setVideoURI(videoUri);
//
//        // Listener for onPrepared() event (runs after the media is prepared).
//        mVideoView.setOnPreparedListener(
//                new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//
//                        // Hide buffering message.
//                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);
//
//                        // Restore saved position, if available.
//                        if (mCurrentPosition > 0) {
//                            mVideoView.seekTo(mCurrentPosition);
//                        } else {
//                            // Skipping to 1 shows the first frame of the video.
//                            mVideoView.seekTo(1);
//                        }
//
//                        // Start playing!
//                        mVideoView.start();
//
//                        presenter.addWatch(link);
//                    }
//                });
//
//        // Listener for onCompletion() event (runs after media has finished
//        // playing).
//        mVideoView.setOnCompletionListener(
//                new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(VideoActivity.this,
//                                "done",
//                                Toast.LENGTH_SHORT).show();
//
//                        // Return the video position to the start.
//                        mVideoView.seekTo(0);
//                    }
//                });
//
////        mVideoView.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                stopPlaying();
////                return true;
////            }
////        });
//    }
//
//    public void stopPlaying() {
//        mVideoView.stopPlayback();
//        this.finish();
//    }
//
//
//    // Release all media-related resources. In a more complicated app this
//    // might involve unregistering listeners or releasing audio focus.
//    private void releasePlayer() {
//        mVideoView.stopPlayback();
//    }
//
//    // Get a Uri for the media sample regardless of whether that sample is
//    // embedded in the app resources or available on the internet.
//    private Uri getMedia(String mediaName) {
//        if (URLUtil.isValidUrl(mediaName)) {
//            // Media name is an external URL.
//            return Uri.parse(mediaName);
//        } else {
//            // Media name is a raw resource embedded in the app.
//            return Uri.parse("android.resource://" + getPackageName() +
//                    "/raw/" + mediaName);
//        }
//    }
//}
