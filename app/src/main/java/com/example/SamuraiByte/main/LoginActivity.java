package com.example.SamuraiByte.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SamuraiByte.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResultLauncher<Intent> contactLauncher;
    private Button loginButtonLoginPage, registerButtonLoginPage, registerButtonPopup;
    private ImageButton contactButtonLogin, contactButtonRegister;
    private EditText nameInputLogin, passwordInputLogin, nameInputRegister, emailInputRegister, passwordInputRegister, inputToChangeContact, respondBoxLogin, respondBoxRegister;
    private DatabaseHandler dbHandler;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        this.loginButtonLoginPage = (Button) findViewById(R.id.login_buttonLogin);
        this.loginButtonLoginPage.setOnClickListener(this);

        this.registerButtonLoginPage = (Button) findViewById(R.id.register_buttonLogin);
        this.registerButtonLoginPage.setOnClickListener(this);

        this.nameInputLogin = (EditText) findViewById(R.id.name_inputLogin);

        this.passwordInputLogin = (EditText) findViewById(R.id.password_inputLogin);

        this.respondBoxLogin = (EditText) findViewById(R.id.respond_boxLogin);

        this.contactButtonLogin = (ImageButton) findViewById(R.id.contact_button_login);
        this.contactButtonLogin.setOnClickListener(this);

        this.initContactP();
        this.dbHandler = new DatabaseHandler(this);

        this.sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        String prefName = this.sharedPref.getString("Name", "NONE");
//        String prefPassword = this.sharedPref.getString("Password", "NONE");
        this.readPrefContents();

        this.inputToChangeContact = this.nameInputLogin;
    }
    private void readPrefContents(){
        String prefName = this.sharedPref.getString("Name", "NONE");
        String prefPassword = this.sharedPref.getString("Password", "NONE");

        if (prefName != "NONE" && prefPassword != "NONE"){
            this.nameInputLogin.setText(prefName);
            this.passwordInputLogin.setText(prefPassword);
        }
    }
    private void writePrefContents(String newName, String newPassword){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString("Name", newName);
        editor.putString("Password", newPassword);
        editor.apply();
    }
    private void initContactP() {

        contactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Cursor cursor = null;

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            try {
                                Uri uri = intent.getData();

                                cursor = getContentResolver().query(uri, null, null, null, null);
                                cursor.moveToFirst();


                                int phoneIndexName = cursor.getColumnIndex
                                        (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);


                                String phoneName = cursor.getString(phoneIndexName);


                                inputToChangeContact.setText(phoneName);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == this.loginButtonLoginPage.getId()){
            String name = this.nameInputLogin.getText().toString().strip();
            String password = this.passwordInputLogin.getText().toString().strip();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            if (this.checkInput(name, password) && this.dbHandler.loginCheckInDB(name, password)){
//                Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT).show();
                this.respondBoxLogin.setText("Login successfull");
                this.writePrefContents(name, password);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("USERNAME", name);
                startActivity(intent);
            }
            else
                this.respondBoxLogin.setText("Login Failed");
//                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == this.registerButtonLoginPage.getId()){
            this.createRegisterDialog();

        }
        else if (view.getId() == this.contactButtonLogin.getId()){
            this.inputToChangeContact = this.nameInputLogin;
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK);
            contactPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            this.contactLauncher.launch(contactPickerIntent);
        }
    }
    private boolean checkInput(String name, String password){
//        String name = this.nameInputLogin.getText().toString().strip();
//        String password = this.passwordInputLogin.getText().toString().strip();
        return !name.isEmpty() && !password.isEmpty();
    }
    private boolean checkInput(String name, String email, String password){
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !this.dbHandler.registerCheckInDB(name, email);
    }
    private void createRegisterDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_register);
        dialog.setCancelable(true);
        Context context = this;

//        DatabaseHandler dbHandlerCopy = this.dbHandler;

        this.nameInputRegister = (EditText) dialog.findViewById(R.id.name_inputRegister);
//        System.out.println(this.nameInputRegister);
        this.emailInputRegister = (EditText) dialog.findViewById(R.id.email_inputRegister);
        this.passwordInputRegister = (EditText) dialog.findViewById(R.id.password_inputRegister);
        this.respondBoxRegister = (EditText) dialog.findViewById(R.id.respond_boxRegister);
        this.registerButtonPopup = (Button) dialog.findViewById(R.id.register_buttonRegister);
        this.registerButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = nameInputRegister.getText().toString().strip();
                String email = emailInputRegister.getText().toString().strip();
                String password = passwordInputRegister.getText().toString().strip();
                if (name.length() <= 9){
                    if (checkInput(name, email, password)){
                        dbHandler.addNewUsersCourse(
                                name,
                                email,
                                password
                        );
//                        Toast.makeText(context, "Registration successfull", Toast.LENGTH_SHORT).show();
                        respondBoxRegister.setText("Registration successfull");
//                        dialog.cancel();
                    }
                    else
                        respondBoxRegister.setText("Registration Failed");
//                        Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                else
                    respondBoxRegister.setText("Name is more than 9 characters!");
//                    Toast.makeText(context, "Name is more than 9 characters!", Toast.LENGTH_SHORT).show();

            }
        });
        this.contactButtonRegister = (ImageButton) dialog.findViewById(R.id.contact_button_register);
        this.contactButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                inputToChangeContact = nameInputRegister;
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK);
                contactPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                contactLauncher.launch(contactPickerIntent);
            }
        });

        dialog.show();
    }
}