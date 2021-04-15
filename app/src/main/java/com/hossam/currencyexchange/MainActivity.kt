package com.hossam.currencyexchange

//import com.hossam.currencyexchange.api.retrofit

//import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.hossam.currencyexchange.api.Authentication
import com.hossam.currencyexchange.api.ExchangeService
import com.hossam.currencyexchange.api.model.ExchangeRates
import com.hossam.currencyexchange.api.model.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var buyUsdTextView: TextView? = null
    private var sellUsdTextView: TextView? = null
    private var fab: FloatingActionButton? = null
    private var transactionDialog: View? = null
    private var menu: Menu? = null
    private var tabLayout: TabLayout? = null
    private var tabsViewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Authentication.initialize(this)
        setContentView(R.layout.activity_main)
        buyUsdTextView = findViewById(R.id.txtBuyUsdRate)
        sellUsdTextView = findViewById(R.id.txtSellUsdRate)

        tabLayout = findViewById(R.id.tabLayout)
        tabsViewPager = findViewById(R.id.tabsViewPager)
        tabLayout?.tabMode = TabLayout.MODE_FIXED
        tabLayout?.isInlineLabel = true
        // Enable Swipe
        tabsViewPager?.isUserInputEnabled = true
        // Set the ViewPager Adapter
        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle)
        tabsViewPager?.adapter = adapter
        TabLayoutMediator(tabLayout!!, tabsViewPager!!) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Exchange"
                }
                1 -> {
                    tab.text = "Transactions"
                }
            }
        }.attach()


        fab = findViewById(R.id.fab)
        fab?.setOnClickListener { view ->
            showDialog()
        }



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        setMenu()
        return true
    }
    private fun setMenu() {
        menu?.clear()
        menuInflater.inflate(if(Authentication.getToken() == null)
            R.menu.menu_logged_out else R.menu.menu_logged_in, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.login) {
            print("im herefrommain")
            Log.d("TAG2", "login")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.register) {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.logout) {
            Authentication.clearToken()
            setMenu()
        }
        return true
    }




    private fun showDialog() {
        transactionDialog = LayoutInflater.from(this)
                .inflate(R.layout.dialog_transaction, null, false)
        MaterialAlertDialogBuilder(this).setView(transactionDialog)
                .setTitle("Add Transaction")
                .setMessage("Enter transaction details")
                .setPositiveButton("Add") { dialog, _ ->
                    val usdAmount = transactionDialog?.findViewById<TextInputLayout>(R.id.txtInptUsdAmount)?.editText?.text.toString().toFloat()


                    val lbpAmount = transactionDialog?.findViewById<TextInputLayout>(R.id.txtInptLbpAmount)?.editText?.text.toString().toFloat()
                   // Log.d("TAG", "I'm here $usdAmount")
                    //print("here $usdAmount")
                    val radioGroup = transactionDialog?.findViewById<View>(R.id.rdGrpTransactionType) as RadioGroup

                    var tr= Transaction()
                    tr.usdAmount=usdAmount
                    tr.lbpAmount=lbpAmount
                   if(radioGroup.checkedRadioButtonId == transactionDialog?.findViewById<RadioButton>(R.id.rdBtnBuyUsd)?.id) {
                      println("u chose usd")
                       tr.usdToLbp=false
                    }
                    else if (radioGroup.checkedRadioButtonId == transactionDialog?.findViewById<RadioButton>(R.id.rdBtnSellUsd)?.id) {
                       tr.usdToLbp=true
                   }

                    addTransaction(tr)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->dialog.dismiss()
                }
                .show()

    }




    private fun addTransaction(transaction: Transaction) {

        ExchangeService.exchangeApi().addTransaction(
            transaction,
            if (Authentication.getToken() != null)
                "Bearer${Authentication.getToken()}"
        else null
        ).enqueue(object : Callback<Any>
        {
        override fun onResponse(call: Call<Any>, response:
            Response<Any>) {
                Snackbar.make(fab as View, "Transaction added!",
                        Snackbar.LENGTH_LONG)
                        .show()
                        //fetchRates()
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Snackbar.make(fab as View, "Could not add transaction.",
                        Snackbar.LENGTH_LONG)
                        .show()
            }
        })
    }


}