package com.huhn.codingchallengeretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.huhn.codingchallengeretrofit.databinding.FragmentFirstBinding
import com.huhn.codingchallengeretrofit.model.RelatedTopic
import com.huhn.codingchallengeretrofit.viewmodel.CharacterViewModelImpl

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val fragmentViewModel : CharacterViewModelImpl by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.fetchCharacters() // Make the API fetch

        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            CharacterListOnBackPressedCallback(slidingPaneLayout)
        )

        val recyclerView: RecyclerView = binding.itemList

        /*
         * Define the Recyclerview event listeners
         */
        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        val onItemClicked = { characterRelatedTopic: RelatedTopic ->
            // Update the user selected item as the current character in the shared viewmodel
            // This will automatically update the dual pane content
            fragmentViewModel.updateCurrentRelatedTopic(characterRelatedTopic)
            // Slide the detail pane into view. If both panes are visible,
            // this has no visible effect.
            binding.slidingPaneLayout.openPane()
        }

        /*
         * Listener for the search text button
         * filters the remote list of characters leaving only ones that match the search text
         */
        binding.buttonSearch.setOnClickListener {
            //filter the list from remote data by query text
            fragmentViewModel.searchText = binding.queryTextInput.text.toString()
            //trigger redisplay of list

            fragmentViewModel.fetchCharacters() // only need if the list changes sometimes

            fragmentViewModel.characterRelatedTopics.value?.let {
                setupRecyclerView(
                    recyclerView,            //RecyclerView object
                    onItemClicked,           //Listener for when user clicks on a pattern
                )
            }
        }
        //set up observer of the remote character data
        fragmentViewModel._characterRelatedTopics.observe(viewLifecycleOwner) {
            setupRecyclerView(
                recyclerView = recyclerView,
                onItemClicked = onItemClicked
            )
        }

        fragmentViewModel.fetchCharacters() // Make the API fetch
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onItemClicked: (RelatedTopic) -> Boolean,
    ) {
        // Initialize the adapter and set it to the RecyclerView.
        val adapter = CharacterAdapter (onItemClicked = onItemClicked)
        recyclerView.adapter = adapter
        val values = fragmentViewModel.characterRelatedTopics.value
        adapter.submitList(values)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/**
 * Callback providing custom back navigation.
 */

class CharacterListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(
    // Set the default 'enabled' state to true only if it is slidable (i.e., the panes
    // are overlapping) and open (i.e., the detail pane is visible).
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
), SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        // Return to the list pane when the system back button is pressed.
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        // Intercept the system back button when the detail pane becomes visible.
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        // Disable intercepting the system back button when the user returns to the
        // list pane.
        isEnabled = false
    }
}
