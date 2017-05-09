package com.ogloba.kyc.mrz.scanner.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microblink.activity.ScanCard;
import com.microblink.hardware.camera.CameraType;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognitionResult;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.recognizers.settings.RecognizerSettingsUtils;
import com.microblink.results.ocr.OcrResult;
import com.microblink.util.RecognizerCompatibility;
import com.microblink.util.RecognizerCompatibilityStatus;
import com.ogloba.kyc.mrz.scanner.android.model.IdentificationDetail;

import java.text.SimpleDateFormat;

public class MrzScanActivity extends AppCompatActivity {

    private final static String TAG = MrzScanActivity.class.getSimpleName();

    public final static String EXTRAS_SCAN_RESULT = "EXTRAS_SCAN_RESULT";
    public final static int REQUEST_CODE = 666;
    private final static int SDK_REQUEST_CODE = 777;

    private String LICENSE_KEY;

    private TextView surname;
    private TextView forename;
    private TextView gender;
    private TextView dateOfBirth;
    private TextView documentType;
    private TextView documentNumber;
    private TextView expireDate;
    private TextView nationality;

    private IdentificationDetail identificationDetail;

    /**
     * Launching MRZ Scanner
     * @param activity Activity for using library project
     * @param license_key the license key from BlinkId for using MRZ scanner.
     */
    public static void startMrzScanActivity(Activity activity, String license_key) {
        Intent intent = new Intent(activity, MrzScanActivity.class);
        intent.putExtra("LICENSE_KEY", license_key);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrz_scan);
        Log.d(TAG, "Using Application package name: " + getPackageName());

        setupView();

        this.LICENSE_KEY = getIntent().getStringExtra("LICENSE_KEY");

        if(checkIfBlinkIDisSupported()){
            startScanning();
        }else {
            finish();
        }
    }

    private void setupView() {

        ((TextView) findViewById(R.id.mrzscannerTitleBarText)).setText(R.string.mrz_scanner_title_bar_text);

        ((TextView)findViewById(R.id.mrzComponentDataRowSurname).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_surname);
        ((TextView)findViewById(R.id.mrzComponentDataRowForename).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_forename);
        ((TextView)findViewById(R.id.mrzComponentDataRowGender).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_gender);
        ((TextView)findViewById(R.id.mrzComponentDataRowDateOfBirth).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_date_of_birth);
        ((TextView)findViewById(R.id.mrzComponentDataRowDocumentType).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_document_type);
        ((TextView)findViewById(R.id.mrzComponentDataRowDocumentNumber).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_document_number);
        ((TextView)findViewById(R.id.mrzComponentDataRowExpireDate).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_expire_date);
        ((TextView)findViewById(R.id.mrzComponentDataRowNationality).findViewById(R.id.mrzTextViewDataLabel)).setText(R.string.mrz_scanner_label_nationality);

        this.surname = (TextView)findViewById(R.id.mrzComponentDataRowSurname).findViewById(R.id.mrzTextViewDataValue);
        this.forename = (TextView)findViewById(R.id.mrzComponentDataRowForename).findViewById(R.id.mrzTextViewDataValue);
        this.gender = (TextView)findViewById(R.id.mrzComponentDataRowGender).findViewById(R.id.mrzTextViewDataValue);
        this.dateOfBirth = (TextView)findViewById(R.id.mrzComponentDataRowDateOfBirth).findViewById(R.id.mrzTextViewDataValue);
        this.documentType = (TextView)findViewById(R.id.mrzComponentDataRowDocumentType).findViewById(R.id.mrzTextViewDataValue);
        this.documentNumber = (TextView)findViewById(R.id.mrzComponentDataRowDocumentNumber).findViewById(R.id.mrzTextViewDataValue);
        this.expireDate = (TextView)findViewById(R.id.mrzComponentDataRowExpireDate).findViewById(R.id.mrzTextViewDataValue);
        this.nationality = (TextView)findViewById(R.id.mrzComponentDataRowNationality).findViewById(R.id.mrzTextViewDataValue);

        Button confirm = (Button) findViewById(R.id.mrzComponentNormalButtonConfirm);
        confirm.setText(R.string.mrz_scanner_button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(EXTRAS_SCAN_RESULT, identificationDetail);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button cancel = (Button) findViewById(R.id.mrzComponentNormalButtonCancel);
        cancel.setText(R.string.mrz_scanner_button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showIdentificationDetail() {
        if(identificationDetail != null){
            this.surname.setText(identificationDetail.getSurname());
            this.forename.setText(identificationDetail.getForename());
            this.gender.setText(identificationDetail.getGender());
            this.dateOfBirth.setText(identificationDetail.getDateOfBirth());
            this.documentType.setText(identificationDetail.getDocumentType());
            this.documentNumber.setText(identificationDetail.getDocumentNumber());
            this.expireDate.setText(identificationDetail.getExpireDate());
            this.nationality.setText(identificationDetail.getNationality());
        }else {
            finish();
        }
    }

    private boolean checkIfBlinkIDisSupported() {
        boolean isSupported = false;

        RecognizerCompatibilityStatus status = RecognizerCompatibility.getRecognizerCompatibilityStatus(this);
        if(status == RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED) {
            Toast.makeText(this, R.string.mrz_scanner_message_blinkid_supported, Toast.LENGTH_SHORT).show();
            isSupported = true;
        } else {
            Toast.makeText(this, R.string.mrz_scanner_message_blinkid_not_supported + status.name(), Toast.LENGTH_SHORT).show();
        }

        return isSupported;
    }

    private void startScanning() {
        RecognitionSettings settings = new RecognitionSettings();
        settings.setNumMsBeforeTimeout(30000); //timeout after 30 seconds.

        RecognizerSettings[] settArray = setupSettingsArray();
        if( !RecognizerCompatibility.cameraHasAutofocus(CameraType.CAMERA_BACKFACE, this)){
            settArray = RecognizerSettingsUtils.filterOutRecognizersThatRequireAutofocus(settArray);
        }

        settings.setRecognizerSettingsArray(settArray);

        Intent intent = new Intent(MrzScanActivity.this, ScanCard.class);
        intent.putExtra(ScanCard.EXTRAS_LICENSE_KEY, LICENSE_KEY);
        intent.putExtra(ScanCard.EXTRAS_RECOGNITION_SETTINGS, settings);
        intent.putExtra(ScanCard.EXTRAS_BEEP_RESOURCE, R.raw.beep);

        startActivityForResult(intent, SDK_REQUEST_CODE);
    }

    private RecognizerSettings[] setupSettingsArray() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();

        return new RecognizerSettings[] { sett };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SDK_REQUEST_CODE){
            if(resultCode == ScanCard.RESULT_OK && data != null){
                RecognitionResults results = data.getParcelableExtra(ScanCard.EXTRAS_RECOGNITION_RESULTS);

                BaseRecognitionResult[] dataArray = results.getRecognitionResults();

                for (BaseRecognitionResult baseResult : dataArray) {
                    if (baseResult instanceof MRTDRecognitionResult) {
                        MRTDRecognitionResult result = (MRTDRecognitionResult) baseResult;

                        if (result.isValid() && !result.isEmpty()) {
                            if (result.isMRZParsed()) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", getApplication().getResources().getConfiguration().locale);

                                identificationDetail = new IdentificationDetail();

                                identificationDetail.setSurname(result.getPrimaryId());
                                identificationDetail.setForename(result.getSecondaryId());
                                identificationDetail.setGender(result.getSex());
                                identificationDetail.setDateOfBirth(sdf.format(result.getDateOfBirth()));
                                identificationDetail.setDocumentNumber(result.getDocumentNumber());
                                identificationDetail.setDocumentType(result.getDocumentType().toString().replace("MRTD_TYPE_", ""));
                                identificationDetail.setExpireDate(sdf.format(result.getDateOfExpiry()));
                                identificationDetail.setNationality(result.getNationality());

                            } else {
                                OcrResult rawOcr = result.getOcrResult();
                            }
                        }
                    }
                }
            }
        }

        showIdentificationDetail();
    }
}
