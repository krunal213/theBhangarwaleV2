package com.app.thebhangarwale.home.feed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.appbar.MaterialToolbar
import com.hrskrs.instadotlib.InstaDotView
import androidx.core.view.ViewCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.app.thebhangarwale.*
import com.app.thebhangarwale.custom.adapter.BhangarwaleOnAttachStateChangeAdapter
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayout
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayoutHeader
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.home.feed.viewmodel.FeedViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import me.dkzwm.widget.srl.indicator.IIndicator
import javax.inject.Inject

class FeedFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var feedViewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(activity?.application?.let {
                BhangarwaleApplicationModule(
                    it
                )
            })
            .build()
            .injectFeedFragment(this)
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialToolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu_feed)
            setOnMenuItemClickListener(this@FeedFragment)
        }
        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = FeedAdapter(this@FeedFragment.viewLifecycleOwner, this@FeedFragment)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext()))
        }
        val header = BhangarwaleSmoothRefreshLayoutHeader<IIndicator>(this.requireActivity())
        view.findViewById<BhangarwaleSmoothRefreshLayout>(R.id.mRefreshLayout).setHeaderView(header)
        feedViewModel.getFeeds().observe(viewLifecycleOwner,Observer<BhangarwaleResult<*>>{
            when(it){
                is BhangarwaleResult.Success->{
                    view.apply {
                        findViewById<ShimmerFrameLayout>(R.id.shimmer_feed).apply {
                            stopShimmer()
                            visibility = View.GONE
                        }
                        findViewById<BhangarwaleSmoothRefreshLayout>(R.id.mRefreshLayout).apply {
                            visibility = View.VISIBLE
                        }
                    }
                }
                is BhangarwaleResult.Loading->{
                    view.findViewById<ShimmerFrameLayout>(R.id.shimmer_feed).startShimmer()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        val items = arrayOf("Redirect to Facebook", "Share to...")
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setItems(items) { dialog, which ->
                    startActivity(Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "https://www.facebook.com/113157777199737/posts/306275154554664/"
                        )
                        type = "text/plain"
                    })


                    /*val share = Intent.createChooser(Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "https://www.facebook.com/113157777199737/posts/306275154554664/")

                        // (Optional) Here we're setting the title of the content
                        putExtra(Intent.EXTRA_TITLE, "Introducing content previews")

                        // (Optional) Here we're passing a content URI to an image to be displayed
                        data = Uri.parse("https://www.facebook.com/113157777199737/posts/306275154554664/")
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }, null)
                    startActivity(share)*/


                    //startFacebookActivity("https://www.facebook.com/113157777199737/posts/306275154554664/")
                    /* startActivity(Intent.createChooser(Intent().apply {
                         action = Intent.ACTION_SEND
                         data = Uri.parse("https://www.facebook.com/TheBhangarwale/posts")
                     }, null))*/
                }
                .show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.navigation_share->{
                //https://play.google.com/store/apps/details?id=com.thekabadiwala.userapp
                startActivity(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        feedViewModel.shareOurAppUrl
                    )
                    type = "text/html"
                })
                return true
            }
        }
        return false
    }

}

class SingleImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class VideoViewHolder(itemView: View, val lifeCycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(itemView)

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class FeedAdapter(val lifeCycleOwner: LifecycleOwner, val onClickListener: View.OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                            repeatMode = REPEAT_MODE_ONE
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

class MultipleImagePagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MultipleImagePagerAdapter : RecyclerView.Adapter<MultipleImagePagerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultipleImagePagerViewHolder {
        return MultipleImagePagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_multiple_image_feed,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: MultipleImagePagerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

}
