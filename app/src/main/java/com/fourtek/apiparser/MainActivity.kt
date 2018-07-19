package com.fourtek.apiparser

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var loginBT:Button
    private lateinit var emailET : EditText
    private lateinit var passwordET : EditText
    private lateinit var name : String
    private lateinit var password : String
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBT = findViewById(R.id.loginBT) as Button
        emailET = findViewById(R.id.emailET) as EditText
        passwordET = findViewById(R.id.passwordET) as EditText
        progressBar=findViewById(R.id.progressBar) as ProgressBar
        emailET.setText("ozair1@fourtek.com")
        passwordET.setText("admin123")
        loginBT.setOnClickListener(this)

    }

    override fun onClick(v: View?)
    {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when(v?.id){
            R.id.loginBT ->
            {
                if (valid())
                {
                    name = emailET.text.toString()
                    password = passwordET.text.toString()
                    if(isNetworkConnected(this)){
                        LoginAPI()
                    }
                    else
                    {
                        Toast.makeText(this, "Login hit", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    fun valid() : Boolean {
        if(emailET.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            return false
        } else if(passwordET.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        return if (networkInfo != null && networkInfo.isConnected) {
            true
        } else false

    }


    private fun LoginAPI()
    {
        progressBar.visibility= View.VISIBLE


        val retrofit = Retrofit.Builder()
                .baseUrl("http://miscapp.in/singlevendor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiInterface = retrofit.create(ResponseKeyAPI::class.java)

        val jsonObject = JSONObject()
        val total_request : String

        try {
            jsonObject.put("email", emailET.text.toString().trim())
            jsonObject.put("password", passwordET.text.toString().trim())
            jsonObject.put("device_id", "ghgdasjhgdasjhg")
            jsonObject.put("device_type", "android")

            Log.e("Request", jsonObject.toString())
            total_request = jsonObject.toString()

            //val call = apiInterface.login(total_request)
            val call= apiInterface.getLogin(emailET.text.toString().trim(),"12323443","android",passwordET.text.toString().trim())

            call.enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(call: Call<UserDetailResponse>, response: Response<UserDetailResponse>) =
                        try {

                            progressBar.visibility= View.GONE
                            Log.e("response", Gson().toJson(response))

                            if (response.body().getCode() == 200)
                            {

                                Toast.makeText(this@MainActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@MainActivity, Main2Activity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(this@MainActivity, "Please enter valid credentials", Toast.LENGTH_SHORT).show()

                            }

                        } catch (e: Exception) {
                            Log.e("response", Gson().toJson(response))
                            e.printStackTrace()
                        }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    progressBar.visibility= View.GONE
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun CustomDialog(mcontext: Context): Dialog {

        val tvRegistering: TextView
        // LayoutInflater inflater = LayoutInflater.from(mcontext);

        val mDialog = Dialog(mcontext,
                android.R.style.Theme_Translucent_NoTitleBar)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
        mDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        mDialog.window!!.setGravity(Gravity.CENTER)

        val lp = mDialog.window!!.attributes
        lp.dimAmount = 0.0f
        mDialog.window!!
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.window
        mDialog.window!!.attributes = lp
        //View dialoglayout = inflater.inflate(R.layout.custom_dialog, null);
        val dialoglayout = View.inflate(mcontext, R.layout.circular_progress, null)
        tvRegistering = dialoglayout.findViewById(R.id.tv_msg) as TextView
        tvRegistering.text = "Please wait.."
        mDialog.setContentView(dialoglayout)
        return mDialog
    }

}

