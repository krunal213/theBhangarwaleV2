package com.app.thebhangarwale

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.Drawable
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
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class ProfileActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.title_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val personProfileDetails: ArrayList<ProfileDetails> = ArrayList<ProfileDetails>()
        personProfileDetails.add(
            ProfileDetails(
                R.id.customer_name,
                resources.getString(R.string.title_name),
                "Krunal Kathikar",
                R.drawable.ic_account,
                true
            )
        )
        personProfileDetails.add(
            ProfileDetails(
                R.id.customer_phone,
                resources.getString(R.string.title_phone),
                "+91 8806616913",
                R.drawable.ic_phone,
                false
            )
        )

        findViewById<RecyclerView>(R.id.recyclerViewProfile).apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(ProfileItemDecoration(this@ProfileActivity))
            adapter = ProfileAdapter(personProfileDetails, this@ProfileActivity)
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
        isCancelable = false
        var profileDetails = arguments?.getParcelable<ProfileDetails>(PROFILE_DETAILS)
        val v = inflater.inflate(R.layout.dialog_profile_edit_phone_number, container, false)
        v.findViewById<TextInputEditText>(R.id.textInputEditTextPhoneNumber).setText(profileDetails?.value.toString().trim())
        return v
    }

}