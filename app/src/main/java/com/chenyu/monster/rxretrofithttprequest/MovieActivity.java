package com.chenyu.monster.rxretrofithttprequest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyu.monster.rxretrofithttprequest.Entity.Subject;
import com.chenyu.monster.rxretrofithttprequest.framework.HttpRequest;

import java.util.List;

import rx.Subscriber;

/**
 * Created by chenyu on 16/4/26.
 */
public class MovieActivity extends AppCompatActivity {
    private TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_movie);
        name = (TextView) findViewById(R.id.tv_name);
        getMovies();
    }

    private void getMovies(){
        HttpRequest.getInstance().getTopMovie(0, 10, new Subscriber<List<Subject>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MovieActivity.this,"completed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MovieActivity.this,"error",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(List<Subject> subjects) {
                if (subjects == null|| subjects.size() == 0){
                    Toast.makeText(MovieActivity.this,"no data",Toast.LENGTH_LONG).show();
                    return;
                }
                name.setText(subjects.get(0).getTitle());
            }
        });
    }
}
