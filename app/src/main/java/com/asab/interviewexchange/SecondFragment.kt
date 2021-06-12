package com.asab.interviewexchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.asab.interviewexchange.databinding.FragmentSecondBinding
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var mAdView: AdView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundledData()

        bannerLoad()
    }

    private fun bannerLoad() {
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
    }

    private fun getBundledData() {
        var bundle:Bundle?=arguments

        binding.textviewQuestion.text="Q: "+bundle?.getString("Question")

        binding.textviewAnswer.text=bundle?.getString("Answer")?.replace("\\n","\n")

        if(!(bundle?.getString("Image_code")!!.equals("empty",ignoreCase=true))) {
            binding.imageViewCodeImage.visibility=View.VISIBLE
            Glide.with(requireActivity())
                .load(bundle?.getString("Image_code"))
                .placeholder(android.R.drawable.picture_frame)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.imageViewCodeImage);
        }else{
            binding.imageViewCodeImage.visibility=View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}