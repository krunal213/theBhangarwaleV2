package com.app.thebhangarwale

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.thebhangarwale.add_item.entity.Media
import com.app.thebhangarwale.add_item.view.AddItemAdapter
import com.app.thebhangarwale.add_item.viewmodel.MultimediaViewModel
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.activity_result.BhangarwaleTakePicture
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class ProfileActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener {

    private val multimediaViewModel by lazy {
        MultimediaViewModel(application)
    }

    private val imageLauncher = registerForActivityResult(BhangarwaleTakePicture()) { imageResponse ->
        when (imageResponse.result) {
            RESULT_OK -> {
                findViewById<ImageView>(R.id.imageViewProfile).apply {
                    Glide.with(context)
                        .load(imageResponse.uri as Uri)
                        .circleCrop()
                        .into(this)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.title_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        findViewById<ImageView>(R.id.imageViewProfile).apply {
            Glide.with(context)
                .load(R.drawable.ic_ashish)
                .circleCrop()
                .into(this)
        }

        val personProfileDetails: ArrayList<ProfileDetails> = ArrayList<ProfileDetails>()
        personProfileDetails.add(
            ProfileDetails(
                R.id.customer_name,
                resources.getString(R.string.title_name),
                "Ashish Shingade",
                R.drawable.ic_account,
                true
            )
        )
        personProfileDetails.add(
            ProfileDetails(
                R.id.customer_phone,
                resources.getString(R.string.title_phone),
                "+91 7304452855",
                R.drawable.ic_phone,
                false
            )
        )

        findViewById<RecyclerView>(R.id.recyclerViewProfile).apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(ProfileItemDecoration(this@ProfileActivity))
            adapter = ProfileAdapter(personProfileDetails, this@ProfileActivity)
        }

        findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener(this@ProfileActivity)
            addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> circularRevalV2() }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.constraintLayoutProfile -> {
                val profileDetails: ProfileDetails? =
                    view?.getTag(R.string.tag_profileDetails) as ProfileDetails?
                editProfileDetails(profileDetails)

            }

            R.id.constraintLayoutProfileTypeTwo -> {
                val intentAddAddress = Intent(this, PhoneNumberUpdateActivity::class.java)
                startActivity(intentAddAddress)
            }

            R.id.fab->{
                val profileNameEditBottomDialogFragment =
                    ProfileImageDialogFragment.newInstance(
                    )
                profileNameEditBottomDialogFragment.show(
                    supportFragmentManager,
                    ProfileImageDialogFragment.javaClass.simpleName
                )

                //selectPhotoFromCamera()
            }
            R.id.textview_profile_photo->{
                val profileNameEditBottomDialogFragment : ProfileImageDialogFragment =
                    supportFragmentManager
                        .findFragmentByTag(ProfileImageDialogFragment.javaClass.simpleName)
                            as ProfileImageDialogFragment
                profileNameEditBottomDialogFragment.dismiss()
                selectPhotoFromCamera()
            }
        }
    }

    fun editProfileDetails(profileDetails: ProfileDetails?) {
        when (profileDetails?.id) {
            R.id.customer_name -> {
                val profileNameEditBottomDialogFragment =
                    ProfileNameEditBottomDialogFragment.newInstance(
                        profileDetails
                    )
                profileNameEditBottomDialogFragment.show(
                    supportFragmentManager,
                    profileNameEditBottomDialogFragment.javaClass.simpleName
                )
            }
        }
    }

    fun selectPhotoFromCamera() {
        imageLauncher.launch(multimediaViewModel.photoUri)
    }
}

data class ProfileDetails(
    val id: Int,
    val label: String?,
    val value: String?,
    val image: Int,
    val isEditable: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(label)
        parcel.writeString(value)
        parcel.writeInt(image)
        parcel.writeByte(if (isEditable) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileDetails> {
        override fun createFromParcel(parcel: Parcel): ProfileDetails {
            return ProfileDetails(parcel)
        }

        override fun newArray(size: Int): Array<ProfileDetails?> {
            return arrayOfNulls(size)
        }
    }
}

class ProfileItemDecoration(mContext: Context) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable? by lazy {
        ContextCompat.getDrawable(mContext, R.drawable.drawable_horizonatal_divider)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mDivider?.apply {
            val childMainView =
                parent.getChildAt(0).findViewById<View>(R.id.constraintLayoutProfile)
            val linearLayoutText = childMainView.findViewById<View>(R.id.linearLayoutText)
            val dividerLeft = linearLayoutText.left
            val dividerRight = childMainView.right
            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + intrinsicHeight
                setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                draw(c)
            }
        }
    }
}

class ProfileAdapter(
    var personProfileDetails: ArrayList<ProfileDetails>,
    var onClickListener: View.OnClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val isEditable: Boolean = personProfileDetails.get(position).isEditable
        when (isEditable) {
            true -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_profile_type_one, parent, false)
                return ProfileTypeOneViewHolder(view)
            }
            false -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_profile_type_two, parent, false)
                return ProfileTypeTwoViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return personProfileDetails.size
    }

    override fun getItemViewType(position: Int): Int {
        return position;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var profileDetails: ProfileDetails = personProfileDetails.get(position)
        when (holder) {
            is ProfileTypeOneViewHolder -> {
                holder.itemView.findViewById<TextView>(R.id.textViewLabel).text =
                    profileDetails.label
                holder.itemView.findViewById<TextView>(R.id.textViewValue).text =
                    profileDetails.value
                var image = profileDetails.image
                if (image != null) {
                    holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(image)
                }
                holder.itemView.setOnClickListener(onClickListener)
                holder.itemView.setTag(R.string.tag_profileDetails, profileDetails)
            }
            is ProfileTypeTwoViewHolder -> {
                holder.itemView.findViewById<TextView>(R.id.textViewValue).text =
                    profileDetails.value
                var image = profileDetails.image
                if (image != null) {
                    holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(image)
                }
                holder.itemView.setOnClickListener(onClickListener)
                holder.itemView.setTag(R.string.tag_profileDetails, profileDetails)
            }
        }
    }
}

class ProfileTypeOneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ProfileTypeTwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ProfileNameEditBottomDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(profileDetails: ProfileDetails?): ProfileNameEditBottomDialogFragment {
            val frag = ProfileNameEditBottomDialogFragment()
            val args = Bundle()
            args.putParcelable(PROFILE_DETAILS, profileDetails)
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogFragmentTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var profileDetails = arguments?.getParcelable<ProfileDetails>(PROFILE_DETAILS)
        val v = inflater.inflate(R.layout.dialog_profile_edit_phone_number, container, false)
        v.findViewById<TextInputEditText>(R.id.textInputEditTextPhoneNumber).setText(profileDetails?.value.toString().trim())
        return v
    }

}

class ProfileImageDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): ProfileImageDialogFragment {
            val frag = ProfileImageDialogFragment()
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogFragmentTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_profile_image, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu_close_item)
        }
        view.findViewById<RecyclerView>(R.id.rv_profile_photo).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ProfilePhotoAdapter(activity as ProfileActivity)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                    outRect.left = parent.resources.getDimension(R.dimen.margin_sixteen_dp).toInt()
                    outRect.right = parent.resources.getDimension(R.dimen.margin_sixteen_dp).toInt()
                }
            })
        }
    }

}

class ProfilePhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ProfilePhotoAdapter(val onClickListener: View.OnClickListener?) : RecyclerView.Adapter<ProfilePhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_profile_photo, parent, false)
        return ProfilePhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfilePhotoViewHolder, position: Int) {
        holder.itemView.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int {
        return 2
    }

}