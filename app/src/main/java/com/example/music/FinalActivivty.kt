package com.example.music

import android.app.admin.TargetUser
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class FinalActivivty : AppCompatActivity() {
    companion object{
        var mediaPlayer: MediaPlayer?=null
    }
    lateinit var songlist: ArrayList<Data>
    var position=0

    lateinit var seekBar: SeekBar
    lateinit var play: ImageView
    lateinit var next: ImageView
    lateinit var prev: ImageView

    val handler= Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_final_activivty)
        seekBar=findViewById(R.id.seekBar1)
        play=findViewById(R.id.playbtn)
        next=findViewById(R.id.nextbtn)
        prev=findViewById(R.id.prevbtn)


        songlist=intent.getSerializableExtra("list") as ArrayList<Data>

        position=intent.getIntExtra("Position",0)


        playSong()

        play.setOnClickListener {
            if (mediaPlayer?.isPlaying==true){
                mediaPlayer?.pause()
                play.setImageResource(android.R.drawable.ic_media_play)
            }else {
                mediaPlayer?.start()
                play.setImageResource(android.R.drawable.ic_media_pause)
            }
        }
        next.setOnClickListener {
            position++
            if (position>= songlist.size){
                position=0
            }
            playSong()
        }
        prev.setOnClickListener {
            position--
            if (position<0){
                position=songlist.size-1
            }
            playSong()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?,progress: Int,fromUser: Boolean){
               if (fromUser){
                   mediaPlayer?.seekTo(progress)
               }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?){}
            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })
        val header=findViewById<ImageView>(R.id.headerbtn)
        header.setOnClickListener {
            finish()
        }
        }

    fun playSong(){
        val song=songlist[position]
        if (mediaPlayer!=null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer=null
        }

        findViewById<TextView>(R.id.finaltitle).text=song.title
        findViewById<TextView>(R.id.finalartist).text=song.artist.name
        Picasso.get().load(song.album.cover).into(findViewById<ShapeableImageView>(R.id.img))


        mediaPlayer= MediaPlayer().apply {
            setDataSource(song.preview)
            prepare()
            start()
        }

        seekBar.max=mediaPlayer!!.duration
        updateseekbar()
        mediaPlayer?.setOnCompletionListener {
            position++
            if(position>=songlist.size){
                position=0
            }
            playSong()
        }
        play.setImageResource(android.R.drawable.ic_media_pause)
    }
    fun updateseekbar(){
        handler.postDelayed(object: Runnable{
            override fun run(){
                mediaPlayer?.let{
                    seekBar.progress=it.currentPosition
                }
                handler.postDelayed(this,500)
            }
        },0)


    }
}