package com.example.SamuraiByte.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SamuraiByte.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButtonLoginPage, registerButtonLoginPage, registerButtonPopup;
    private EditText nameInputLogin, passwordInputLogin, nameInputRegister, emailInputRegister, passwordInputRegister;
    private DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        this.loginButtonLoginPage = (Button) findViewById(R.id.login_buttonLogin);
        this.loginButtonLoginPage.setOnClickListener(this);

        this.registerButtonLoginPage = (Button) findViewById(R.id.register_buttonLogin);
        this.registerButtonLoginPage.setOnClickListener(this);

        this.nameInputLogin = (EditText) findViewById(R.id.name_inputLogin);
//        this.nameInput.setOnClickListener(this);

        this.passwordInputLogin = (EditText) findViewById(R.id.password_inputLogin);
//        this.passwordInput.setOnClickListener(this);

//        this.nameInputRegister = findViewById(R.id.name_inputRegister);
//
//        this.emailInputRegister = findViewById(R.id.email_inputRegister);
//
//        this.passwordInputRegister = findViewById(R.id.password_inputRegister);
//
//        this.registerButtonPopup = findViewById(R.id.register_buttonRegister);
//        this.registerButtonPopup.setOnClickListener(this);
        this.dbHandler = new DatabaseHandler(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == this.loginButtonLoginPage.getId()){
            String name = this.nameInputLogin.getText().toString().strip();
            String password = this.passwordInputLogin.getText().toString().strip();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            if (this.checkInput(name, password) && this.dbHandler.checkInDB(name, password)){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        else if (view.getId() == this.registerButtonLoginPage.getId()){
            this.createRegisterDialog();
        }
    }
    private boolean checkInput(String name, String password){
//        String name = this.nameInputLogin.getText().toString().strip();
//        String password = this.passwordInputLogin.getText().toString().strip();
        return !name.isEmpty() && !password.isEmpty();
    }
    private boolean checkInput(String name, String email, String password){
        return !name.isEmpty() && !email.isEmpty() &&!password.isEmpty();
    }
    private void createRegisterDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_register);
        dialog.setCancelable(true);

//        DatabaseHandler dbHandlerCopy = this.dbHandler;

        this.nameInputRegister = (EditText) dialog.findViewById(R.id.name_inputRegister);
//        System.out.println(this.nameInputRegister);
        this.emailInputRegister = (EditText) dialog.findViewById(R.id.email_inputRegister);
        this.passwordInputRegister = (EditText) dialog.findViewById(R.id.password_inputRegister);
        this.registerButtonPopup = (Button) dialog.findViewById(R.id.register_buttonRegister);
        this.registerButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = nameInputRegister.getText().toString().strip();
                String email = emailInputRegister.getText().toString().strip();
                String password = passwordInputRegister.getText().toString().strip();
                if (checkInput(name, email, password))
                    dbHandler.addNewCourse(
                            name,
                            email,
                            password
                    );
            }
        });

        dialog.show();
    }
}