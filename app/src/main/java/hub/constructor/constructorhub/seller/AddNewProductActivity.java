package hub.constructor.constructorhub.seller;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.Upload;

public class AddNewProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button chooseButton, postButton;

    private EditText editTextHeading;
    private EditText editTextCompanyName;
    private EditText editTextCompanyAddress;
    private Spinner spinner;
    private EditText editTextPrice;
    private EditText editTextDescription;

    private ImageView imageView;

    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference mRef;

    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        chooseButton = findViewById(R.id.buttonLoadPictureId);
        postButton = findViewById(R.id.postAddBtnId);

        editTextHeading = findViewById(R.id.addHeadingId);
        editTextCompanyName = findViewById(R.id.addCompanyNameId);
        editTextCompanyAddress = findViewById(R.id.companyAddId);
        spinner = findViewById(R.id.serviceProviderId);
        editTextPrice = findViewById(R.id.addPriceId);
        editTextDescription = findViewById(R.id.addDescriptionId);
        imageView = findViewById(R.id.addImageId);
        imageView.setVisibility(View.GONE);


        storageReference = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference();

/*
        editTextDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDescription.setHeight(200);
            }
        });

*/



        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseButton.setVisibility(View.GONE);
                openFileChooser();
                imageView.setVisibility(View.VISIBLE);

            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

    }


    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void UploadImage(){

        postButton.setEnabled(false);

        final String heading = editTextHeading.getText().toString().trim();
        final String companyName = editTextCompanyName.getText().toString().trim();
        final String companyAddress = editTextCompanyAddress.getText().toString().trim();
        final String service = spinner.getSelectedItem().toString();
        final String price = editTextPrice.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();


        if (imageUri != null){

            final StorageReference fileRef = storageReference.child(System.currentTimeMillis()
            +"."+getFileExtension(imageUri));

            uploadTask = fileRef.putFile(imageUri);
            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String miUrlOk = downloadUri.toString();

                        Upload upload = new Upload(heading,companyName,companyAddress,service,price,description,miUrlOk);
                        String uploadId = mRef.push().getKey();
                        mRef.child("uploads").child(uploadId).setValue(upload);
                        mRef.child("AllUsers").child(uploadId).setValue(upload);

                        Toast.makeText(getApplicationContext(), "Your add posted successfully", Toast.LENGTH_SHORT).show();
                        imageView.setVisibility(View.GONE);
                        chooseButton.setVisibility(View.VISIBLE);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "No File selected", Toast.LENGTH_SHORT).show();
                }
            });


        }
        else {
            Toast.makeText(getApplicationContext(),"Insert image",Toast.LENGTH_LONG).show();
        }



    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);

        }
    }
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.adimg);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }*/
}

