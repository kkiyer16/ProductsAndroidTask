package com.qstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.qstest.databinding.ActivityProductsBinding
import okhttp3.*
import java.io.IOException


class ProductsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductsBinding
    lateinit var productsArrayList : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsArrayList = ArrayList()

        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchJson()
    }

    private fun fetchJson(){
        val url = "http://35.154.26.203/product-ids"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute Request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)
            }

        })
    }
}