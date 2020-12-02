package com.roh.timer

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import java.util.*


class MainActivity : AppCompatActivity() {

    private var isPlaying = false
    private var isRunning: Boolean = false

    private lateinit var countdownTimer: CountDownTimer
    var timeMilliSeconds = 0L


    private lateinit var mTimer: TextView
    private lateinit var mCurrentTime: TextView
    private lateinit var mTxtPlayPause: TextView
    private lateinit var mCardView: LinearLayout
    private lateinit var mCardBottom: LinearLayout
    private lateinit var mUp: LottieAnimationView
    private lateinit var mDown: ImageView







    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mPlayPause = findViewById<LottieAnimationView>(R.id.play_pause)
        mTimer = findViewById(R.id.timer_txt)
        mCurrentTime = findViewById(R.id.current_time)
        mTxtPlayPause = findViewById(R.id.txt_)
        mCardView = findViewById(R.id.linear_layout)
        mCardBottom = findViewById(R.id.lin_)
        mUp = findViewById(R.id.up)
        mUp.speed = 0.4F
        mUp.setMaxFrame(80)
        mDown = findViewById(R.id.down)

        mTimer.text = getString(R.string.time)
        mTxtPlayPause.text = getString(R.string.play)






        mPlayPause.setOnClickListener {

            if (isPlaying) {
                pauseTimer()
                mTxtPlayPause.text = getString(R.string.restart)
                mPlayPause.pauseAnimation()
                mPlayPause.progress = 0F

                isPlaying = false

            } else {
                if (!isRunning) {

                    timeMilliSeconds = 3 * 60000L
                    startTimer(timeMilliSeconds)

                    val calendar = Calendar.getInstance()
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)

                    var plusMin = minute + 3
                    var plusHr = hour


                    if (plusMin > 59) {
                        plusHr += 1
                        plusMin -= 59
                    }

                    mCurrentTime.text = "$hour:$minute - $plusHr:$plusMin"


                }

                mPlayPause.setMaxFrame(95)
                mPlayPause.playAnimation()
                mPlayPause.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        mPlayPause.progress = 0.5F
                        mPlayPause.speed = 1.1F
                        mPlayPause.resumeAnimation()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                })

                isPlaying = true
            }


        }

        mCardBottom.setOnClickListener {
            slideUp(mCardView)
        }

        mDown.setOnClickListener {
            slideDown(mCardView)
        }


        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        var plusMin = minute+3

        if (plusMin > 59){
            hour += 1
            plusMin -= 59
        }

        mCurrentTime.text = "$hour:$minute - $hour:$plusMin"



    }

    private fun pauseTimer() {
        countdownTimer.cancel()
        isRunning = false
    }



    private fun startTimer(time_in_seconds: Long) {
        countdownTimer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                val mPlayPause = findViewById<LottieAnimationView>(R.id.play_pause)
                mPlayPause.pauseAnimation()
                mPlayPause.progress = 0F
                isPlaying = false
                mTimer.text = getString(R.string.time)


            }

            override fun onTick(p0: Long) {
                timeMilliSeconds = p0
                updateTextUI()
            }
        }
        countdownTimer.start()

        isRunning = true


    }

    @SuppressLint("SetTextI18n")
    private fun updateTextUI() {
        mTxtPlayPause.text = getString(R.string.pause)

        val minute = (timeMilliSeconds / 1000) / 60
        val seconds = (timeMilliSeconds / 1000) % 60

        if (minute < 10){

            if (seconds < 10){
                mTimer.text = "0$minute:0$seconds"


            }else{
                mTimer.text = "0$minute:$seconds"

            }

        }else{
            mTimer.text = "$minute:$seconds"

        }

    }


    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
                0F,  // fromXDelta
                0F,  // toXDelta
                view.height.toFloat(),  // fromYDelta
                0F) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)

        mCardView.visibility = View.VISIBLE

        Handler().postDelayed({
            mCardBottom.visibility = View.INVISIBLE

        }, 200)



    }

    private fun slideDown(view: View) {
        val animate = TranslateAnimation(
                0F,  // fromXDelta
                0F,  // toXDelta
                0F,  // fromYDelta
                view.height.toFloat()) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)

        mCardView.visibility = View.INVISIBLE

        Handler().postDelayed({
            mCardBottom.visibility = View.VISIBLE


        },200)


    }

}