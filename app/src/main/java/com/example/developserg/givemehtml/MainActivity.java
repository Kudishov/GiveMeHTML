package com.example.developserg.givemehtml;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText urlAdress;
    private Button buttonGo;
    private String adres;
    private TextView textXTML;
    private String htmlCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textXTML = (TextView) findViewById(R.id.textViewHTML);
        urlAdress = (EditText) findViewById(R.id.editText);
        buttonGo = (Button) findViewById(R.id.buttonGO);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adres = urlAdress.getText().toString();
                new DownloadHTML().execute(adres);
            }
        });
    }

    private class DownloadHTML extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                htmlCode = getContent(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return htmlCode;
        }

        protected void onPostExecute(String result) {
            textXTML.setText(result);
        }

        private String getContent(String path) throws IOException {
            BufferedReader reader = null;
            try {
                URL url = new URL(path);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(5000);
                c.connect();
                reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                return (stringBuilder.toString());
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }
}
