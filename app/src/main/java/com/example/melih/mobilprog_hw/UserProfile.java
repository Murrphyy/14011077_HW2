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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class UserProfile extends AppCompatActivity {
    ArrayList<String> allInputs = new ArrayList<String>();
    TextView tvName,tvSurname,tvMail,tvTC,tvBirthDate,tvPhone,tvAge;
    ImageView imgView,callBtnIV,mailBtnIV;
    Button lessonListBtn;
    Uri photoUri;
    Bitmap userPhoto;
    byte[] byteArray;

    Intent callIntent,mailIntent,lessonsListIntent;
    private static final int RESULT_CALL_PHONE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String newString;

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
        } else {
            allInputs = extras.getStringArrayList("infos");

            tvMail=(TextView) findViewById(R.id.tvMail);
            tvName=(TextView) findViewById(R.id.tvName);
            tvSurname=(TextView) findViewById(R.id.tvSurname);
            tvTC=(TextView) findViewById(R.id.tvTC);
            tvBirthDate=(TextView) findViewById(R.id.tvBirthDate);
            tvPhone=(TextView) findViewById(R.id.tvPhone);
            tvAge=(TextView) findViewById(R.id.tvAge) ;
            imgView=(ImageView) findViewById(R.id.userPhotoFinalIV);
            callBtnIV=(ImageView) findViewById(R.id.callBtnIV);
            mailBtnIV=(ImageView) findViewById(R.id.mailBtnIV);
            lessonListBtn=(Button) findViewById(R.id.lessonListBtn);

            tvMail.setText("E-Mail : "+allInputs.get(0));
            tvName.setText("İsim : "+allInputs.get(1));
            tvSurname.setText("Soyisim : "+allInputs.get(2));
            tvTC.setText("TC No : "+allInputs.get(3));
            tvBirthDate.setText("Doğum Tarihi : "+allInputs.get(4)+"/"+allInputs.get(5)+"/"+allInputs.get(6));
            tvPhone.setText("Telefon : "+allInputs.get(7));
            tvAge.setText("Yaş : "+Integer.toString(Calendar.getInstance().get(Calendar.YEAR)- Integer.parseInt(allInputs.get(6))));

            //get user's photo's uri as string and parse it into uri
            photoUri=Uri.parse(extras.getString("img"));
            //parse uri to bitmap and set it to the imageview
            try{
                final InputStream imageStream = getContentResolver().openInputStream(photoUri);
                userPhoto = BitmapFactory.decodeStream(imageStream);
                imgView.setImageBitmap(userPhoto);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }

            //Intent initializations
            mailIntent= new Intent(Intent.ACTION_SENDTO);
            mailIntent.setData(Uri.parse("mailto:"+tvMail.getText().toString()));

            callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+tvPhone.getText().toString()));

            lessonsListIntent=new Intent(UserProfile.this,LessonList.class);

            callBtnIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (ActivityCompat.checkSelfPermission(UserProfile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.CALL_PHONE}, RESULT_CALL_PHONE);
                        } else {
                            startActivity(callIntent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            mailBtnIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(mailIntent);
                }
            });

            lessonListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(lessonsListIntent);
                }
            });

        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case RESULT_CALL_PHONE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    Toast.makeText(UserProfile.this,"Arama Kullanımına İzin Verilmedi",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
