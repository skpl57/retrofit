package com.example.retrofit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btn;
    private RadioGroup radioGroupa;
    private RadioButton radioA, radioB, radioC;

    private int nrPytania = 0;
    private int wybranaOdp = 0;
    private List<Pytanie> pytaniaNet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        https://my-json-server.typicode.com/skpl57/pytaniaRetrofit
        textView = findViewById(R.id.trescPytania);
        btn = findViewById(R.id.button);

        radioA = findViewById(R.id.radioButton);
        radioB = findViewById(R.id.radioButton2);
        radioC = findViewById(R.id.radioButton3);
        radioGroupa = findViewById(R.id.radioGroup);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/skpl57/pytaniaRetrofit/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Pytanie>> call = jsonPlaceHolderApi.getPytania();
        call.enqueue(
                new Callback<List<Pytanie>>() {
                    @Override
                    public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, response.code() + "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pytaniaNet = response.body();
                        wypiszPytanie(nrPytania);
                    }

                    @Override
                    public void onFailure(Call<List<Pytanie>> call, Throwable t) {

                    }
                }
        );

        btn.setOnClickListener(
                v -> {
                    RadioButton temp = findViewById(radioGroupa.getCheckedRadioButtonId());
                    RadioButton temp2 = (RadioButton) radioGroupa.getChildAt(pytaniaNet.get(nrPytania).getPoprawna());

                    if (temp == null) return;

                    if(temp == temp2) Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Źle !", Toast.LENGTH_SHORT).show();

                    nrPytania++;
                    wypiszPytanie(nrPytania);
                }
        );


    }

    private void wypiszPytanie(int x){
        radioGroupa.clearCheck();
        Pytanie tempPytanie = pytaniaNet.get(x);
        textView.setText(tempPytanie.getTrescPytania());
        radioA.setText(tempPytanie.getOdpA());
        radioB.setText(tempPytanie.getOdpB());
        radioC.setText(tempPytanie.getOdpC());
    }
}