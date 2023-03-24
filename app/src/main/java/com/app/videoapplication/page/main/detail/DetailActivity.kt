package com.app.videoapplication.page.main.detail

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.page.BaseActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : BaseActivity(), Player.Listener {
    lateinit var detailViewModel: DetailViewModel
    var isPlayWhenReady=false
    var isPlayerEnded=false
    var mResumeWindow =0
    var mResumePosition : Long=0
    private val mPlaybackPosition = 0L
    private val mCurrentWindow = 0
    private val mExoPlayerFullscreen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //fullScreen()
        setContentView(com.app.videoapplication.R.layout.activity_detail)
        detailViewModel= ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.exoPlayer.observe(this){
            playerView.player=it
            it.addListener(this)
        }
    }

    override fun onPause() {
        super.onPause()
        playerView.player?.let {
            mResumeWindow = it.currentWindowIndex
            mResumePosition = Math.max(0, it.contentPosition)
            it.release()
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        playerView.keepScreenOn = !(playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED || !playWhenReady)
        isPlayerEnded=playbackState == Player.STATE_ENDED
    }

    override fun onDestroy() {
        super.onDestroy()
        playerView.player?.release()
    }

    val STATE_RESUME_WINDOW="state_resume_window"
    val STATE_RESUME_POSITION="state_resume_position"
    var STATE_PLAYER_FULLSCREEN="state_player_fullscreen"

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow)
        outState.putLong(STATE_RESUME_POSITION, mResumePosition)
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        super.onSaveInstanceState(outState)

    }

    private fun resumeExoPlayer() {
        val haveResumePosition = mResumeWindow != C.INDEX_UNSET
        if (haveResumePosition) {
            playerView.player?.seekTo(mResumeWindow, mResumePosition)
        }
    }

    override fun onResume() {
        super.onResume()
        resumeExoPlayer()
        fullScreen()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        println("//on player : onPlaybackStateChanged $playbackState")
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        super.onPlaybackParametersChanged(playbackParameters)
        println("//on player : onPlaybackParametersChanged ${playbackParameters.pitch}")
    }


    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        isPlayWhenReady=playWhenReady
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        if(isPlayWhenReady && !isPlayerEnded){
            if (isPlaying) progressBar.visibility=View.GONE
            else
                progressBar.visibility=View.VISIBLE
        }else{
            progressBar.visibility=View.GONE
        }

    }




    fun fullScreen() {
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.setOnSystemUiVisibilityChangeListener {
            if((it and (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION))==0){
                Handler(mainLooper).postDelayed({
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                },5000)
            }
        }
    }

}
