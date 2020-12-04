package com.fogs.assetstest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.assetpacks.AssetPackManagerFactory
import com.google.android.play.core.assetpacks.AssetPackStateUpdateListener
import com.google.android.play.core.assetpacks.model.AssetPackStatus
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    val assetPackName = "assetPack"
    val mAssetPackStateUpdateListener =
        AssetPackStateUpdateListener { state ->
            Log.e("qwerty", "mAssetPackStateUpdateListener onStateUpdate state: " + state.status())
            if (state.status() == AssetPackStatus.COMPLETED)
                recycler.adapter = CustomAdapter(getPhotos(), applicationContext)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = CustomAdapter(getPhotos(), applicationContext)
        AssetPackManagerFactory.getInstance(applicationContext).registerListener(mAssetPackStateUpdateListener);

        button.setOnClickListener {
            AssetPackManagerFactory.getInstance(applicationContext).fetch(Collections.singletonList(assetPackName)).addOnSuccessListener {

            }
            Log.e("myLog", applicationContext.filesDir.toString())
        }

    }

    fun getPhotos(): ArrayList<String> {
        Log.e("myLog", "1")
        val photos = ArrayList<String>()
        val folder: String = "my"
        val list: Array<String>?

        try {
            val file = File(AssetPackManagerFactory.getInstance(applicationContext).getPackLocation(assetPackName)?.assetsPath(), "my")
            file.listFiles()?.forEach {
                photos.add(it.path)
            }
            list = applicationContext.assets.list(folder)
            if (Objects.requireNonNull(list)!!.isNotEmpty()) {
                for (file in list!!) {
                    Log.e("myLog", file)
                    photos.add("$folder/$file")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return photos
    }

    override fun onDestroy() {
        super.onDestroy()
        AssetPackManagerFactory.getInstance(applicationContext).unregisterListener(mAssetPackStateUpdateListener)
    }

}

