package com.fogs.assetstest

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.InputStream


class CustomAdapter(val userList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: String) {

            // get input stream
            var ims: InputStream
            try {
                ims = itemView.context.assets.open(user)
            } catch (e: Exception){
                ims = File(user).inputStream()
            }


            val bitmap = BitmapFactory.decodeStream(ims)
            itemView.findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
        }
    }
}
