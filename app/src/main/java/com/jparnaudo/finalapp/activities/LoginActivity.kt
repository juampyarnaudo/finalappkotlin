package com.jparnaudo.finalapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jparnaudo.finalapp.*
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
            if (isValidEmail(email) && isValidPassword(password)) {
                //estamos pasando el email y password cargado en los campos.
                logInByEmail(email, password)
            } else
                toast("Por favor verifica que los campos estén correctos..")
        }

        textViewForgotPassword.setOnClickListener {
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        buttonCreateAccount.setOnClickListener {
            goToActivity<SignUpActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        editTextEmail.validate {
//            is es lo mismo que decir editTextEmail.text.toString()
            editTextEmail.error = if (isValidEmail(it)) null else "El Mail es Invalido"
        }
        editTextPassword.validate {
//            is es lo mismo que decir editTextEmail.text.toString()
            editTextPassword.error =
                if (isValidPassword(it)) null else "La contraseña debe contener 6 caracteres o más, incluyendo como minimo  con 1 minuscula, 1 mayuscula, 1 número"
        }

    }

    private fun logInByEmail(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
//si el task es exitoso traemos un toast con el nombre del usuario y el mensaje
            if (task.isSuccessful) {
                if (mAuth.currentUser!!.isEmailVerified) {
                    toast("Usuario Logeado")
                } else {

                    toast("Debes verificar el email.")


//                    toast("Bienvenido ${mAuth.currentUser.toString()}!!")
//                    val currentUser = mAuth.currentUser!!
//                    currentUser.displayName
//                    currentUser.email
//                    currentUser.photoUrl
//                    currentUser.phoneNumber
//                    currentUser.isEmailVerified
                }
            } else {
                toast("Ha occurido un error, por favor intente nuevamente.")

            }
        }
    }

}
