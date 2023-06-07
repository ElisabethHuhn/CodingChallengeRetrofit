package com.huhn.codingchallengeretrofit.ui

import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.huhn.codingchallengeretrofit.BuildConfig
import com.huhn.codingchallengeretrofit.R
import com.huhn.codingchallengeretrofit.databinding.FragmentSecondBinding
import com.huhn.codingchallengeretrofit.model.RelatedTopic
import com.huhn.codingchallengeretrofit.viewmodel.CharacterViewModelImpl
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private val fragmentViewModel : CharacterViewModelImpl by activityViewModels()

//    private var toolbarLayout: CollapsingToolbarLayout? = null

    private lateinit var buildTypeTextView: TextView
    private lateinit var characterNameTextView: TextView
    private lateinit var firstUrlTextView: TextView
    private lateinit var rtTextTextView: TextView
    private lateinit var rtImageView: ImageView

    private var position: Int = 0
    private var selectedRelatedTopic: RelatedTopic? = null


    private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

     private fun updateContent() {
//        toolbarLayout?.title = selectedPattern?.name

         //this.getText(R.string.build_type_placeholder)  //this.getText(R.string.build_type_res)

         buildTypeTextView.text = BuildConfig.CHARACTER_TYPE_STRING
         val urlString = selectedRelatedTopic?.FirstURL ?: "Unknown Character"
         val firstUrl = "FirstURL: ${urlString}"
         val rtText = "Text: ${selectedRelatedTopic?.Text ?: ""}"
         var imageUrl = selectedRelatedTopic?.Icon?.URL ?: ""
         imageUrl = if (imageUrl.isEmpty()) "http://i.imgur.com/DvpvklR.png"
                    else "https://duckduckgo.com$imageUrl"
         Picasso.get().load(imageUrl).resize(600,600).centerCrop().into(rtImageView)

         val characterName = if (urlString.equals("Unknown Character")) urlString
                                   else fragmentViewModel.filterCharacterNameFromUrl(selectedRelatedTopic?.FirstURL ?: "https://duckduckgo.com/unknown_character")
         characterNameTextView.text = characterName
         firstUrlTextView.text = firstUrl
         rtTextTextView.text   = rtText
    }

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
          Toast.makeText(v.context, "Drag Event detected", Toast.LENGTH_SHORT).show()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                val itemFirstUrl = it.getString(ARG_ITEM_ID)
                if (itemFirstUrl != null) {
                    position = fragmentViewModel.findPosition(itemFirstUrl)
                    if (position >= 0) {
                        selectedRelatedTopic = fragmentViewModel.findRelatedTopic(position)!!
                    } else {
                        selectedRelatedTopic = null
                    }
                } else {
                    position = -1
                    selectedRelatedTopic = null
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
//        toolbarLayout = binding.toolbarLayout
        buildTypeTextView = binding.buildType
        characterNameTextView = binding.characterName
        firstUrlTextView = binding.rtFirstUrl
        rtTextTextView = binding.rtText
        rtImageView = binding.characterImage

        selectedRelatedTopic = fragmentViewModel.findRelatedTopic(position)!!

        updateContent()
        binding.root.setOnDragListener(dragListener)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}