package com.example.interntaskvendor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Home extends AppCompatActivity {
    String _category,_price,_name,_gst,_offer,_charge;
    ImageView image1,image2,image3;
    public static final int GalleryPick = 1;
    private Uri ImageUri;
    EditText name,price,gst,offer,category,deliverycharge;
    private ProgressDialog loadingbar;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        upload = findViewById(R.id.upload);
        name = findViewById(R.id.name);
        price= findViewById(R.id.price);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        gst=findViewById(R.id.gst);
        offer=findViewById(R.id.offer);
        category=findViewById(R.id.category);
        deliverycharge=findViewById(R.id.charge);
        loadingbar = new ProgressDialog(this);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_menu:
                        Toast.makeText(Home.this,"Menu",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_order:
                        Toast.makeText(Home.this,"Order",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_payin:
                        Toast.makeText(Home.this,"PAY IN",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(Home.this,"Profile",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    private void ValidateProductData()
    {
        _category=category.getText().toString();
        _price=price.getText().toString();
        _name=name.getText().toString();
        _gst=gst.getText().toString();
        _offer=offer.getText().toString();
        _charge=deliverycharge.getText().toString();
        if (ImageUri==null)
        {
            Toast.makeText(this, "Product Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(_category))
        {
            Toast.makeText(this, "Category is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(_name))
        {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(_price))
        {
            Toast.makeText(this, "Product Price is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(_charge))
        {
            Toast.makeText(this, "Delivier charge  is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(_gst))
        {
            Toast.makeText(this, "GST charge is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(_offer))
        {
            Toast.makeText(this, "Product Offer is required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingbar.setMessage("Please Wait");
        loadingbar.setTitle("Adding New Product");
        loadingbar.setCancelable(false);
        loadingbar.show();
        UploadImage();


    }

    public void UploadImage() {

        if (ImageUri != null) {

            loadingbar.setTitle("Image is Uploading...");
            loadingbar.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(ImageUri));
            storageReference2.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempImageName = name.getText().toString().trim();
                            String TempImageDescription = category.getText().toString().trim();
                            String TempImagePrice = price.getText().toString().trim();
                            String TempImageoffer = offer.getText().toString().trim();
                            String TempImagegst = gst.getText().toString().trim();
                            String TempImagecharge = deliverycharge.getText().toString().trim();
                            loadingbar.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    uploadinfo imageUploadInfo = new uploadinfo(TempImageName,TempImageDescription,TempImagePrice,TempImagegst,TempImagecharge,TempImageoffer, url);
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.setValue(imageUploadInfo);
                                }
                            });

//                            @SuppressWarnings("VisibleForTests")
//
//                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName,TempImageDescription,TempImagePrice, taskSnapshot.getUploadSessionUri().toString());
//                            String ImageUploadId = databaseReference.push().getKey();
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(Home.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    private void OpenGallery()
    {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            image1.setImageURI(ImageUri);
        }
    }
}