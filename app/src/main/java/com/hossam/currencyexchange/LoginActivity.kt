package com.hossam.currencyexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.hossam.currencyexchange.api.Authentication
import com.hossam.currencyexchange.api.ExchangeService
import com.hossam.currencyexchange.api.model.ExchangeRates
import com.hossam.currencyexchange.api.model.Token
import com.hossam.currencyexchange.api.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LoginActivity : AppCompatActivity() {
    private var usernameEditText: TextInputLayout? = null
    private var passwordEditText: TextInputLayout? = null
    private var submitButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameEditText = findViewById(R.id.txtInptUsername)
        passwordEditText = findViewById(R.id.txtInptPassword)
        submitButton = findViewById(R.id.btnSubmit)
        submitButton?.setOnClickListener { view ->
            login()
        }
    }
    private fun login() {
        val user = User()
        user.username = usernameEditText?.editText?.text.toString()
        user.password = passwordEditText?.editText?.text.toString()
        //Log.d("myTag", user.username!!);
        //print("user is $(user.username)");
      //  console.log(user.password)

                ExchangeService.exchangeApi().authenticate(user).enqueue(object :
                    Callback<Token> {

                    override fun onFailure(call: Call<Token>, t:
                    Throwable) {
                        print("failed");
                        return
                    }
                    override fun onResponse(call: Call<Token>, response:
                    Response<Token>) {
                      //  print("succeeded");
                        Snackbar.make(
                            submitButton as View,
                            "Logged in.",
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                      //  print("still")
                        //print ("response ${response.body().token}")
                        //var a=(response.body().toString())
                       // print("sometoken $a")

                        //val responseBody: Token? = response.body()
                        //print("here ${(response.get)}")
                        var b=response.body()
                        print("some response $b")
                        response.body()?.token?.let { Authentication.saveToken(it) }
                        onCompleted()
                    }
                })
    }
    private fun onCompleted() {
       // print("intent");
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}