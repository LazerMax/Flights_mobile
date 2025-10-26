package com.example.succulentus

import LoggingActivity
import User
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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

        // Проверка формата username (как в LoginActivity)
        if (!isValidUsername(username)) {
            showToast("Имя пользователя должно содержать от 3 до 20 символов (буквы, цифры, _)")
            editTextUsernameNew.requestFocus()
            return false
        }

        if (!isValidEmail(email)) {
            showToast("Введите корректный email")
            editTextTextEmailAddress.requestFocus()
            return false
        }

        // Проверка пароля (как в LoginActivity)
        if (password.length < 5) {
            showToast("Пароль должен содержать минимум 5 символов")
            editTextPasswordNew.requestFocus()
            return false
        }

        if (!isValidPassword(password)) {
            showToast("Пароль должен содержать хотя бы одну букву и одну цифру")
            editTextPasswordNew.requestFocus()
            return false
        }

        if (password != passwordConfirm) {
            showToast("Пароли не совпадают")
            editTextPasswordNewConfirm.requestFocus()
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
        return hasLetter && hasDigit && password.length >= 5
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Обновленная функция регистрации
    private fun registerNewUser(user: User): Boolean {
        if (checkIfUserExists(user.username, user.email)) {
            showToast("Пользователь с таким именем или email уже существует")
            return false
        }

        if (saveUserToDatabase(user)) {
            // ДОБАВЛЯЕМ пользователя в список для проверки при входе
            addUserToRegisteredList(user)
            showToast("Регистрация прошла успешно")
            return true
        } else {
            showToast("Ошибка регистрации. Попробуйте позже")
            return false
        }
    }

    private fun addUserToRegisteredList(user: User) {
        // Добавляем пользователя в общий список для проверки при входе
        UserManager.registeredUsers.add(user)
    }

    private fun checkIfUserExists(username: String, email: String): Boolean {
        // Проверяем нет ли пользователя с таким username или email
        return UserManager.registeredUsers.any {
            it.username.equals(username, ignoreCase = true) ||
                    it.email.equals(email, ignoreCase = true)
        }
    }

    private fun saveUserToDatabase(user: User): Boolean {
        // В реальном приложении здесь будет сохранение в БД
        // Пока просто возвращаем true для демонстрации
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}