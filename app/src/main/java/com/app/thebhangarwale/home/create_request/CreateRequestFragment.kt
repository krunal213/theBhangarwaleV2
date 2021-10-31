package com.app.thebhangarwale.home.create_request

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.observe
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.app.thebhangarwale.*
import com.app.thebhangarwale.add_item.view.AddItemActivity
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayout
import com.app.thebhangarwale.custom.view.BhangarwaleSmoothRefreshLayoutHeader
import com.app.thebhangarwale.databinding.FragmentCreateRequestBinding
import com.app.thebhangarwale.home.create_request.entity.Request
import com.app.thebhangarwale.home.create_request.viewmodel.RequestViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialFade
import me.dkzwm.widget.srl.indicator.IIndicator

class CreateRequestFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private val fragmentCreateRequestBinding by lazy {
        activity?.layoutInflater?.let { FragmentCreateRequestBinding.inflate(it) }
    }
    private val requestViewModel by lazy {
        activity?.application?.let { RequestViewModel(it) }
    }
    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                with(fragmentCreateRequestBinding?.
                recyclerView?.
                adapter as RequestAdapter){
                    selectionTracker?.clearSelection()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return fragmentCreateRequestBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCreateRequestBinding?.apply {
            recyclerView.apply {
                adapter = RequestAdapter(this@CreateRequestFragment)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(requireContext()))
            }.also { recyclerview ->
                with(recyclerview.adapter as RequestAdapter) {
                    selectionTracker = SelectionTracker.Builder<Request>(
                        "item",
                        recyclerview,
                        RequestItemKeyProvider(),
                        object : ItemDetailsLookup<Request>() {
                            override fun getItemDetails(e: MotionEvent): ItemDetails<Request>? =
                                recyclerview.findChildViewUnder(e.x, e.y)?.let { view ->
                                    with(recyclerview.getChildViewHolder(view) as? SelectionViewHolder) {
                                        this?.getRequestItemDetails()
                                    }
                                }
                        },
                        StorageStrategy.createParcelableStorage(Request::class.java)
                    ).build()
                    selectionTracker?.addObserver(object :
                        SelectionTracker.SelectionObserver<Request>() {
                        override fun onItemStateChanged(key: Request, selected: Boolean) {
                            if (selectionTracker?.hasSelection() == true) {
                                onBackPressedCallback.isEnabled = true
                                fragmentCreateRequestBinding?.menuVisibility(View.VISIBLE)
                                selectionTracker?.selection?.size()?.let { count ->
                                    fragmentCreateRequestBinding?.toolbarCountDisplay(
                                        count
                                    )
                                }
                            } else {
                                onBackPressedCallback.isEnabled = false
                                fragmentCreateRequestBinding?.menuVisibility(View.GONE)
                            }
                        }
                    })
                }
            }
            floatingActionButton.setOnClickListener(this@CreateRequestFragment)
            imageViewContinue.setOnClickListener(this@CreateRequestFragment)
            search.roundedCorner.setOnClickListener(this@CreateRequestFragment)
            val header =
                BhangarwaleSmoothRefreshLayoutHeader<IIndicator>(this@CreateRequestFragment.requireActivity())
            mRefreshLayout.setHeaderView(header)
            toolbar.apply {
                inflateMenu(R.menu.menu_delete_item)
                setNavigationOnClickListener {
                    with(fragmentCreateRequestBinding?.recyclerView?.adapter as RequestAdapter) {
                        selectionTracker?.clearSelection()
                    }
                    fragmentCreateRequestBinding?.menuVisibility(View.GONE)
                }
                setOnMenuItemClickListener(this@CreateRequestFragment)
            }
        }
        requestViewModel?.getRequests()?.observe(viewLifecycleOwner, {
            when (it) {
                is BhangarwaleResult.Success -> {
                    val requestAdapter =
                        fragmentCreateRequestBinding?.recyclerView?.adapter as RequestAdapter
                    requestAdapter.notifyDataSetChanged(it.data)
                }
            }
        })
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,onBackPressedCallback)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.item -> {
                val intent = Intent(activity, UpdateItemActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    v,
                    ViewCompat.getTransitionName(v)
                )
                intent.putExtra("transition_name", ViewCompat.getTransitionName(v))
                startActivity(intent, options.toBundle())
                //fragmentCreateRequestBinding?.menuVisibility(View.VISIBLE)
            }
            R.id.floatingActionButton -> {
                startActivity(Intent(activity, AddItemActivity::class.java))
            }
            R.id.imageViewContinue -> {
                startActivity(Intent(activity, ChooseAddressActivity::class.java))
            }
            R.id.rounded_corner -> {
                val intent = Intent(activity, SearchRequestInsideCartActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    v,
                    ViewCompat.getTransitionName(v)
                )
                intent.putExtra("transition_name", ViewCompat.getTransitionName(v))
                startActivity(intent, options.toBundle())
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_delete->{
                activity?.let {
                    MaterialAlertDialogBuilder(it)
                        .setMessage("Delete items?")
                        .setNegativeButton("CANCEL") { dialog, which ->
                        }
                        .setPositiveButton("OK") { dialog, which ->
                        }
                        .show()
                }
            }
            R.id.navigation_mark_All->{
                with(fragmentCreateRequestBinding
                    ?.recyclerView
                    ?.adapter as RequestAdapter){
                    selectAll()
                }
            }

            R.id.navigation_unmark_All->{
                with(fragmentCreateRequestBinding
                    ?.recyclerView
                    ?.adapter as RequestAdapter){
                    unSelectAll()
                }
            }
        }
        return true
    }

}

fun FragmentCreateRequestBinding.menuVisibility(visibilityMode: Int) {
    root?.let {
        TransitionManager.beginDelayedTransition(
            it, MaterialFade()
        )
    }
    toolbar?.apply {
        visibility = visibilityMode
    }
}

fun FragmentCreateRequestBinding.toolbarCountDisplay(count : Int){
    toolbar?.apply {
        title = count.toString()
    }
}

class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

abstract class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun getRequestItemDetails(): ItemDetailsLookup.ItemDetails<Request>?
}

class RequestViewHolder(itemView: View) : SelectionViewHolder(itemView) {
    override fun getRequestItemDetails(): ItemDetailsLookup.ItemDetails<Request>? {
        return object : ItemDetailsLookup.ItemDetails<Request>() {
            override fun getPosition(): Int = itemView.getTag(R.string.tag_position) as Int
            override fun getSelectionKey(): Request? =
                itemView.getTag(R.string.tag_item) as? Request
        }
    }
}

class RequestAdapter(
    val onClickListener: View.OnClickListener,
    private val list: ArrayList<Request> = ArrayList(),
    private val FOOTER: Int = -1
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectionTracker: SelectionTracker<Request>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FOOTER -> {
                InfoViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.adapter_info, parent, false)
                )
            }
            else -> {
                RequestViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.adapter_request, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RequestViewHolder -> {
                selectionTracker?.apply {
                    holder.itemView.setTag(R.string.tag_item, list[position])
                    holder.itemView.setTag(R.string.tag_position, position)
                    holder.itemView.isActivated = isSelected(list[position]) ?: false
                    if (holder.itemView.isActivated) {
                        holder.itemView.findViewById<AppCompatImageView>(R.id.image_activated)
                            .apply {
                                holder.itemView
                                val backgroundShapeModel: ShapeAppearanceModel =
                                    ShapeAppearanceModel.builder()
                                        .setAllCorners(CornerFamily.ROUNDED, 60.toFloat())
                                        .build()
                                background = MaterialShapeDrawable(backgroundShapeModel).apply {
                                    fillColor = ColorStateList.valueOf(Color.WHITE)
                                }
                                circularRevalV2()
                            }
                    } else {
                        holder.itemView.findViewById<AppCompatImageView>(R.id.image_activated)
                            .apply {
                                hideReval()
                            }
                    }
                }
                ViewCompat.setTransitionName(holder.itemView, position.toString())
                holder.itemView.setOnClickListener(onClickListener)
                ViewCompat.setTransitionName(holder.itemView, position.toString())
            }
            is InfoViewHolder -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return (list.size + 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.size > 0) {
            if (position == (list.size)) {
                FOOTER
            } else {
                position
            }
        } else {
            position
        }
    }

    fun notifyDataSetChanged(data: ArrayList<Request>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun selectAll(){
        selectionTracker?.setItemsSelected(list,true)
    }

    fun unSelectAll(){
        selectionTracker?.clearSelection()
    }

    inner class RequestItemKeyProvider() : ItemKeyProvider<Request>(ItemKeyProvider.SCOPE_CACHED) {
        override fun getKey(position: Int): Request = list[position]
        override fun getPosition(request: Request): Int = list.indexOf(request)
    }
}

