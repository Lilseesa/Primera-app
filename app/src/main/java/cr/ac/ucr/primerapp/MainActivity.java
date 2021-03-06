package cr.ac.ucr.primerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import cr.ac.ucr.primerapp.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayAdapter<String> todosAdapter;
    private ArrayList<String> todosArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lvTodos = findViewById(R.id.lv_todos);
        todosArr = new ArrayList<>();

        todosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todosArr);

        lvTodos.setAdapter(todosAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {

        AppPreferences.getInstance(this).clear();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_todo:
                showAlert();
                break;
            default:
                break;
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_task, null);

        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextInputEditText etTaskName = view.findViewById(R.id.et_task_name);

                        String taskName = etTaskName.getText().toString();
                        if(!taskName.isEmpty()){
                            todosArr.add(taskName);
                            todosAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null);
        builder.create();
        builder.show();
    }
}