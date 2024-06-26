package com.kuro.movie.presenter.detail.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kuro.movie.databinding.YoutubePlayerViewBinding
import com.kuro.movie.domain.model.VideoResult
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback

class VideosAdapter(
    private val lifecycle: Lifecycle
) : ListAdapter<VideoResult, VideosAdapter.VideoResultViewHolder>(
    videoItemCallback
) {
    class VideoResultViewHolder(
        private val binding: YoutubePlayerViewBinding
    ) : ViewHolder(binding.root) {
        fun bind(
            videoResult: VideoResult,
            lifecycle: Lifecycle
        ) {
            binding.txtVideoName.text = videoResult.name
            binding.txtVideoType.text = videoResult.type
            if (Build.VERSION.SDK_INT >= 26) {
                binding.txtVideoName.tooltipText = videoResult.name
            }

            lifecycle.addObserver(binding.youtubePlayerView)
            binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(
                        videoId = videoResult.key,
                        startSeconds = 0f
                    )
                }
            })
        }

        fun unBind(lifecycle: Lifecycle) {
            lifecycle.removeObserver(binding.youtubePlayerView)
        }

        companion object {
            fun from(parent: ViewGroup): VideoResultViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = YoutubePlayerViewBinding.inflate(inflater, parent, false)
                return VideoResultViewHolder(binding)
            }
        }
    }

    override fun onViewRecycled(holder: VideoResultViewHolder) {
        super.onViewRecycled(holder)
        holder.unBind(lifecycle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoResultViewHolder {
        return VideoResultViewHolder.from(parent = parent)
    }

    override fun onBindViewHolder(holder: VideoResultViewHolder, position: Int) {
        val videoResult = getItem(position)
        holder.bind(videoResult = videoResult, lifecycle = lifecycle)
    }
}

val videoItemCallback = object : DiffUtil.ItemCallback<VideoResult>() {

    override fun areItemsTheSame(oldItem: VideoResult, newItem: VideoResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VideoResult, newItem: VideoResult): Boolean {
        return oldItem == newItem
    }
}