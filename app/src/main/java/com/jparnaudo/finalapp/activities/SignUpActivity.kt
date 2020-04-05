package com.jparnaudo.finalapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jparnaudo.finalapp.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        botonlogin
        buttonGoLogIn.setOnClickListener {
//            esto me lleva al LoginActivity
            goToActivity<LoginActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)

//            EL GOTOACTIVITY REEMPLAZA ESTA FUNCION
////            se crea un intent desde el THIS hasta el loginactivity, pasa de un activity al otro.
//            val intent= Intent(this,LoginActivity::class.java)
////            se pone una flags, para cuando le des back, no vuelva al intent anterior.
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
        }
//         Boton para crear cuenta.
        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            if (isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(
                    password,
                    confirmPassword
                )
            ) {
                //estamos pasando el email y password cargado en los campos.
                signUpbyEmail(email, password)
            } else
                toast("Por favor verifica que los campos estén correctos..")
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
        editTextConfirmPassword.validate {
//            is es lo mismo que decir editTextEmail.text.toString()
            editTextConfirmPassword.error = if (isValidConfirmPassword(
                    editTextPassword.text.toString(),
                    it
                )
            ) null else "La contraseña no son iguales"
        }

    }

    private fun signUpbyEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Esto hace que envie un mail a su correo para verificarlo
                mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) {
                    toast("Por favor confirmar el mail.")
                    goToActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    }
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

            } else {
                toast("Ocurrió un error, por favor intenta nuevamente")
            }
        }
    }

}
