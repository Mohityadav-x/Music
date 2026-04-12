package com.example.music

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class Adapter(val context: Activity, val DataList: List<Data>):
    RecyclerView.Adapter<Adapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var title: TextView
    var image: ShapeableImageView
    var Artist: TextView
    init {
        title=itemView.findViewById<TextView>(R.id.Title)
        image=itemView.findViewById<ShapeableImageView>(R.id.Image)
        Artist=itemView.findViewById<TextView>(R.id.artist)
    }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
val itemView= LayoutInflater.from(context).inflate(R.layout.eachittem,parent,false)
        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return DataList.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
    val currentItem=DataList[position]
        holder.title.text=currentItem.title
        Picasso.get().load(currentItem.album.cover_medium).into(holder.image)
        holder.Artist.text=currentItem.artist.name
holder.itemView.setOnClickListener {

    if (FinalActivivty.mediaPlayer!=null){
        FinalActivivty.mediaPlayer!!.stop()
        FinalActivivty.mediaPlayer!!.release()
        FinalActivivty.mediaPlayer=null
    }
    val intent= Intent(context, FinalActivivty::class.java)
    intent.putExtra("Position",position)
    intent.putExtra("list", ArrayList(DataList))
    context.startActivity(intent)
}
    }


}