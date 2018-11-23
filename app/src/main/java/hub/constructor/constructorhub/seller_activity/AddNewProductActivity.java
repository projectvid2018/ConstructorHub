package hub.constructor.constructorhub.seller_activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import hub.constructor.constructorhub.Class.User;
import hub.constructor.constructorhub.R;
import hub.constructor.constructorhub.Class.Upload;

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
    String uploadUser;
    String userEmail;

    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.postProductProgressbarId);

        imageView.setVisibility(View.GONE);


        storageReference = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference();



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
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference userNameRef = FirebaseDatabase.getInstance()
                        .getReference("Registered Users")
                        .child(firebaseUser.getUid());
                userNameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        uploadUser = dataSnapshot.child("userName").getValue().toString();
                        userEmail = dataSnapshot.child("userEmail").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

        final String heading = editTextHeading.getText().toString().trim();
        final String companyName = editTextCompanyName.getText().toString().trim();
        final String companyAddress = editTextCompanyAddress.getText().toString().trim();
        final String service = spinner.getSelectedItem().toString();
        final String price = editTextPrice.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();


        if (heading.isEmpty()){
            editTextHeading.setError("Insert Heading");
            editTextHeading.requestFocus();
            return;
        }
        else if (companyName.isEmpty()){
            editTextCompanyName.setError("Insert Company Name");
            editTextCompanyName.requestFocus();
            return;
        }
        else if (companyAddress.isEmpty()){
            editTextCompanyAddress.setError("Insert Company Address");
            editTextCompanyAddress.requestFocus();
        }
        else if (service.isEmpty()){
            Toast.makeText(getApplicationContext(),"Select service",Toast.LENGTH_SHORT).show();
        }
        else if (price.isEmpty()) {
            editTextPrice.setError("Enter price");
            editTextPrice.requestFocus();
        }
        else if (description.isEmpty()) {
            editTextDescription.setError("Write description");
            editTextDescription.requestFocus();
        }
        else {

            if (imageUri != null){
                progressBar.setVisibility(View.VISIBLE);
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

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            final String user_uid = current_user.getUid();
                            final Upload upload = new Upload(user_uid,uploadUser,userEmail,
                                    heading,companyName,companyAddress,service,price,description,miUrlOk);
                            final String uploadId = mRef.push().getKey();
                            mRef.child("My Uploads/"+user_uid).child(uploadId).setValue(upload)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                mRef.child("Uploaded Products").child(uploadId).setValue(upload)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    progressBar.setVisibility(View.GONE);
                                                                    Toast.makeText(getApplicationContext(), "Your add posted successfully", Toast.LENGTH_SHORT).show();
                                                                    imageView.setVisibility(View.GONE);
                                                                    chooseButton.setVisibility(View.VISIBLE);

                                                                    editTextHeading.setText("");
                                                                    editTextCompanyName.setText("");
                                                                    editTextCompanyAddress.setText("");
                                                                    editTextPrice.setText("");
                                                                    editTextDescription.setText("");

                                                                }
                                                            }
                                                        });
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });



                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "No File selected", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
            else {
                Toast.makeText(getApplicationContext(),"Insert image",Toast.LENGTH_LONG).show();
            }

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

}

