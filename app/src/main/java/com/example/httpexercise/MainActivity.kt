package com.example.httpexercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}

class MainActivity : AppCompatActivity(), OnUserClickListener {

    private val userList = ArrayList<User>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        FetchUserData(object : FetchUserData.OnDataFetchedListener {
            override fun onDataFetched(users: List<User>) {
                this@MainActivity.userList.addAll(users)
                displayUsers()
            }
        }).execute("https://lebavui.github.io/jsons/users.json")
    }

    private fun displayUsers() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = UserAdapter(userList, this)
    }

    override fun onUserClick(lat: String, lng: String) {
        showMap(lat, lng)
    }

    private fun showMap(lat: String, lng: String) {
        val uri = "geo:$lat,$lng?q=$lat,$lng"
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}



