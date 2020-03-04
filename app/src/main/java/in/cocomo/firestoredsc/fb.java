package in.cocomo.firestoredsc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import in.cocomo.firestoredsc.SSN.contact_temp;

public class fb extends AppCompatActivity {

    TextView tv_score;
    //FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb);

        tv_score=findViewById(R.id.tv_score);


        //createManually();
        //createClass();

        //readDocument();
        //readCollection();

        //update();

        //listenToEvent();

        //delete();
    }

    /*public void initGoogleSignIn() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(fb.this, gso);

        //mAuth.signOut();
        //mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount acct = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(fb.this, "signIn:success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(fb.this, "signIn:failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*****************************************************/

    void createManually(){
        HashMap<String,Object> hmp=new HashMap<>();
        hmp.put("name","harsha");
        hmp.put("array", Arrays.asList("1","2","3"));
        hmp.put("flag",true);
        hmp.put("float",100.88);
        hmp.put("server timestamp", FieldValue.serverTimestamp());
        hmp.put("geopoint",new GeoPoint(50.8,45.5));

       FirebaseFirestore.getInstance().collection("dsc").document("harsha").set(hmp);

       FirebaseFirestore.getInstance().collection("dsc").add(hmp);

    }

    void createClass(){
        FirebaseFirestore.getInstance()
                .collection("dsc")
                .document("custom_doc")
                .set(new contact_temp("tarun",12,"dfdsf@gs"));
    }

    void readDocument(){
        FirebaseFirestore.getInstance().collection("dsc").document("custom_doc").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name=documentSnapshot.get("name").toString();
                Log.d("test_set",name);
            }
        });

    }

    void readCollection(){
        FirebaseFirestore.getInstance().collection("dsc").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot snapshot:queryDocumentSnapshots){
                    String name=snapshot.get("name").toString();
                    Log.d("test_set",name);
                }
            }
        });
    }

    void update(){
        FirebaseFirestore.getInstance()
                .collection("dsc")
                .document("harsha")
                .update("float",57.66);
    }

    void listenToEvent(){
        FirebaseFirestore.getInstance().collection("dsc").document("custom_doc")
            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    tv_score.setText(documentSnapshot.get("number")+" ");
                }
            });
    }

    void delete(){
        FirebaseFirestore.getInstance()
                .collection("dsc")
                .document("custom_doc")
                .delete();
    }
}
