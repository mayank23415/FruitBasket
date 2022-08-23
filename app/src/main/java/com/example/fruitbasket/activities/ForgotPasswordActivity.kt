package com.example.fruitbasket.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.ToolbarWidgetWrapper
import com.example.fruitbasket.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_forgot_password_activity))
        val actionBar = supportActionBar
        //how to remove title from action bar 
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }

        findViewById<Toolbar>(R.id.toolbar_forgot_password_activity).setNavigationOnClickListener { onBackPressed() }

        val btn_submit = findViewById<Button>(R.id.btn_submit)
        btn_submit.setOnClickListener{
            val email = findViewById<TextView>(R.id.et_email_forgot_pw).text.toString().trim { it <= ' '}
            if(email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{task ->
                    hideProgressDialog()
                    if(task.isSuccessful) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            resources.getString(R.string.email_sent_success),
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
            }
        }


    }
}