package com.jparnaudo.finalapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.jparnaudo.finalapp.*
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        editTextEmail.validate {
            editTextEmail.error = if (isValidEmail(it)) null else "Email no es valido"
        }

        buttonGoLogIn.setOnClickListener{
            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        }

        buttonForgot.setOnClickListener{
            val email = editTextEmail.text.toString()
            if(isValidEmail(email)){
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                    toast("Se envio un correo para resetear la contraseña")
                    goToActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                }
            }else{
                toast("Por favor verifica que la dirección de Mail sea la correcta.")
            }
        }

    }
}
