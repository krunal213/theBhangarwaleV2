package com.app.thebhangarwale

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.app.thebhangarwale.address.view.CreateAddressActivity
import com.app.thebhangarwale.address.viewmodel.AddressViewModel
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.ProgressBarDialog
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import javax.inject.Inject

class EditAddressPickerFragment : Fragment(), View.OnClickListener {

    private val progressBarDialog by lazy {
        activity?.let { ProgressBarDialog(it).show() }
    }

    @Inject
    lateinit var addressViewModel : AddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address_picker,container,false)
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
            .injectEditAddressPickerFragment(this)
        super.onViewCreated(view, savedInstanceState)
        val toolbar : MaterialToolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        view.findViewById<MaterialButton>(R.id.button_submit).apply {
            setOnClickListener(this@EditAddressPickerFragment)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_submit->{
                addressViewModel.submitAddress().observe(viewLifecycleOwner, Observer{
                    when(it){
                        is BhangarwaleResult.Loading->{
                            progressBarDialog?.show()
                        }
                        is BhangarwaleResult.Success->{
                            Toast
                                .makeText(activity,it.data, Toast.LENGTH_LONG)
                                .show()
                            activity?.finish()
                        }
                    }
                })
            }
        }
    }


}