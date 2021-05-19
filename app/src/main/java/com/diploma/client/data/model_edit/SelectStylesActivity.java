package com.diploma.client.data.model_edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;

import java.util.ArrayList;

public class SelectStylesActivity extends AppCompatActivity {
    ArrayList<Artwork.Style> styles = Artwork.Style.allStyles;
    StylesGenresSelectorAdapter adapter;

    private String getString() {
        ArrayList<Artwork.ArtworkProperties> selected_items = new ArrayList<>();
        for (int i = 0; i < adapter.sg_items.size(); ++i) {
            if (adapter.checked[i])
                selected_items.add(adapter.sg_items.get(i));
        }

        StringBuilder sb = new StringBuilder();
        for (Artwork.ArtworkProperties item : selected_items) {
            sb.append(item.id);
            sb.append(",");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_styles);


        RecyclerView rv_styles = findViewById(R.id.selectStyles_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_styles.setLayoutManager(layoutManager);

        adapter = new StylesGenresSelectorAdapter(styles);
        rv_styles.setAdapter(adapter);


        Button confirmButton = (Button) findViewById(R.id.selectStyles_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent data = new Intent();
                data.putExtra("STYLES", getString());

                setResult(RESULT_OK, data);
                finish();
            }
        });

    }


}