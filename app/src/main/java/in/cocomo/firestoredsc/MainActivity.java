package in.cocomo.firestoredsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



// https://stackoverflow.com/questions/52296395/how-to-update-array-elements-in-firestore-with-android

public class MainActivity extends AppCompatActivity  {

    final String TAG="MainActivity";


    Button btn_create,btn_updateEmail,btn_updateNumber,btn_read,btn_delete;
    EditText edt_name,edt_email,edt_phone;
    TextView tv_result;

    ArrayList<String> documentIdList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_create=findViewById(R.id.btn_create);
        btn_updateEmail=findViewById(R.id.btn_update_email);
        btn_updateNumber=findViewById(R.id.btn_update_number);
        btn_read=findViewById(R.id.btn_read);
        btn_delete=findViewById(R.id.btn_delete);

        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.edt_email);
        edt_phone=findViewById(R.id.edt_phone);
        tv_result=findViewById(R.id.tv_result);


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*
                HashMap<String,Object> data=new HashMap();
                data.put("name",edt_name.getText().toString());
                data.put("number", Arrays.asList(edt_phone.getText().toString()));
                data.put("email",Arrays.asList(edt_phone.getText().toString()));

                ArrayList<String> email=new ArrayList<>();
                email.add("a");
                email.add("b");
                email.add("c");

                ArrayList<Integer> number=new ArrayList<>();
                number.add(1);
                number.add(2);
                number.add(3);

                Contact contact=new Contact("harsha",email,number);
                 */

                ArrayList<String> email=new ArrayList<>();
                email.add(edt_email.getText().toString());

                ArrayList<Integer> number=new ArrayList<>();
                number.add(Integer.parseInt(edt_phone.getText().toString()));

                Contact contact=new Contact(edt_name.getText().toString(),email,number);

                FirebaseFirestore.getInstance().collection("contact").add(contact).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"successful insertion");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"failed insertion");
                    }
                });


            }
        });


        btn_updateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                documentIdList.clear();

                FirebaseFirestore.getInstance().collection("contact").whereEqualTo("name",edt_name.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document:task.getResult()){
                                documentIdList.add(document.getId());
                                Log.d(TAG,""+document.get("name")+"  "+document.get("number") + document.getId());
                            }

                            for(String i:documentIdList){
                                Log.d(TAG,"document id "+i);
                                FirebaseFirestore.getInstance().collection("contact").document(i).update("number", FieldValue.arrayRemove(Integer.parseInt(edt_phone.getText().toString())))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful())
                                                    Log.d(TAG,"number added to the list of numbers");
                                                else
                                                    Log.d(TAG,"number not updated yet in the list");
                                            }
                                        });
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"query failed");
                        }
                    }
                });





            }
        });


        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("contact").whereEqualTo("name",edt_name.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                Contact contact=new Contact((String)documentSnapshot.get("name"),(ArrayList<String>) documentSnapshot.get("email"),(ArrayList<Integer>) documentSnapshot.get("number"));
                                tv_result.setText(contact.getName()+"\n"+contact.getEmail()+"\n"+contact.getNumber());
                            }

                        }else{
                            Log.d(TAG,"task is a failure");
                        }
                    }
                });
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                documentIdList.clear();
                FirebaseFirestore.getInstance().collection("contact").whereEqualTo("name",edt_name.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                documentIdList.add(documentSnapshot.getId());
                                Log.d(TAG,"record fetched name "+documentSnapshot.get("name"));
                            }


                            for(String i:documentIdList)
                            {
                                FirebaseFirestore.getInstance().collection("contact").document(i).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Log.d(TAG,"delete operation successfull");
                                        }else{
                                            Log.d(TAG,"delete operation has failed");
                                        }
                                    }
                                });
                            }

                        }



                    }
                });
            }
        });



    }

}
