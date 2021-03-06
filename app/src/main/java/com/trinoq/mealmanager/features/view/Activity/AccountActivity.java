package com.trinoq.mealmanager.features.view.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trinoq.mealmanager.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.trinoq.mealmanager.R.color.colorAccent;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.backBt)
    ImageView backBt;
    @BindView(R.id.editFab)
    FloatingActionButton editFab;
    @BindView(R.id.nameEt)
    EditText nameEt;
    @BindView(R.id.phoneNumberEt)
    EditText phoneNumberEt;
    @BindView(R.id.emailEt)
    EditText emailEt;
    @BindView(R.id.addImagebackground)
    LinearLayout addImagebackground;
    @BindView(R.id.addImage)
    ImageView addImage;
    @BindView(R.id.profileCircleImageView)
    CircleImageView profileCircleImageView;

    String check="false";
    Uri uri;
    String filePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(AccountActivity.this);


        nameEt.setClickable(false);
        nameEt.setEnabled(false);
        nameEt.setFocusableInTouchMode(false);
        emailEt.setFocusableInTouchMode(false);
        phoneNumberEt.setFocusableInTouchMode(false);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //if (check.equals("false")){
            editFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (check.equals("false")){
                        addImage.setVisibility(View.VISIBLE);
                        addImagebackground.setVisibility(View.VISIBLE);

                        nameEt.setClickable(true);
                        nameEt.setEnabled(true);
                        nameEt.setFocusable(true);
                        nameEt.setFocusableInTouchMode(true);
                        emailEt.setFocusableInTouchMode(true);
                        phoneNumberEt.setFocusableInTouchMode(true);
                        nameEt.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.black));
                        nameEt.setHintTextColor(ContextCompat.getColor(AccountActivity.this,R.color.black));
                        emailEt.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.black));
                        emailEt.setHintTextColor(ContextCompat.getColor(AccountActivity.this,R.color.black));
                        phoneNumberEt.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.black));
                        phoneNumberEt.setHintTextColor(ContextCompat.getColor(AccountActivity.this,R.color.black));

                        // editFab.setBackgroundResource(ContextCompat.getDrawable(AccountActivity.this,R.drawable.ic_check_black_24dp));
                        editFab.setImageDrawable(ContextCompat.getDrawable(AccountActivity.this,R.drawable.ic_check_black_24dp));

                        Toast.makeText(AccountActivity.this, "call", Toast.LENGTH_SHORT).show();
                        check="true";
                    }
                    else if (check.equals("true"))
                    {


                        addImage.setVisibility(View.INVISIBLE);
                        addImagebackground.setVisibility(View.INVISIBLE);
                        nameEt.setFocusableInTouchMode(false);
                        emailEt.setFocusableInTouchMode(false);
                        phoneNumberEt.setFocusableInTouchMode(false);
                        nameEt.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.white));
                        nameEt.setHintTextColor(ContextCompat.getColor(AccountActivity.this,R.color.white));
                        emailEt.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.white));
                        emailEt.setHintTextColor(ContextCompat.getColor(AccountActivity.this,R.color.white));
                        phoneNumberEt.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.white));
                        phoneNumberEt.setHintTextColor(ContextCompat.getColor(AccountActivity.this,R.color.white));
                        editFab.setImageDrawable(ContextCompat.getDrawable(AccountActivity.this,R.drawable.ic_edit_black_24dp));
                        check="false";
                        Toast.makeText(AccountActivity.this, "faile", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagePickerTypeBottomSheet();
                }
            });

    }
    private void imagePickerTypeBottomSheet() {
        final BottomSheetDialog dialog = new BottomSheetDialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_selected_image);

        ImageButton cameraDialogImageBt = dialog.findViewById(R.id.cameraDialogImageBt);
        ImageButton gallaryDialogImageBt = dialog.findViewById(R.id.gallaryDialogImageBt);


        cameraDialogImageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
                dialog.dismiss();
            }
        });

        gallaryDialogImageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex1 = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex1);
                if(checkImageSize(filePath)){
                    profileCircleImageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                   // Log.d("OOO",BitmapFactory.decodeFile(filePath).toString());
                }else {
                    maximumImageAlertDialog();
                }
            }
        }else if(requestCode == 2){
            try{
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                if (thumbnail!=null){
                    filePath = saveImage(thumbnail);
                    if(checkImageSize(filePath)){
                        profileCircleImageView.setImageBitmap(thumbnail);
                    }else {
                        maximumImageAlertDialog();
                    }
                    //Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Camera connection failed try again.", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception  e){}

        }
    }


    private boolean checkImageSize(String filePath) {
        File file = new File(filePath);
        if (file.length()/1024<=5000) {//minimum upload mb in server is 5 mb
            return true;
        }
        return false;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/TscPhoto");
        if (!wallpaperDirectory.exists()) {  // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
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
            e1.printStackTrace();
        }
        return "";
    }
    private void maximumImageAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Maximum file size is 5 Mb. Select file between 5 mb and upload");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imagePickerTypeBottomSheet();
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
