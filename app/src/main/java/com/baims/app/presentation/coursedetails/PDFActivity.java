package com.baims.app.presentation.coursedetails;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baims.app.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFActivity extends AppCompatActivity {

    @BindView(R.id.pdfView)
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        ButterKnife.bind(this);
        pdfView.fromUri(Uri.parse("https://baims.com/uploads/video/watermarked_probability_3_2.pdf"));
        pdfView.loadPages();

//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        try {
//            startActivityForResult(Intent.createChooser(intent, "Select a PDF "), 1548);
//        } catch (ActivityNotFoundException f) {
//            Toast.makeText(PDFActivity.this, "Activity Not Found", Toast.LENGTH_SHORT).show();
//        }
    }
}
