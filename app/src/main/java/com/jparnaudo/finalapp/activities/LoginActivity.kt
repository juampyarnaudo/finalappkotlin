package com.jparnaudo.finalapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jparnaudo.finalapp.R
import com.jparnaudo.finalapp.goToActivity
import com.jparnaudo.finalapp.toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//vamos a preguntar si el usuario es nulo.
//        si apagamos la app y hacemos login el usuario esta logeado
//        si cerramos la app y hacemos login el usuario esta logeado por que no ha cerrado la sesion.
//        if (mAuth.currentUser === null) {
//            toast("Nope")
//        } else {
//            toast("si esta logueado")
//            mAuth.signOut()
//        }
        buttonLogIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
//            este IF corrobora que el email y pass son "TRUE"
            if (isValidEmailAndPassword(email, password)) {
                logInByEmail(email, password)


            }
        }

        textViewForgotPassword.setOnClickListener {
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)

        }
        buttonCreateAccount.setOnClickListener {
            goToActivity<SignUpActivity>()
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        }
    }

    private fun logInByEmail(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
//si el task es exitoso traemos un toast con el nombre del usuario y el mensaje
            if (task.isSuccessful) {
                toast("Bienvenido ${mAuth.currentUser.toString()}!!")
            } else {
                toast("Ha occurido un error, por favor intente nuevamente.")

            }
        }
    }

    //esta funcion hace la validación que el email y password no esten vacios ni nulos
    private fun isValidEmailAndPassword(email: String, password: String): Boolean {
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()

    }

}
