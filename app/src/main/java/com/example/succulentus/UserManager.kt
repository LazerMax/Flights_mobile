package com.example.succulentus

import User

object UserManager {
    val registeredUsers = mutableListOf(
        User("user@example.com", "password123", "john_doe"),
        User("test@test.com", "test12345", "test_user"),
        User("admin@admin.com", "admin123", "admin")
    )

    fun validateUser(username: String, password: String): Boolean {
        return registeredUsers.any { user ->
            user.username.equals(username, ignoreCase = true) && user.password == password
        }
    }

    fun userExists(username: String, email: String): Boolean {
        return registeredUsers.any { user ->
            user.username.equals(username, ignoreCase = true) ||
                    user.email.equals(email, ignoreCase = true)
        }
    }
}