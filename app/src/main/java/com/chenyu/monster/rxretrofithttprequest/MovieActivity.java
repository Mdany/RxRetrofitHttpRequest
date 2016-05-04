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

    private void getMovies() {
        HttpRequest.getInstance().getTopMovie(0, 10, new Subscriber<Subject>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MovieActivity.this, "completed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MovieActivity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Subject subject) {
                Toast.makeText(MovieActivity.this, "subject---" + subject.getTitle(), Toast.LENGTH_SHORT).show();
                name.setText(subject.getTitle());
            }
        });
    }
}
