package com.hossam.currencyexchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import android.widget.TextView
import com.hossam.currencyexchange.api.ExchangeService
import com.hossam.currencyexchange.api.model.ExchangeRates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExchangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExchangeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var buyUsdTextView: TextView? = null
    private var sellUsdTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchRates()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_exchange,
            container, false)

        buyUsdTextView = view.findViewById(R.id.txtBuyUsdRate)
        sellUsdTextView = view.findViewById(R.id.txtSellUsdRate)

        fetchRates()
        return view }







    private fun fetchRates(){

        ExchangeService.exchangeApi().getExchangeRates().enqueue(object :
            Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response:
            Response<ExchangeRates>
            ) {

                val responseBody: ExchangeRates? = response.body();
                // Log.d("TAG", "I'm here ${response?.toString()}")

                println("response:")


                buyUsdTextView?.text="${(responseBody?.lbpToUsd).toString()}"
                sellUsdTextView?.text="${(responseBody?.usdToLbp).toString()}"
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