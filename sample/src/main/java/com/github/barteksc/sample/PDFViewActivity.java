/**
 * Copyright 2016 Bartosz Schiller
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.barteksc.sample;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnActionEnd;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.Constants;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.github.barteksc.pdfviewer.util.Hotspot;
import com.github.barteksc.pdfviewer.util.Note;
import com.github.barteksc.pdfviewer.util.TextLine;
import com.github.barteksc.pdfviewer.util.TextNote;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.util.Size;
import com.shockwave.pdfium.util.SizeF;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.options)
public class PDFViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener, OnActionEnd, OnPageScrollListener {

    private static final String TAG = PDFViewActivity.class.getSimpleName();

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

    public static final String SAMPLE_FILE = "1.pdf";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

    @ViewById
    PDFView pdfView;

    @NonConfigurationInstance
    Uri uri;

    @NonConfigurationInstance
    Integer pageNumber = 0;

    String pdfFileName;

    @OptionsItem(R.id.pickFile)
    void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );

            return;
        }

        launchPicker();
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, R.string.toast_pick_file_error, Toast.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void afterViews() {
        pdfView.setBackgroundColor(Color.LTGRAY);
        if (uri != null) {
            displayFromUri(uri);
        } else {
            displayFromAsset(SAMPLE_FILE);
        }
        setTitle(pdfFileName);
    }

    private void displayFromAsset(String assetFileName) {

        List<Hotspot> hotspots = new ArrayList<>();
        /*hotspots.add(new Hotspot(22.58064516129032, 35.80246913580247, "play"));
        hotspots.add(new Hotspot(32.25491431451613, 35.95679012345679, "game"));
        hotspots.add(new Hotspot(12.093623991935484, 38.269193672839506, "default"));
        hotspots.add(new Hotspot(0.0, 95.37037037037037, "activity"));
        hotspots.add(new Hotspot(9.07258064516129, 83.64198001814476, "link"));
        hotspots.add(new Hotspot(66.33064516129032, 64.043214586046, "key"));
        hotspots.add(new Hotspot(7.661290322580645, 8.333333333333332, "audio"));
        hotspots.add(new Hotspot(7.661290322580645, 18.333333333333332, "check_mark"));
        hotspots.add(new Hotspot(17.661290322580645, 18.333333333333332, "document"));
        hotspots.add(new Hotspot(17.661290322580645, 28.333333333333332, "image"));
        hotspots.add(new Hotspot(27.661290322580645, 28.333333333333332, "link"));
        hotspots.add(new Hotspot(27.661290322580645, 38.333333333333332, "presentation"));*/


        List<Note> notes = new ArrayList<>();
        notes.add(new Note(50, 50, "red"));
        /*notes.add(new Note(10, 10, "blue"));
        notes.add(new Note(40, 50, "red"));
        notes.add(new Note(0, 10, "blue"));
        notes.add(new Note(30, 50, "red"));
        notes.add(new Note(100, 10, "blue"));
        notes.add(new Note(20, 50, "red"));
        notes.add(new Note(90, 10, "blue"));
        notes.add(new Note(60, 50, "red"));
        notes.add(new Note(80, 10, "blue"));
        notes.add(new Note(45, 50, "red"));
        notes.add(new Note(10, 10, "blue"));*/


        List<TextNote> textNotes = new ArrayList<>();
        List<TextLine> lines = new ArrayList<>();
        TextLine line = new TextLine(45, "#E25185", 0.43f, "Texto numero 1\nOutra linha");
        //TextLine line2 = new TextLine(45, "#E25185", 0.43f, "Outra linha");
        lines.add(line);
        //lines.add(line2);
        TextNote textNote = new TextNote(50.9, 86.7, 40.4, 5.5, "#FAE32D", 0.28f, "#A551A5",10, 0.67f, lines);




        List<TextLine> lines1 = new ArrayList<>();
        TextLine line3 = new TextLine(16, "#000000", 1.0f, "fff");
        lines1.add(line3);
        TextNote textNote1 = new TextNote(8.4, 2, 73.7, 3.1, "transparent", 1.0f, "#000000",10, 1.0f, lines1);
        textNotes.add(textNote);
        textNotes.add(textNote1);


        pdfView.setMinZoom(1);
        pdfView.setMaxZoom(10);
        pdfView.setMidZoom(5);
        Constants.Pinch.MINIMUM_ZOOM = 1;
        Constants.Pinch.MAXIMUM_ZOOM = 10;

        pdfView.fromAsset(SAMPLE_FILE)
                .swipeHorizontal(true)
                .withHotspots(hotspots)
                .withNotes(notes)
                .withTextNotes(textNotes)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .onPageScroll(this)
                .onActionEnd(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();

        pdfView.setMaxZoom(10);
        pdfView.setMinZoom(10);
    }

    private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    @OnActivityResult(REQUEST_CODE)
    public void onResult(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            uri = intent.getData();
            displayFromUri(uri);
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");


        Size pageSize = pdfView.getOriginalPageSize(0);

        Log.d("WIDTH", String.format("%d", pageSize.getWidth()));
        Log.d("HEIGHT", String.format("%d", pageSize.getHeight()));

        //pdfView.zoomTo(10);
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    /**
     * Listener for response to user permission request
     *
     * @param requestCode  Check that permission request code matches
     * @param permissions  Permissions that requested
     * @param grantResults Whether permissions granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }

    @Override
    public void actionEnd() {
        Log.d("TESTE", "END");
    }


    @Override
    public void onPageScrolledEnd() {
        Log.d("TESTE", "onPageScrolledEnd");
    }


    @Override
    public void onPageScrolled(int page, float positionOffset) {
        //Log.d("TESTE", "onPageScrolled");
    }
}
