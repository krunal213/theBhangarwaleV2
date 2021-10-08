package com.app.thebhangarwale.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.*
import com.app.thebhangarwale.add_item.view.AddItemActivity
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayout
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayoutHeader
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.dkzwm.widget.srl.indicator.IIndicator

class CreateRequestFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_request, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = RequestAdapter(this@CreateRequestFragment)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            addItemDecoration(DividerItemDecoration(requireContext()))
        }
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.imageViewContinue).setOnClickListener(this)
        view.findViewById<ConstraintLayout>(R.id.rounded_corner).setOnClickListener(this)
        val header = BhangarwaleSmoothRefreshLayoutHeader<IIndicator>(this.requireActivity())
        view.findViewById<BhangarwaleSmoothRefreshLayout>(R.id.mRefreshLayout).setHeaderView(header)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.item->{
                val intent = Intent(activity, UpdateItemActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    v,
                    ViewCompat.getTransitionName(v)
                )
                intent.putExtra("transition_name",ViewCompat.getTransitionName(v))
                startActivity(intent, options.toBundle())
            }
            R.id.floatingActionButton->{
                startActivity(Intent(activity, AddItemActivity::class.java))
            }
            R.id.imageViewContinue->{
                startActivity(Intent(activity,ChooseAddressActivity::class.java))
            }
            R.id.rounded_corner->{
                val intent = Intent(activity, SearchRequestInsideCartActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    v,
                    ViewCompat.getTransitionName(v)
                )
                startActivity(intent, options.toBundle())
            }
        }
    }
}

class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class RequestAdapter(val onClickListener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            5->{
                InfoViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.adapter_info,parent,false)
                )
            }
            else->{
                RequestViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.adapter_request,parent,false)
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RequestViewHolder->{
                ViewCompat.setTransitionName(holder.itemView,position.toString())
                holder.itemView.setOnClickListener(onClickListener)
                ViewCompat.setTransitionName(holder.itemView,position.toString())
            }
            is InfoViewHolder->{
            }
        }

    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

