package com.example.memesshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var img_url:String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    fun loadMeme(){
        progress_barr.visibility=View.VISIBLE

        val url="https://meme-api.com/gimme"
        val jobjrequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            try {
                 img_url=response.getString("url")

                Glide.with(this).load(img_url).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_barr.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_barr.visibility=View.GONE
                        return false
                    }

                }).into(img_meme)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, { error ->

            Log.e("TAG", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this@MainActivity, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        MySingleton.getInstance(this).addToRequestQueue(jobjrequest)
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Check this Cool Meme $img_url")
        val chooser=Intent.createChooser(intent,"Share Meme using....")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}