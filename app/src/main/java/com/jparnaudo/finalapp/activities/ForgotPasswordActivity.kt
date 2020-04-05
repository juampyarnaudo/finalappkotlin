package com.jparnaudo.finalapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jparnaudo.finalapp.R
import com.jparnaudo.finalapp.goToActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        buttonGoLogIn.setOnClickListener{
            goToActivity<LoginActivity>()
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        }

    }
}
