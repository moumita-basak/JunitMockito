package com.infosys.junitmockito.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.infosys.junitmockito.Adapter.CustomAdapter
import com.infosys.junitmockito.R
import com.infosys.junitmockito.modelnew.ItemRow
import com.infosys.junitmockito.modelnew.Items
import com.infosys.junitmockito.network.MyApi
import com.infosys.junitmockito.util.CommonConstants

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ItemsRxJavaActivity : AppCompatActivity() {
    private val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"
    var retrofit: Retrofit? = null
    private val TAG = ItemsRxJavaActivity::class.java.simpleName
    private lateinit  var recycler: RecyclerView
    private lateinit var customAdapter: CustomAdapter
    private var mItemList: MutableList<ItemRow> = ArrayList<ItemRow>()
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_rx_java)
        recycler = findViewById(R.id.item_recyclerView)
//        customAdapter= CustomAdapter(this, mItemList)

        initRecyclerView()
//        loadJSON()
        callEndpoints()
    }


    @SuppressLint("CheckResult")
    private fun callEndpoints() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor).readTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build()
        val gson = GsonBuilder()
                .setLenient()
                .create()

        retrofit = Retrofit.Builder()
                .baseUrl(CommonConstants.BASE_URL)
                .client(okkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val myAPi: MyApi = retrofit!!.create(MyApi::class.java)


        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
           myAPi.getItemData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))

    }
    private fun onFailure(t: Throwable) {
        Toast.makeText(this,t.message, Toast.LENGTH_SHORT).show()
    }

    private fun onResponse(response: Items) {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        customAdapter = CustomAdapter(this, response.rows)
        recycler.adapter = customAdapter
        }

    private fun handleResults(itemList: MutableList<ItemRow>) {
        if (itemList.size != 0) {
            mItemList = ArrayList(itemList)
            customAdapter = CustomAdapter(this, mItemList)
            recycler.adapter = customAdapter
        } else {
            Toast.makeText(
                this, "NO RESULTS FOUND",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handleResponse(itemList: List<ItemRow>) {
        if (itemList.isNotEmpty()) {
            mItemList = ArrayList(itemList)
            customAdapter = CustomAdapter(this, mItemList)
            recycler.adapter = customAdapter
        }
        else {
            Toast.makeText(
                this, "NO RESULTS FOUND",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handleError(error: Throwable) {

        Log.d(TAG, error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        customAdapter= CustomAdapter(this, mItemList)
        recycler.adapter = customAdapter
    }

}