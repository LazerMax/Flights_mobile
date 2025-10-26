package com.example.succulentus

import LoggingActivity
import User
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

class LoginActivity : LoggingActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@LoginActivity, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)

        val user = intent.getSerializableExtra("user") as? User
        user?.let {
            editTextUsername.setText(it.username)
            editTextPassword.setText(it.password)
        }

        val buttonSignIn = findViewById<Button>(R.id.buttonSignedIn)
        buttonSignIn.setOnClickListener {
            if (validateLoginData()) {
                val intent = Intent(this, FlightsActivity::class.java)
                intent.putExtra("username", editTextUsername.text.toString().trim())
                startActivity(intent)
                finish()
            }
        }

        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        buttonSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateLoginData(): Boolean {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isEmpty()) {
            showToast("Введите username")
            editTextUsername.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            showToast("Введите пароль")
            editTextPassword.requestFocus()
            return false
        }

        if (!isValidUsername(username)) {
            showToast("Имя пользователя должно содержать от 3 до 20 символов (буквы, цифры, _)")
            editTextUsername.requestFocus()
            return false
        }

        if (password.length < 5) {
            showToast("Пароль должен содержать минимум 5 символов")
            editTextPassword.requestFocus()
            return false
        }

        if (!isValidPassword(password)) {
            showToast("Пароль должен содержать хотя бы одну букву и одну цифру")
            editTextPassword.requestFocus()
            return false
        }

        if (!UserManager.validateUser(username, password)) {
            showToast("Неверный username или пароль")
            return false
        }

        return true
    }

    private fun isValidUsername(username: String): Boolean {
        val usernameRegex = "^[a-zA-Z0-9_]{3,20}$"
        return Pattern.matches(usernameRegex, username)
    }

    private fun isValidPassword(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return hasLetter && hasDigit
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}