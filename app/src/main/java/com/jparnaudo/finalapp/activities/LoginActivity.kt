package com.jparnaudo.finalapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jparnaudo.finalapp.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mGoogleApiClient: GoogleApiClient by lazy { getGoogleApiClient() }
    private val RC_GOOGLE_SIGN_IN = 99

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
//Creamos el boton de google login
        buttonLogInGoogle.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
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

    //Implementar el Login con Google.
    private fun getGoogleApiClient(): GoogleApiClient {
//    este metodo hace que devuelva un ApiCLient.
//    el default_web_client_id viene del google-services (dependencias) tiene un id unico.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun loginByGoogleAccountIntoFirebase(googleAccount: GoogleSignInAccount) {
//de esta forma recibimos las credenciales.
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
//        Hacemos el login con las credenciales de google.
        mAuth.signInWithCredential(credential).addOnCompleteListener(this){
            toast("Has ingresado con tu cuenta de Google!")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                loginByGoogleAccountIntoFirebase(account!!)
            }

        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Connection Failed!!")
    }

}
