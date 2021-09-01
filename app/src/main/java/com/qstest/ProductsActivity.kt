package com.qstest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.qstest.adapter.ProductsAdapter
import com.qstest.api.FetchProductDetails
import com.qstest.databinding.ActivityProductsBinding
import com.qstest.models.Product
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductsBinding
    lateinit var productsArrayList: ArrayList<Product>
    lateinit var adapter: ProductsAdapter
    val url = "http://35.154.26.203/product-ids"

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loader.visibility = View.VISIBLE
        productsArrayList = ArrayList()
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductsAdapter(productsArrayList, applicationContext)
        binding.productsRecyclerView.adapter = adapter
        getProductIds()

    }

    @DelicateCoroutinesApi
    private fun getProductIds() {
        GlobalScope.launch {
            val data = FetchProductDetails().fetchJson(url)
            getFirebaseData(data)
        }
    }

    private fun getFirebaseData(data: List<String>) {
        val ref = FirebaseDatabase.getInstance().reference
        for (id: String in data) {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() and snapshot.hasChildren()) {
                        val productname = snapshot.child("product-name").child(id).value.toString()
                        val productdesc = snapshot.child("product-desc").child(id).value.toString()
                        val productimage = snapshot.child("product-image").child(id).value.toString()
                        val productprice = snapshot.child("product-price").child(id).value.toString()
                        if (!productname.isNullOrEmpty()) {
                            val product = Product(productname, productprice, productimage, productdesc)
                            binding.loader.visibility = View.GONE
                            productsArrayList.add(product)
                            adapter.update(productsArrayList)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error")
                }

            })

        }

    }

}