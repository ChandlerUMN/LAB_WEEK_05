package com.example.lab_week_05

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.lab_week_05.api.CatApiService
import com.example.lab_week_05.api.CatImage


// Define your CatApiService and CatImage in separate files if not already done

class MainActivity : AppCompatActivity() {

    // Retrofit instance
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API service
    private val catApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    // TextView reference (initialized after setContentView)
    private lateinit var apiResponseView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextView after setContentView
        apiResponseView = findViewById(R.id.api_response)

        getCatImageResponse()
    }

    private fun getCatImageResponse() {
        val call = catApiService.searchImages(1, "full")
        call.enqueue(object : Callback<List<CatImage>> {
            override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
            }

            override fun onResponse(call: Call<List<CatImage>>, response: Response<List<CatImage>>) {
                if (response.isSuccessful) {
                    val catList = response.body()
                    if (!catList.isNullOrEmpty()) {
                        val catImageUrl = catList[0].url
                        apiResponseView.text = catImageUrl
                    }
                } else {
                    Log.e(MAIN_ACTIVITY, "Failed to get response\n${response.errorBody()?.string().orEmpty()}")
                }
            }
        })
    }

    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}
