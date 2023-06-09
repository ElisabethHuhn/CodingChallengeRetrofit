package com.huhn.codingchallengeretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.huhn.codingchallengeretrofit.BuildConfig
import com.huhn.codingchallengeretrofit.databinding.FragmentSecondBinding
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

    private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

     private fun updateContent() {
         buildTypeTextView.text = BuildConfig.CHARACTER_TYPE_STRING

         val urlString = fragmentViewModel.currentCharacterRelatedTopic.value?.FirstURL ?: "Unknown Character"
         val characterName = if (urlString.equals("Unknown Character")) urlString
                                   else fragmentViewModel.filterCharacterNameFromUrl(fragmentViewModel.currentCharacterRelatedTopic.value?.FirstURL ?: "https://duckduckgo.com/unknown_character")
         characterNameTextView.text = characterName

         val firstUrl = "FirstURL: ${urlString}"
         firstUrlTextView.text = firstUrl

         val rtText = "Text: ${fragmentViewModel.currentCharacterRelatedTopic.value?.Text ?: ""}"
         rtTextTextView.text   = rtText

         var imageUrl = fragmentViewModel.currentCharacterRelatedTopic.value?.Icon?.URL ?: ""
         imageUrl = if (imageUrl.isEmpty()) "https://duckduckgo.com/i/c9497b7e.png"
         else "https://duckduckgo.com$imageUrl"
         Picasso.get().load(imageUrl).resize(600,600).centerCrop().into(rtImageView)
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        buildTypeTextView = binding.buildType
        characterNameTextView = binding.characterName
        firstUrlTextView = binding.rtFirstUrl
        rtTextTextView = binding.rtText
        rtImageView = binding.characterImage

        updateContent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attach an observer on the currentCharacter to update the UI automatically
        // when the selection changes
        fragmentViewModel.currentCharacterRelatedTopic.observe(this.viewLifecycleOwner) {
            updateContent()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}