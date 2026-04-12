package com.example.music

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import android.os.Handler
import android.widget.ImageView
import retrofit2.http.Query


lateinit var handler: Handler
var runnable: Runnable?=null
lateinit var recyclerView: RecyclerView
lateinit var apiservice: Apiinterface
class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val search = findViewById<TextInputEditText>(R.id.search)
         recyclerView = findViewById<RecyclerView>(R.id.recycler)

        handler = Handler(Looper.getMainLooper())


        search.addTextChangedListener {
            val query = it.toString()
            runnable?.let { handler.removeCallbacks(it) }

            runnable = Runnable {
                if (query.isEmpty()) {
                    getSongs("top")
                }else if(query.length>2){
                    getSongs(query)
                }
            }
            handler.postDelayed(runnable!!, 500)
        }


        apiservice = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)
        getSongs("arijit singh")

        val exit=findViewById<ImageView>(R.id.exitbtn)
        exit.setOnClickListener {
            finish()
        }
    }

    fun getSongs(query: String) {
        apiservice.getData(query).enqueue(object : Callback<Mydata> {
            override fun onResponse(call: Call<Mydata?>, response: Response<Mydata?>) {
                val dataList = response.body()?.data ?: emptyList()
                val adapter = Adapter(this@MainActivity, dataList)
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adapter
            }


            override fun onFailure(call: Call<Mydata?>, t: Throwable) {
                Log.d("Main Activity", "onFailure:" + t.message)

            }
        })
    }
}

