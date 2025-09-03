package com.bytecoder.dailywaves.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bytecoder.dailywaves.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var content: String? = null
    private var imageUrl: String? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title")
            content = it.getString("content")
            imageUrl = it.getString("imageUrl")
            url = it.getString("url")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.text = title ?: ""
        binding.tvContent.text = content ?: ""
        Glide.with(binding.ivHeader).load(imageUrl).centerCrop().into(binding.ivHeader)

        binding.btnReadMore.setOnClickListener {
            url?.let { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it))) }
        }
        binding.btnShare.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "$title\n\n$url")
            }
            startActivity(Intent.createChooser(share, "Share via"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
