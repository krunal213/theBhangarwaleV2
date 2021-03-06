package com.app.thebhangarwale.home.feed.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.thebhangarwale.R
import com.app.thebhangarwale.custom.adapter.BhangarwaleOnAttachStateChangeAdapter
import com.app.thebhangarwale.getHeightForFeed
import com.app.thebhangarwale.home.feed.view.holder.PagerViewHolder
import com.app.thebhangarwale.home.feed.view.holder.SingleImageViewHolder
import com.app.thebhangarwale.home.feed.view.holder.VideoViewHolder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.hrskrs.instadotlib.InstaDotView

class FeedAdapter(val lifeCycleOwner: LifecycleOwner, val onClickListener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType % 3) {
            0 -> {
                return VideoViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.adapter_video_feed,
                            parent,
                            false
                        ),
                    lifeCycleOwner
                )
            }
            1 -> {
                return SingleImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.adapter_single_image_feed,
                            parent,
                            false
                        )
                )
            }
            else -> {
                return PagerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.adapter_pager_feed,
                            parent,
                            false
                        )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> {
                holder.itemView.findViewById<AppCompatImageView>(R.id.menu)
                    .setOnClickListener(onClickListener)
                if (holder.itemView.findViewById<PlayerView>(R.id.video_player).player == null) {
                    holder.itemView.findViewById<AppCompatCheckBox>(R.id.imageViewVolume).apply {
                        val shapeAppearanceModel = ShapeAppearanceModel()
                            .toBuilder()
                            .setAllCorners(CornerFamily.ROUNDED, 50.toFloat())
                            .build()
                        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
                        shapeDrawable.fillColor =
                            ContextCompat.getColorStateList(
                                holder.itemView.context,
                                R.color.color_feed_checkbox
                            )
                        ViewCompat.setBackground(this, shapeDrawable)
                    }
                    SimpleExoPlayer.Builder(holder.itemView.context)
                        .build()
                        .apply {
                            holder.itemView.findViewById<PlayerView>(R.id.video_player).player =
                                this
                            holder.itemView.findViewById<PlayerView>(R.id.video_player)
                                .apply {
                                    layoutParams.height = context.getHeightForFeed(576, 720)
                                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                                }
                            setMediaItem(MediaItem.fromUri(Uri.parse("https://video.fdel28-1.fna.fbcdn.net/v/t42.1790-2/242043882_376290060657329_2713760109483680559_n.mp4?_nc_cat=104&ccb=1-5&_nc_sid=985c63&efg=eyJybHIiOjM5OSwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoic3ZlX3NkIn0%3D&_nc_ohc=9hVC7htQ_7sAX-6q6vk&rl=399&vabr=222&_nc_ht=video.fdel28-1.fna&edm=AJdBtusEAAAA&oh=18333b36ef2836f46e257d3d3b0a1197&oe=6151FA64")))
                            playWhenReady = true
                            repeatMode = Player.REPEAT_MODE_ONE
                            audioComponent?.volume = 0f
                            prepare()
                            holder.lifeCycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                                @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                                fun onStop() {
                                    holder.itemView
                                        .findViewById<AppCompatCheckBox>(R.id.imageViewVolume)
                                        .isChecked = false
                                    audioComponent?.volume = 0f
                                }
                            })
                            holder.itemView
                                .findViewById<PlayerView>(R.id.video_player)
                                .addOnAttachStateChangeListener(object :
                                    BhangarwaleOnAttachStateChangeAdapter() {
                                    override fun onViewDetachedFromWindow(v: View?) {
                                        holder.itemView
                                            .findViewById<AppCompatCheckBox>(R.id.imageViewVolume)
                                            .isChecked = false
                                        audioComponent?.volume = 0f
                                    }
                                })
                            holder.itemView
                                .findViewById<AppCompatCheckBox>(R.id.imageViewVolume)
                                .setOnCheckedChangeListener { buttonView, isChecked ->
                                    if (isChecked) {
                                        audioComponent?.volume = 1f
                                    } else {
                                        audioComponent?.volume = 0f
                                    }
                                }
                        }
                }

            }
            is PagerViewHolder -> {
                holder.itemView.findViewById<AppCompatImageView>(R.id.menu)
                    .setOnClickListener(onClickListener)
                if (holder.itemView.findViewById<ViewPager2>(R.id.viewpager).adapter == null) {
                    holder.itemView.findViewById<ViewPager2>(R.id.viewpager).apply {
                        layoutParams.height = context.getHeightForFeed(720, 604)
                        adapter = MultipleImagePagerAdapter()
                        (getChildAt(0) as RecyclerView).overScrollMode =
                            RecyclerView.OVER_SCROLL_NEVER
                        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                            override fun onPageSelected(position: Int) {
                                holder.itemView.findViewById<InstaDotView>(R.id.indicator)
                                    .onPageChange(position)
                            }
                        })
                    }
                }
                holder.itemView.findViewById<InstaDotView>(R.id.indicator).apply {
                    var multipleImagePagerAdapter =
                        holder.itemView.findViewById<ViewPager2>(R.id.viewpager).adapter as MultipleImagePagerAdapter
                    noOfPages = multipleImagePagerAdapter.itemCount
                }
            }
            is SingleImageViewHolder -> {
                holder.itemView.findViewById<AppCompatImageView>(R.id.menu)
                    .setOnClickListener(onClickListener)
                holder.itemView.findViewById<AppCompatImageView>(R.id.imageview).apply {
                    layoutParams.height = context.getHeightForFeed(720, 604)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}