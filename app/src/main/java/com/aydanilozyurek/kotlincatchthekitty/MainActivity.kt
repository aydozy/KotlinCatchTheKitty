package com.aydanilozyurek.kotlincatchthekitty

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aydanilozyurek.kotlincatchthekitty.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score = 0
    var runnable : Runnable = Runnable { }
    var handler : Handler = Handler(Looper.getMainLooper())
    var images = ArrayList<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Images
        images.add(binding.imageView3)
        images.add(binding.imageView4)
        images.add(binding.imageView5)
        images.add(binding.imageView6)
        images.add(binding.imageView7)
        images.add(binding.imageView8)
        images.add(binding.imageView9)
        images.add(binding.imageView10)
        images.add(binding.imageView11)
        images.add(binding.imageView12)
        images.add(binding.imageView13)
        images.add(binding.imageView14)

        hideImages()

        //CountDownTimer

        object : CountDownTimer(10500, 1000){
            override fun onTick(millisUntilFinished: Long) {
               binding.timeText.text = "Time: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                binding.timeText.text = "Time: 0"
                handler.removeCallbacks(runnable)
                for (image in images) {
                    image.visibility = View.INVISIBLE
                }

                //AlertDialog
                var alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart The Game ?")
                alert.setPositiveButton("YES",DialogInterface.OnClickListener{ dialog, which ->
                    //restart the game (restart activity)
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                })
                alert.setNegativeButton("NO",DialogInterface.OnClickListener { dialog, which ->  
                    Toast.makeText(this@MainActivity, "Game Over!", Toast.LENGTH_LONG).show()
                })
                alert.show()
            }
        }.start()

        }

    fun increaseScore(view:View){
        score++;
        binding.scoreText.text = "Score: ${score}"
    }

    fun hideImages(){
        runnable = object : Runnable {
            override fun run() {

                for(image in images){
                    image.visibility = View.INVISIBLE
                }

                val random = Random
                val randomIndex = random.nextInt(12)
                images[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(this,500);
            }

        }
        handler.post(runnable);
    }
}