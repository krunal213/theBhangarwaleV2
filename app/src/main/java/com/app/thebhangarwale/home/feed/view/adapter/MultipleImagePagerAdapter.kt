package com.app.thebhangarwale.home.feed.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.R
import com.app.thebhangarwale.home.feed.view.holder.MultipleImagePagerViewHolder

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
