package com.hossam.currencyexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
//import com.hossam.currencyexchange.api.retrofit
import com.hossam.currencyexchange.api.ExchangeService
import com.hossam.currencyexchange.api.model.ExchangeRates
import android.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var buyUsdTextView: TextView? = null
    private var sellUsdTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buyUsdTextView = findViewById(R.id.txtBuyUsdRate)
        sellUsdTextView = findViewById(R.id.txtSellUsdRate)
        //val d = Log.d("TAG", "here")
       // println("here")
        ExchangeService.exchangeApi().getExchangeRates().enqueue(object :
                Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response:
            Response<ExchangeRates>) {

                val responseBody: ExchangeRates? = response.body();
                Log.d("TAG", "I'm here ${response?.toString()}")

                println("response:")

                //val editText = findViewById<EditText>(R.id.editText_id)
                //val editTextValue = editText.text
                buyUsdTextView?.text="${(responseBody?.usdToLbp).toString()}"
                sellUsdTextView?.text="${(responseBody?.lbpToUsd).toString()}"
                //Log.d("TAG", ${responseBody.toString()})

            }
            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                Log.d("TAG", "did not work")
                return;
                TODO("Not yet implemented")

            }
        })
    }
}