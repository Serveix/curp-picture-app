package com.eliapp.curpform2;

import com.eliapp.curpform2.models.Curp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class NewCurpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_curp);
    }

    public void formBtnClicked(View view) {
        try{
            int day = Integer.parseInt(((EditText)findViewById(R.id.FormInputBirthdayDay)).getText().toString());
            int month = Integer.parseInt(((EditText)findViewById(R.id.FormInputBirthdayMonth)).getText().toString());
            int year = Integer.parseInt(((EditText)findViewById(R.id.FormInputBirthdayYear)).getText().toString());


            String name = ((EditText)findViewById(R.id.FormInputName)).getText().toString();
            String fLastName = ((EditText)findViewById(R.id.FormInputFatherLastName)).getText().toString();
            String mLastName = ((EditText)findViewById(R.id.FormInputMotherLastName)).getText().toString();
            String gender =((RadioButton)findViewById(((RadioGroup)findViewById(R.id.FormRadioGroupGender)).getCheckedRadioButtonId())).getText().toString();
            String state = ((Spinner)findViewById(R.id.FormSpinnerState)).getSelectedItem().toString();

            Curp curp = new Curp(name, fLastName, mLastName, gender, day, month, year, state);

            Intent intent = new Intent(NewCurpActivity.this, CurpInfoActivity.class);
            intent.putExtra("curp", curp);
            startActivity(intent);
        }
        catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(NewCurpActivity.this).create();
            alertDialog.setTitle(getString(R.string.title_error));
            alertDialog.setMessage(getString(R.string.body_error));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBack:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
