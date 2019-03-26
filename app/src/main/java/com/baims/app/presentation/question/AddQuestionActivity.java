package com.baims.app.presentation.question;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.response.UploadResponse;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.utils.Base64Converter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class AddQuestionActivity extends BaseActivity implements AddQuestionView, MediaController.MediaPlayerControl {

    private static final String IMAGE_DIRECTORY = "/baims";
    String path;
    private int GALLERY = 1, CAMERA = 2;
    String imageBase64 = "";
    String audioBase64 = "";
    String videoBase64 = "";
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.iv_browse)
    ImageView ivBrowse;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.switchIdentity)
    SwitchCompat mSwitchIdentity;
    @BindView(R.id.edt_question)
    EditText edtComment;
    @BindView(R.id.edt_display_name)
    EditText edtDisplayName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.iv_play)
    ImageView ivPlay;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    AddQuestionPresenter presenter;
    static final String AB = "abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    MediaPlayer mediaPlayer;
    Handler mHandler = new Handler();

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;

    boolean start = true;
    MediaController mediaController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        ButterKnife.bind(this);

        presenter = new AddQuestionPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
        presenter.setCourse(getIntent().getParcelableExtra(Constants.KEY_EXTRA_COURSE));
        presenter.setLectureLink(getIntent().getStringExtra(Constants.KEY_EXTRA_LECTURE_LINK));
        presenter.setQuestion(getIntent().getParcelableExtra(Constants.KEY_EXTRA_QUESTION));
        presenter.setTime(getIntent().getStringExtra(Constants.KEY_QUESTION_TIME));
        Log.i("duration",presenter.getTime());
    }

    @OnClick(R.id.tv_send)
    public void onSendClick() {

        if (!mSwitchIdentity.isChecked() && edtDisplayName.getText().toString().isEmpty()) {
            edtDisplayName.setError(getString(R.string.please_enter_display_name));
            return;
        }


        presenter.addComment(edtComment.getText().toString(), mSwitchIdentity.isChecked() ? 0.0 : 1.0, edtDisplayName.getText().toString(), "emoji", imageBase64, videoBase64, audioBase64, Constants.TYPE_COMMENT, edtEmail.getText().toString(), edtPassword.getText().toString(),presenter.getTime());// (note,comment,question)
    }

    @Override
    public void showProgress(boolean show) {
        showProgressBar(progressBar, show, this);
    }

    @Override
    public void showError(String error) {
        showDialog(this, error);
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, getString(R.string.comment_added), Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnCheckedChanged(R.id.switchIdentity)
    public void onShowIdentityChanged() {
        if (mSwitchIdentity.isChecked())
            edtDisplayName.setVisibility(View.GONE);
        else
            edtDisplayName.setVisibility(View.VISIBLE);

    }

    @Override
    public void showEmailFeilds() {
        edtEmail.setVisibility(View.VISIBLE);
        edtPassword.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_browse)
    public void onBrowse() {
        choosePhotoFromGallary();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String absPath = saveImage(bitmap);
                    imageBase64 = Base64Converter.convertToBase64(bitmap);
                    Log.e("fileName", path + mFileName);

                    showDialog(this, "Image Saved!");
                    //Toast.makeText(AddQuestionActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ivImage.setVisibility(View.VISIBLE);
                    ivImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    showDialog(this, "Failed!");
                    Toast.makeText(AddQuestionActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivImage.setVisibility(View.VISIBLE);
            ivImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            showDialog(this, "Image Saved!");
            //Toast.makeText(AddQuestionActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        path = Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY;
        File wallpaperDirectory = new File(
                path);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            mFileName = Calendar.getInstance()
                    .getTimeInMillis() + ".jpg";
            File f = new File(wallpaperDirectory, mFileName);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());


            return f.getAbsolutePath();
        } catch (IOException e1) {
            Log.e("ex", e1.getMessage());
            e1.printStackTrace();
        }
        return "";
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @OnClick(R.id.iv_record)
    public void onRecordClick() {
//        Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//        startActivityForResult(recordIntent, 1111);
        Log.e("record", "" + start);
        if (checkPermission()) {
            if (start) {
                startRecording();
            } else {
                stopRecording();
            }
        } else {
            requestPermission();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("radwa", "onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0)
            switch (requestCode) {
                case REQUEST_RECORD_AUDIO_PERMISSION:
                    permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
            }
        if (!permissionToRecordAccepted) {
            Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
            showDialog(this, "no permission");
        }

    }


    private void startRecording() {

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File audioVoice = new File(mFileName + File.separator + "voices");
        if (!audioVoice.exists()) {
            audioVoice.mkdir();
        }
        mFileName = absolutePath + File.separator + "voices/" + generateVoiceFilename(6) + ".3gpp";
        System.out.println("Audio path : " + mFileName);

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        ivRecord.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        start = false;
    }


    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        start = true;
        mediaPlayer = new MediaPlayer();
        ivRecord.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic));
        audioBase64 = Base64Converter.encodeAudio(mFileName);
        Log.e("base64", ": " + audioBase64);
        onPlayStopClick();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    private String generateVoiceFilename(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    @OnClick(R.id.iv_play)
    public void onPlayStopClick() {
        if (mediaPlayer.isPlaying()) {
            stopAudioPlay();
        } else {
            playAudio();
        }
    }

    private void playAudio() {
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(AddQuestionActivity.this);
        mediaController.setAnchorView(findViewById(R.id.layout_question));
        try {
            mediaPlayer.setDataSource(mFileName);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mHandler.post(new Runnable() {
                    public void run() {
                        mediaController.show(10000);
                        mediaPlayer.start();
                    }
                });
            }
        });
        //  ivPlay.setBackground(getResources().getDrawable(R.drawable.ic_close));
    }

    private void stopAudioPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //for media player
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        int percentage = (mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration();

        return percentage;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mediaController != null)
            mediaController.show();

        return false;
    }

    @OnClick(R.id.iv_close)
    public void closeClick()
    {
        onBackPressed();
    }


}
