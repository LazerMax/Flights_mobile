package com.example.succulentus

import LoggingActivity
import User
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : LoggingActivity() {

    private lateinit var editTextUsernameNew: EditText
    private lateinit var editTextTextEmailAddress: EditText
    private lateinit var editTextPasswordNew: EditText
    private lateinit var editTextPasswordNewConfirm: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        editTextUsernameNew = findViewById(R.id.editTextUsernameNew)
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress)
        editTextPasswordNew = findViewById(R.id.editTextPasswordNew)
        editTextPasswordNewConfirm = findViewById(R.id.editTextPasswordNewConfirm)

        val buttonSigningUp = findViewById<Button>(R.id.buttonSigningUp)
        val buttonHaveAccount = findViewById<Button>(R.id.buttonHaveAccount)

        buttonSigningUp.setOnClickListener {
            if (validateSignUpData()) {
                val user = User(
                    email = editTextTextEmailAddress.text.toString().trim(),
                    password = editTextPasswordNew.text.toString().trim(),
                    username = editTextUsernameNew.text.toString().trim()
                )
                if (registerNewUser(user)) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                    finish()
                }
            }
        }

        buttonHaveAccount.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun validateSignUpData(): Boolean {
        val username = editTextUsernameNew.text.toString().trim()
        val email = editTextTextEmailAddress.text.toString().trim()
        val password = editTextPasswordNew.text.toString().trim()
        val passwordConfirm = editTextPasswordNewConfirm.text.toString().trim()

        if (username.isEmpty()) {
            showToast("Введите имя пользователя")
            editTextUsernameNew.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            showToast("Введите email")
            editTextTextEmailAddress.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            showToast("Введите пароль")
            editTextPasswordNew.requestFocus()
            return false
        }

        if (passwordConfirm.isEmpty()) {
            showToast("Подтвердите пароль")
            editTextPasswordNewConfirm.requestFocus()
            return false
        }

        if (!isValidEmail(email)) {
            showToast("Введите корректный email")
            editTextTextEmailAddress.requestFocus()
            return false
        }

        if (username.length < 3) {
            showToast("Имя пользователя должно содержать минимум 3 символа")
            editTextUsernameNew.requestFocus()
            return false
        }

        if (password.length < 6) {
            showToast("Пароль должен содержать минимум 6 символов")
            editTextPasswordNew.requestFocus()
            return false
        }

        if (password != passwordConfirm) {
            showToast("Пароли не совпадают")
            editTextPasswordNewConfirm.requestFocus()
            return false
        }

        if (!isValidUsername(username)) {
            showToast("Имя пользователя должно содержать от 8 до 20 символов")
            editTextUsernameNew.requestFocus()
            return false
        }

        return true
    }

    private fun registerNewUser(user: User): Boolean {
        if (checkIfUserExists(user.username, user.email)) {
            showToast("Пользователь с таким именем или email уже существует")
            return false
        }

        if (saveUserToDatabase(user)) {
            showToast("Регистрация прошла успешно")
            return true
        } else {
            showToast("Ошибка регистрации. Попробуйте позже")
            return false
        }
    }

    private fun checkIfUserExists(username: String, email: String): Boolean {
        return false
    }

    private fun saveUserToDatabase(user: User): Boolean {
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidUsername(username: String): Boolean {
        return username.length in 8..20
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}