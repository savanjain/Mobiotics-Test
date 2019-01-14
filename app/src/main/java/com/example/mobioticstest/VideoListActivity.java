package com.example.mobioticstest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class VideoListActivity extends AppCompatActivity
{
    RecyclerView rv;
    MyAdapter myAdapter;
    LinearLayoutManager llm;
    Uri uri;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;

        rv = findViewById(R.id.rv);
        myAdapter = new MyAdapter();
        llm=new LinearLayoutManager(getApplicationContext());
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(llm);

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MemberViewHolder>
    {
        public class MemberViewHolder extends RecyclerView.ViewHolder {
            TextView description, id, url, title;
            ImageView thumb;
            CardView cv;

            public MemberViewHolder(@NonNull View itemView) {
                super(itemView);
                //id = itemView.findViewById(R.id.id);
                title = itemView.findViewById(R.id.title);
                description = itemView.findViewById(R.id.description);
                thumb=itemView.findViewById(R.id.image_id);
                cv=itemView.findViewById(R.id.cvs);

            }
        }

        @NonNull
        @Override
        public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = getLayoutInflater().inflate(R.layout.designrow, null);
            MemberViewHolder mvh = new MemberViewHolder(v);
            return mvh;
        }

        @Override
        public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int i) {

            final UserData userData = Global.firebaseDataArrayList.get(i);
            memberViewHolder.title.setText(userData.getTitle());
            //  memberViewHolder.id.setText(firebaseData.getId());
            memberViewHolder.description.setText(userData.getDescription());
            uri=Uri.parse(userData.getThumb());
            // context=memberViewHolder.thumb.getContext();
            Picasso.get().load(uri).into(memberViewHolder.thumb);
            memberViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Global.userData=userData;
                    Intent in=new Intent(VideoListActivity.this,PlayVideosActivity.class);
                    startActivity(in);
                    finish();
                }
            });

        }

        @Override
        public int getItemCount() {
            return Global.firebaseDataArrayList.size();
        }
    }


    @Override
    public boolean onSupportNavigateUp()
    {
        Intent in=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(in);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(in);
        finish();
    }
}
