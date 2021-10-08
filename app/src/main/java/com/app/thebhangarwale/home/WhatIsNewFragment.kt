package com.app.thebhangarwale.home

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.R
import com.app.thebhangarwale.RequestDetailActivity
import android.view.ViewAnimationUtils
import com.app.thebhangarwale.circularReval

class WhatIsNewFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_what_is_new, container, false)
        view.circularReval()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv : RecyclerView = view?.findViewById<RecyclerView>(R.id.rv)
        rv?.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            adapter = WhatsNewAdapter(this@WhatIsNewFragment)
            addItemDecoration(WhatsNewItemDecoration())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.item->{
                startActivity(Intent(activity, RequestDetailActivity::class.java))
            }
        }
    }

}

class WhatsNewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class WhatsNewAdapter(val onClickListener : View.OnClickListener) : RecyclerView.Adapter<WhatsNewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatsNewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_whats_new,parent,false)
        return WhatsNewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 19
    }

    override fun onBindViewHolder(holder: WhatsNewViewHolder, position: Int) {
        holder.itemView.setOnClickListener(onClickListener)
        ViewCompat.setTransitionName(holder.itemView,position.toString())

    }
}

class WhatsNewItemDecoration : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        super.getItemOffsets(outRect, itemPosition, parent)
        if (itemPosition==0){
            outRect.top = parent.resources.getDimensionPixelOffset(R.dimen.margin_eighteen_dp)
        }
        outRect.left = parent.resources.getDimensionPixelOffset(R.dimen.margin_eighteen_dp)
        outRect.bottom = parent.resources.getDimensionPixelOffset(R.dimen.margin_eighteen_dp)
        outRect.right = parent.resources.getDimensionPixelOffset(R.dimen.margin_eighteen_dp)
    }

}