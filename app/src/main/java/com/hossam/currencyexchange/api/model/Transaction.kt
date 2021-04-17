package com.hossam.currencyexchange.api.model
import com.google.gson.annotations.SerializedName
import java.util.*


class Transaction {


        @SerializedName("usd_amount")
        var usdAmount: Float? = null
        @SerializedName("lbp_amount")
        var lbpAmount: Float? = null
        @SerializedName("usd_to_lbp")
        var usdToLbp: Boolean? = null

        @SerializedName("id")
        var id: Int? = null

        @SerializedName("added_date")
        var added_date: String? = null

        fun setusdAmount(usdAmount: Float?) {
                this.usdAmount = usdAmount
        }

        fun setlbpAmount(lbpAmount: Float?) {
                this.lbpAmount = lbpAmount
        }

        fun setusdToLbp(usdToLbp: Boolean?) {
                this.usdToLbp = usdToLbp
        }

        fun setid(id: Int?) {
                this.id = id
        }

        fun setAddedDate(addedDate: String) {
                added_date = addedDate
        }




}