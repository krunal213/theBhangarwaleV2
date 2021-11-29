package com.app.thebhangarwale.home.feed.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.*
import com.app.thebhangarwale.*
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayoutHeader
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.databinding.FragmentFeedBinding
import com.app.thebhangarwale.home.feed.view.adapter.FeedAdapter
import com.app.thebhangarwale.home.feed.viewmodel.FeedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.dkzwm.widget.srl.indicator.IIndicator
import javax.inject.Inject

class FeedFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var feedViewModel: FeedViewModel
    val fragmentFeedBinding by lazy {
        FragmentFeedBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return fragmentFeedBinding.root
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
        fragmentFeedBinding.apply {
            toolbar.apply {
                inflateMenu(R.menu.menu_feed)
                setOnMenuItemClickListener(this@FeedFragment)
            }
            recyclerView.apply {
                adapter = FeedAdapter(this@FeedFragment.viewLifecycleOwner, this@FeedFragment)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(requireContext()))
            }
            with(this@FeedFragment.activity) {
                mRefreshLayout.setHeaderView(BhangarwaleSmoothRefreshLayoutHeader<IIndicator>(this))
            }
        }
        feedViewModel.getFeeds().observe(viewLifecycleOwner, {
            when (it) {
                is BhangarwaleResult.Success -> {
                    fragmentFeedBinding.apply {
                        shimmerFeed.root.apply {
                            stopShimmer()
                            visibility = View.GONE
                        }
                        mRefreshLayout.apply {
                            visibility = View.VISIBLE
                        }
                    }
                }
                is BhangarwaleResult.Loading -> {
                    fragmentFeedBinding.apply {
                        shimmerFeed.root.apply {
                            startShimmer()
                        }
                    }
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
                }
                .show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.navigation_share -> {
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





