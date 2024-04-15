package com.example.mobile_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
//import android.widget.Toast;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.ImageRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.bumptech.glide.Glide;
//import com.example.mobile_8.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Тег для логирования
    public final String TAG = "RRR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим кнопки для параллельного и последовательного запуска задач
        Button parallelButton = findViewById(R.id.parallelButton);
        Button sequenceButton = findViewById(R.id.sequenceButton);

        // Параллельный запуск
        parallelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Data.Builder и OneTimeWorkRequest для двух рабочих задач
                Data.Builder data1 = new Data.Builder();
                data1.putString("key", "Parallel 1");
                OneTimeWorkRequest work =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .setInputData(data1.build())
                                .build();

                Data.Builder data2 = new Data.Builder();
                data2.putString("key", "Parallel 2");
                OneTimeWorkRequest work2 =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .setInputData(data2.build())
                                .build();

                // Добавляем рабочие задачи в список
                List<OneTimeWorkRequest> list = new ArrayList<OneTimeWorkRequest>() {{
                    add(work);
                    add(work2);
                }};

                // Запускаем рабочие задачи параллельно с помощью WorkManager
                WorkManager.getInstance(getApplicationContext()).enqueue(list);
            }
        });

        // Последовательный запуск
        sequenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Создаем Data.Builder и OneTimeWorkRequest для трех рабочих задач
                Data.Builder data1 = new Data.Builder();
                data1.putString("key", "Sequence 1");
                OneTimeWorkRequest work =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .setInputData(data1.build())
                                .build();

                Data.Builder data2 = new Data.Builder();
                data2.putString("key", "Sequence 2");
                OneTimeWorkRequest work2 =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .setInputData(data2.build())
                                .build();

                Data.Builder data3 = new Data.Builder();
                data3.putString("key", "Sequence 3");
                OneTimeWorkRequest work3 =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .setInputData(data3.build())
                                .build();

                // Добавляем рабочие задачи в список
                List<OneTimeWorkRequest> list = new ArrayList<OneTimeWorkRequest>() {{
                    add(work);
                    add(work2);
                    add(work3);
                }};

                // Запускаем рабочие задачи последовательно с помощью WorkManager
                WorkManager.getInstance(getApplicationContext())
                        .beginWith(work)
                        .then(work2)
                        .then(work3)
                        .enqueue();
            }
        });
    }
}
