package com.octopi.recyclerviewdiffutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.octopi.recyclerviewdiffutil.model.PostResponse
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import kotlin.random.Random

class MainActivity : AppCompatActivity(), PostAdapter.ItemClickListener {

    val TAG = "main_db"
    
    private lateinit var viewModel: MainViewModel
    private lateinit var postAdapter: PostAdapter
    private val currentList: ArrayList<PostResponse.PostResponseItem> = ArrayList()
    
    override fun onCreate(savedInstanceState: Bundle?) { 
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initValue()
        observer()
        getData()
        clickEvents()
    }

    private fun clickEvents() {
        fab_button.setOnClickListener {
            addNewList()
        }
    }

    private fun initValue() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        postAdapter = PostAdapter(this)
        post_recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }

    private fun getData() {
        viewModel.getPosts()
    }

    private fun observer() {
        viewModel.postObserver.observe(this) {
            Logger.json(Gson().toJson(it))
            currentList.addAll(it)
            postAdapter.submitList(it)
            progress_bar.visibility = View.GONE

            //addNewList()
        }
    }

    private fun addNewList() {
        currentList.add(PostResponse.PostResponseItem("Android Developer",getId(),"Asad",getId()))
        postAdapter.submitList(currentList)
    }

    override fun itemClicked(position: Int,item: PostResponse.PostResponseItem) {
        Timber.i("status: ${item.isSelected}")
        item.isSelected = !item.isSelected
        currentList[position] = item
        postAdapter.submitList(currentList)
    }

    override fun deleteItem(position: Int) {
        currentList.removeAt(position)
        postAdapter.submitList(currentList)
    }

    private fun getId(): Int {
        val from = 100000
        val to = 999999
        return Random.nextInt(to - from) + from
    }
}