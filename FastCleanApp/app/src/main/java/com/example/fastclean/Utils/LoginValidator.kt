package com.example.fastclean.Utils

import android.util.Patterns

class LoginValidator {
    companion object {
        fun isValidEmail(data: String): Boolean {

            val valid = Patterns.EMAIL_ADDRESS.matcher(data).matches()

            return valid
        }

        fun isValidPassword(data: String): Boolean {

            var valid = true

            if (data.length < 2) {
                valid = false
            }
            return valid
        }
    }
}