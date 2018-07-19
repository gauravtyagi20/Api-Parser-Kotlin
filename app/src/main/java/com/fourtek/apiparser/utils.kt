package com.fourtek.apiparser

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.util.regex.Pattern

/**
 * Created by fourtek on 28/2/18.
 */
open class utils{
    @SuppressLint("MissingPermission")
    public fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        return if (networkInfo != null && networkInfo.isConnected) {
            true
        } else false

    }

    // Checking email format

    open fun isEmailValid(email: String): Boolean {
        val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

        val pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return if (matcher.matches())
            true
        else
            false
    }
}