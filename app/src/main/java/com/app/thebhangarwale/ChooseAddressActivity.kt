package com.app.thebhangarwale

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.view.*
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.app.thebhangarwale.address.entity.AddressV2
import com.app.thebhangarwale.address.view.*
import com.app.thebhangarwale.address.viewmodel.AddressViewModel
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.home.create_request.InfoViewHolder
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialFade
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChooseAddressActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var addressViewModel : AddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(BhangarwaleApplicationModule(application))
            .build()
            .injectChooseAddressActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_address)

        val toolbarWithContextMenu = findViewById<MaterialToolbar>(R.id.toolbarWithContextMenu)
        toolbarWithContextMenu.inflateMenu(R.menu.menu_delete_item)
        toolbarWithContextMenu.setNavigationOnClickListener {
            toolbarWithContextMenu.visibility = View.GONE
            with(this@ChooseAddressActivity){
                findViewById<RecyclerView>(com.app.thebhangarwale.R.id.recyclerviewAddress).apply{
                    with(adapter as? ChooseAddressAdapter){
                        this?.unSelectAll()
                    }
                }
            }
        }
        toolbarWithContextMenu.setOnMenuItemClickListener(this)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.title_select_address)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        addressViewModel.getAddress().observe(this,{
            when(it){
                is BhangarwaleResult.Success->{
                    findViewById<RecyclerView>(R.id.recyclerviewAddress).apply{
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = ChooseAddressAdapter(it.data, this@ChooseAddressActivity)
                        addItemDecoration(DividerItemDecoration(context))
                    }.also {
                            recyclerview ->
                        with(recyclerview.adapter as ChooseAddressAdapter) {
                            selectionTracker = SelectionTracker.Builder<AddressV2>(
                                "address",
                                recyclerview,
                                ChooseAddressItemKeyProvider(),
                                object : ItemDetailsLookup<AddressV2>() {
                                    override fun getItemDetails(e: MotionEvent): ItemDetails<AddressV2>? =
                                        recyclerview.findChildViewUnder(e.x, e.y)?.let { view ->
                                            with(recyclerview.getChildViewHolder(view) as? ChooseAddressViewHolder) {
                                                this?.getAddressItemDetails()
                                            }
                                        }
                                },
                                StorageStrategy.createParcelableStorage(AddressV2::class.java)
                            ).build()
                            selectionTracker?.addObserver(object :
                                SelectionTracker.SelectionObserver<AddressV2>() {
                                override fun onItemStateChanged(key: AddressV2, selected: Boolean) {
                                    if (selectionTracker?.hasSelection() == true) {
                                        with(this@ChooseAddressActivity){
                                            menuVisibility(android.view.View.VISIBLE)
                                            selectionTracker?.selection?.size()?.let { count ->
                                                toolbarCountDisplay(
                                                    count
                                                )
                                            }
                                        }
                                    } else {
                                        menuVisibility(View.GONE)
                                    }
                                }
                            })
                        }
                    }
                }
            }
        })

        findViewById<MaterialButton>(R.id.buttonSaveAddress).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_address,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_address->{
                val intentAddAddress = Intent(this, CreateAddressWithGoogleMapActivity::class.java)
                startActivity(intentAddAddress)
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageViewMenu -> {
                val popup = PopupMenu(this, v)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.pop_up_menu_my_address, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.action_google_map -> {
                            val intentAddAddress =
                                Intent(this, EditWithGoogleMapAddressActivity::class.java)
                            startActivity(intentAddAddress)

                            /*val address: Address? = v.getTag(R.string.tag_address) as Address?
                            val intentAddress =
                                Intent(this@MyAddressActivity, AddAddressActivity::class.java)
                            intentAddress.putExtra(EDIT_AVAILABLE_ADDRESS, address)
                            this@MyAddressActivity.startActivity(intentAddress)*/
                        }

                        R.id.action_edit -> {
                            startActivity(Intent(this, EditAddressActivity::class.java))
                            /*val address: Address? = v.getTag(R.string.tag_address) as Address?
                            val intentUpdateAddress =
                                Intent(this@MyAddressActivity, UpdateAddressActivity::class.java)
                            intentUpdateAddress.putExtra(EDIT_AVAILABLE_ADDRESS, address)
                            this@MyAddressActivity.startActivity(intentUpdateAddress)*/
                        }
                    }
                    true
                }
                popup.show()
            }
            R.id.buttonSaveAddress->{
                val intentAddAddress = Intent(this,ConfirmationActivity::class.java)
                startActivity(intentAddAddress)
            }
        }

    }

    class ChooseAddressAdapter(
        val myaddress: ArrayList<AddressV2>,
        val onClickListener: View.OnClickListener?,
        private val FOOTER: Int = -1) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var selectionTracker: SelectionTracker<AddressV2>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                FOOTER -> {
                    InfoViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.adapter_info, parent, false)
                    )
                }else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_choose_address,parent,false)
                    ChooseAddressViewHolder(view)
                }
            }
        }

        override fun getItemCount(): Int {
            return (myaddress.size + 1)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder){
                is ChooseAddressViewHolder->{
                    selectionTracker?.apply {
                        val address = myaddress[position]
                        holder.itemView.isActivated = isSelected(address) ?: false
                        holder.itemView.findViewById<TextView>(R.id.textViewAddress).text = address.getAddressLine(0)
                        holder.itemView.setTag(R.string.tag_address,address)
                        holder.itemView.setTag(R.string.tag_position,position)
                        holder.itemView.findViewById<ImageView>(R.id.imageViewMenu).setOnClickListener(onClickListener)
                    }
                }
            }
            //holder.itemView.setOnLongClickListener(longClickListener)
            //holder.itemView.imageViewMenu.setOnClickListener(onClickListener)
            //holder.itemView.imageViewMenu.setTag(R.string.tag_address,address)
            /*TooltipCompat.setTooltipText(holder.itemView.textViewAddress,
                myaddress.get(position))*/
        }

        override fun getItemViewType(position: Int): Int {
            return if (myaddress.size > 0) {
                if (position == (myaddress.size)) {
                    FOOTER
                } else {
                    position
                }
            } else {
                position
            }
        }

        fun selectAll(){
            selectionTracker?.setItemsSelected(myaddress,true)
        }

        fun unSelectAll(){
            selectionTracker?.clearSelection()
        }

        inner class ChooseAddressItemKeyProvider() : ItemKeyProvider<AddressV2>(ItemKeyProvider.SCOPE_CACHED) {
            override fun getKey(position: Int): AddressV2 = myaddress[position]
            override fun getPosition(request: AddressV2): Int = myaddress.indexOf(request)
        }
    }

    abstract class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun getAddressItemDetails(): ItemDetailsLookup.ItemDetails<AddressV2>?
    }

    class ChooseAddressViewHolder(itemView : View) : SelectionViewHolder(itemView) {
        override fun getAddressItemDetails(): ItemDetailsLookup.ItemDetails<AddressV2>? {
            return object : ItemDetailsLookup.ItemDetails<AddressV2>() {
                override fun getPosition(): Int = itemView.getTag(R.string.tag_position) as Int
                override fun getSelectionKey(): AddressV2? =
                    itemView.getTag(R.string.tag_address) as? AddressV2
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete -> {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Delete items?")
                    .setNegativeButton("CANCEL") { dialog, which ->
                    }
                    .setPositiveButton("OK") { dialog, which ->
                    }
                    .show()
            }
            R.id.navigation_mark_All -> {
                findViewById<RecyclerView>(R.id.recyclerviewAddress).apply{
                    with(adapter as? AddressActivity.AddressAdapter){
                        this?.selectAll()
                    }
                }

                /* with(fragmentCreateRequestBinding
                     ?.recyclerView
                     ?.adapter as RequestAdapter
                 ){
                     selectAll()
                 }*/
            }

            R.id.navigation_unmark_All -> {
                findViewById<RecyclerView>(R.id.recyclerviewAddress).apply{
                    with(adapter as? AddressActivity.AddressAdapter){
                        this?.unSelectAll()
                    }
                }
                /* with(fragmentCreateRequestBinding
                     ?.recyclerView
                     ?.adapter as RequestAdapter
                 ){
                     unSelectAll()
                 }*/
            }
        }
        return true
    }

    override fun onBackPressed() {
        findViewById<MaterialToolbar>(R.id.toolbarWithContextMenu)
            .apply {
                if (isVisible) {
                    with(this@ChooseAddressActivity){
                        findViewById<RecyclerView>(R.id.recyclerviewAddress).apply{
                            with(adapter as? ChooseAddressAdapter){
                                this?.unSelectAll()
                            }
                        }
                    }
                    visibility = View.GONE

                } else {
                    super.onBackPressed()
                }
            }
    }

}

fun ChooseAddressActivity.menuVisibility(visibilityMode: Int) {
    findViewById<ConstraintLayout>(R.id.root).apply {
        TransitionManager.beginDelayedTransition(
            this, MaterialFade()
        )
    }
    findViewById<Toolbar>(R.id.toolbarWithContextMenu).apply {
        visibility = visibilityMode
    }
}

fun ChooseAddressActivity.toolbarCountDisplay(count : Int){
    findViewById<Toolbar>(R.id.toolbarWithContextMenu).apply {
        title = count.toString()
    }
}