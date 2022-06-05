package com.octopi.recyclerviewdiffutil

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.octopi.recyclerviewdiffutil.model.PostResponse
import com.octopi.recyclerviewdiffutil.network.apiHitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val TAG = "data_db"

    val postLiveData = MutableLiveData<PostResponse>()
    val postObserver get() = postLiveData

    fun getPosts() {
        apiHitter().getPosts().enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                postLiveData.value = response.body()
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
            }

        })
    }
}