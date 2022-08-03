package com.example.dailynews.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailynews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout txt_name, txt_email, txt_pass, txt_pass_con;
    TextView txt_sign_in;
    String name, email, pass, pass_con;
    CircleImageView circleImageView;
    static final int im_code = 1;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    Uri uri = null;
    CircularProgressButton circularProgressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        setID();
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("img");
        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressButton.startAnimation();
                email = txt_email.getEditText().getText().toString().trim();
                name = txt_name.getEditText().getText().toString().trim();
                pass = txt_pass.getEditText().getText().toString().trim();
                pass_con = txt_pass_con.getEditText().getText().toString().trim();
                if (!isValidName() | !isValidEmail() | !isValidPass() | !isValidPassCon() | !equal()) {
                    circularProgressButton.revertAnimation();
                    return;
                } else {
                    createEmailAndPassword();
                    txt_email.getEditText().setText("");
                    txt_name.getEditText().setText("");
                    txt_pass.getEditText().setText("");
                    txt_pass_con.getEditText().setText("");
                }
            }
        });
        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //To get image from gallery
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, im_code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == im_code && resultCode == RESULT_OK) {
            uri = data.getData();
            circleImageView.setImageURI(uri);
        }
    }


    boolean isValidEmail() {
        if (email.isEmpty()) {
            txt_email.getEditText().setError("Email can’t be empty..!");
            txt_email.getEditText().requestFocus();
            return false;
        }
        if (email.length() > 50) {
            txt_email.getEditText().setError("Email is too long..!");
            return false;
        }
        return true;
    }

    boolean isValidName() {
        if (name.isEmpty()) {
            txt_name.getEditText().setError("Name can’t be empty..!");
            return false;
        }
        if (name.length() > 15) {
            txt_name.getEditText().setError("Name is too long..!");
            return false;
        }
        return true;
    }

    boolean isValidPass() {
        if (pass.isEmpty()) {
            txt_pass.getEditText().setError("Password can’t be empty..!");
            return false;
        }
        if (pass.length() < 6) {
            txt_pass.getEditText().setError("Password is too small..!");
            return false;
        }
        return true;
    }

    boolean isValidPassCon() {
        if (pass_con.isEmpty()) {
            txt_pass_con.getEditText().setError("confirm Password can’t be empty..!");
            return false;
        }
        if (pass_con.length() < 6) {
            txt_pass_con.getEditText().setError("confirm Password is too small..!");
            return false;
        }
        return true;
    }

    boolean equal() {
        if (!pass.equals(pass_con)) {
            txt_pass_con.getEditText().setError("Doesn’t match..!");
            return false;
        }
        return true;
    }

    private void setID() {
        txt_name = findViewById(R.id.edit_name_create);
        txt_email = findViewById(R.id.edit_email_create);
        txt_pass = findViewById(R.id.edit_pass_create);
        txt_pass_con = findViewById(R.id.edit_pass_con_create);
        txt_sign_in = findViewById(R.id.txt_sign);
        circleImageView = findViewById(R.id.imageView_gallery);
        circularProgressButton = findViewById(R.id.btn_register);
    }

    void createEmailAndPassword() {
        @SuppressLint("UseCompatLoadingForDrawables") final Drawable d = getResources().getDrawable(R.drawable.ic_baseline_done_24);
        mAuth.createUserWithEmailAndPassword(email, pass).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                final StorageReference storageRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                                                storageRef.putFile(uri)
                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                if (taskSnapshot.getMetadata() != null) {
                                                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                            @Override
                                                                            public void onSuccess(Uri uri) {
                                                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(uri)
                                                                                        .build();
                                                                                circularProgressButton.doneLoadingAnimation(Color.parseColor("#F44336"), drawableToBitmap(d));
                                                                                mAuth.getCurrentUser().updateProfile(profileUpdates);
                                                                                Intent intent = new Intent(SignUp.this, Login.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                circularProgressButton.revertAnimation();

                                                            }
                                                        });
                                                Toast.makeText(SignUp.this, "Verification Sent To Your Account", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                                                circularProgressButton.revertAnimation();
                                            }

                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            circularProgressButton.revertAnimation();
                        }
                    }
                });


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}