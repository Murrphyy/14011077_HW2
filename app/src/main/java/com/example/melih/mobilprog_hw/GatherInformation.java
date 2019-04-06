package com.example.melih.mobilprog_hw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class GatherInformation extends AppCompatActivity {
    ArrayList<String> allInputs = new ArrayList<String>();
    EditText etName,etSurname,etMail,etTC,etPhone;
    Spinner spDay,spMonth,spYear;
    Button saveBtn,photoBtn,clearBtn;
    ImageView imgView;
    Uri userPhotoUri;
    int i, j;
    private String[] days = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30","31"};
    private String[] months = {"00","01","02","03","04","05","06","07","08","09","10","11","12"};
    private String[] years ;

    private ArrayAdapter<String> dataAdapterForDays;
    private ArrayAdapter<String> dataAdapterForMonths;
    private ArrayAdapter<String> dataAdapterForYears;

    private static final int RESULT_LOAD_IMG = 1;
    Bundle extras;
    Intent userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather_information);

        extras=new Bundle();
        userProfile=new Intent(GatherInformation.this,UserProfile.class);

        fillYearsList();

        dataAdapterForDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        dataAdapterForMonths = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,months);
        dataAdapterForYears = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);

        dataAdapterForDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        etMail=(EditText)findViewById(R.id.etMail);
        etName=(EditText)findViewById(R.id.etName);
        etSurname=(EditText)findViewById(R.id.etSurname);
        etTC=(EditText)findViewById(R.id.etTC);
        spDay=(Spinner)findViewById(R.id.spDay);
        spMonth=(Spinner)findViewById(R.id.spMonth);
        spYear=(Spinner)findViewById(R.id.spYear);
        etPhone=(EditText)findViewById(R.id.etPhone);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        photoBtn= (Button)findViewById(R.id.photoBtn);
        imgView=(ImageView) findViewById(R.id.userPhotoIV) ;
        clearBtn=(Button)findViewById(R.id.clearBtn);

        spDay.setAdapter(dataAdapterForDays);
        spMonth.setAdapter(dataAdapterForMonths);
        spYear.setAdapter(dataAdapterForYears);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(GatherInformation.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(GatherInformation.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMG);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllFieldsFilled()){
                    allInputs.add(etMail.getText().toString());
                    allInputs.add(etName.getText().toString());
                    allInputs.add(etSurname.getText().toString());
                    allInputs.add(etTC.getText().toString());
                    allInputs.add(spDay.getSelectedItem().toString());
                    allInputs.add(spMonth.getSelectedItem().toString());
                    allInputs.add(spYear.getSelectedItem().toString());
                    allInputs.add(etPhone.getText().toString());

                    extras.putStringArrayList("infos",allInputs);
                    extras.putString("img",userPhotoUri.toString());
                    userProfile.putExtras(extras);
                    startActivity(userProfile);
                }else{
                    Toast.makeText(GatherInformation.this,"Tüm alanlar doldurulmalıdır!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spDay.setSelection(0);
                spMonth.setSelection(0);
                spYear.setSelection(0);
                etMail.getText().clear();
                etName.getText().clear();
                etSurname.getText().clear();
                etTC.getText().clear();
                etPhone.getText().clear();
                imgView.setImageResource(android.R.color.transparent);
                userPhotoUri=null;
            }
        });
    }

    public boolean isAllFieldsFilled(){
        if(etMail.getText().toString().equals("") || etPhone.getText().toString().equals("") ||etName.getText().toString().equals("") || etSurname.getText().toString().equals("") ||
                etTC.getText().toString().equals("") || spDay.getSelectedItem().toString().equals("00") || spYear.getSelectedItem().toString().equals("0000") ||
                spMonth.getSelectedItem().toString().equals("00") || userPhotoUri==null){
            return false;
        }else{
            return true;
        }

    }

    public void fillYearsList(){
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        j = 1;
        years = new String[thisYear-1899];
        years[0] = "0000";
        for(i = 1900; i < thisYear; i++){
            years[j] = Integer.toString(i);
            j++;
        }
    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgView.setImageBitmap(selectedImage);
                userPhotoUri=imageUri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(GatherInformation.this, "Hatalı İşlem", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(GatherInformation.this, "Fotoğraf Seçmedin",Toast.LENGTH_LONG).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case RESULT_LOAD_IMG:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                } else {
                    Toast.makeText(GatherInformation.this,"Galeri Kullanımına İzin Verilmedi",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
