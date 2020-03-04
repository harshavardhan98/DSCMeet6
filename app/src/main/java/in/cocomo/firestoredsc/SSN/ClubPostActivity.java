package in.cocomo.firestoredsc.SSN;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

import in.cocomo.firestoredsc.R;

public class ClubPostActivity extends AppCompatActivity {

    final String TAG="ClubPostActivity";
    ListenerRegistration listenerRegistration;
    ArrayList<Comments> commentsArrayList=new ArrayList<>();
    ListView lv_comments;
    CommentsAdapter commentsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_post);

        lv_comments=findViewById(R.id.lv_comments);
        commentsAdapter=new CommentsAdapter(this,R.layout.comment_item);
        lv_comments.setAdapter(commentsAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        DocumentReference documentReference=FirebaseFirestore.getInstance().collection("club_post").document( "Z12d3WqypBP71MEE5ccK");

         listenerRegistration= documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                ArrayList<HashMap<Object,Object>> comment_data=(ArrayList<HashMap<Object,Object>>) documentSnapshot.get("comment");

                commentsArrayList.clear();
                commentsAdapter.clear();
                for(HashMap<Object,Object> i:comment_data){
                    Comments tempComment=new Comments((String)i.get("author"),(String)i.get("message"),(ArrayList<HashMap<String, String>>) i.get("reply"));
                    commentsArrayList.add(tempComment);
                }
                commentsAdapter.addAll(commentsArrayList);
                commentsAdapter.notifyDataSetChanged();


            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        listenerRegistration.remove();
    }

    void addClubPost(){

        ArrayList<HashMap<String,String>> reply=new ArrayList<>();
        HashMap<String,String> hp=new HashMap<>();
        hp.put("author","deexith");
        hp.put("message","Hello my name is deexith");
        reply.add(hp);

        hp=new HashMap<>();
        hp.put("author","logesh");
        hp.put("message","Hello my name is logesh");
        reply.add(hp);


        Comments comments1=new Comments("harsha","Hello how you doing?",reply);
        Comments comments2=new Comments("harsha","Hello how you doing?",reply);
        Comments comments3=new Comments("harsha","Hello how you doing?",reply);

        ArrayList<Comments> commentsArrayList=new ArrayList<>();
        commentsArrayList.add(comments1);
        commentsArrayList.add(comments2);
        commentsArrayList.add(comments3);


        ArrayList<String> commonData=new ArrayList<>();
        commonData.add("test string 1");
        commonData.add("test string 2");
        commonData.add("test string 3");


        ArrayList<HashMap<String,String> > fileList=new ArrayList<>();
        hp=new HashMap<>();
        hp.put("name","file1");
        hp.put("url","www.file1.com");
        fileList.add(hp);

        //ClubPost clubPost=new ClubPost("harsha","12cid",commentsArrayList,"testing descrption",
        //                    fileList,commonData,commonData,"8:50PM","test title","13id");

        HashMap<String,Long> likes=new HashMap<>();
        likes.put("harsha1@gmail.com",1L);
        likes.put("harsha2@gmail.com",2L);
        likes.put("harsha3@gmail.com",3L);



        ClubPost clubPost=new ClubPost("harsha","12cid",commentsArrayList,"desc",fileList,commonData,likes,"8:50PM","title","id");


        FirebaseFirestore.getInstance().collection("club_post").add(clubPost)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"Success insertion");
                    }
                });

    }

    void readClubPost(){

        FirebaseFirestore.getInstance().collection("club_post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){

                        ArrayList<Comments> commentsArrayList=new ArrayList<>();
                        ArrayList<HashMap<Object,Object>> comment_data=(ArrayList<HashMap<Object,Object>>) documentSnapshot.get("comment");

                        for(HashMap<Object,Object> i:comment_data){
                            Comments tempComment=new Comments((String)i.get("author"),(String)i.get("message"),(ArrayList<HashMap<String, String>>) i.get("reply"));
                            commentsArrayList.add(tempComment);
                        }


                        String author=(String)documentSnapshot.get("author");
                        String cid=(String)documentSnapshot.get("cid");
                        String description=(String) documentSnapshot.get("description");
                        ArrayList<HashMap<String,String>> fileurls=(ArrayList<HashMap<String, String>>)documentSnapshot.get("file_urls");
                        ArrayList<String> img_url=(ArrayList<String>)documentSnapshot.get("img_urls");
                        HashMap<String,Long> likeList=(HashMap<String, Long>)documentSnapshot.get("like");
                        String time=(String) documentSnapshot.get("time");
                        String title=(String) documentSnapshot.get("title");
                        String id=(String) documentSnapshot.get("id");

                        ClubPost clubPost=new ClubPost(author,cid,commentsArrayList,description,fileurls,img_url,likeList,time,title,id);

                        Log.d(TAG,"club post "+clubPost);

                    }
                }
            }
        });

    }


}


