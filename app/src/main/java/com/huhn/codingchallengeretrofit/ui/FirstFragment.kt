package com.huhn.codingchallengeretrofit.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huhn.codingchallengeretrofit.databinding.FragmentFirstBinding
import com.huhn.codingchallengeretrofit.databinding.ItemListContentBinding
import com.huhn.codingchallengeretrofit.model.Data
import com.huhn.codingchallengeretrofit.viewmodel.GPatternViewModelImpl
//import org.koin.androidx.viewmodel.ext.android.activityViewModel
//import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val fragmentViewModel : GPatternViewModelImpl by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        /*
         * Define the Recyclerview event listeners
         */
        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as Data
            val bundle = Bundle()
            bundle.putString(
                SecondFragment.ARG_ITEM_ID,
                item.name
            )
            Toast.makeText(
                itemView.context,
                "Click Listener triggered",
                Toast.LENGTH_SHORT
            ).show()

//            if (itemDetailFragmentContainer != null) {
//                itemDetailFragmentContainer.findNavController()
//                    .navigate(R.id.fragment_item_detail, bundle)
//            } else {
//                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
//            }
        }

        /**
         * Context click listener to handle Right click events
         * from mice and trackpad input to provide a more native
         * experience on larger screen devices
         */
        val onContextClickListener = View.OnContextClickListener { v ->
            val item = v.tag as Data
            Toast.makeText(
                v.context,
                "Context click of item " + item.name,
                Toast.LENGTH_LONG
            ).show()
            true
        }

        //initiate the remote data fetch
        fragmentViewModel.getGPatterns()

        //set up observer of the remote pattern data
        fragmentViewModel.gPatternData.observe(
            viewLifecycleOwner
        ) { dataList ->
            setupRecyclerView(
                recyclerView,              //RecyclerView object
                dataList,                  //Data to display
                onClickListener,           //Listener for when user clicks on a pattern
                onContextClickListener     //Listener for a long click, which involves click data
            )
        }

    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        songList: List<Data>,
        onClickListener: View.OnClickListener,
        onContextClickListener: View.OnContextClickListener
    ) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            songList,
            onClickListener,
            onContextClickListener
        )
    }

    class SimpleItemRecyclerViewAdapter(
        private val values: List<Data>,
        private val onClickListener: View.OnClickListener,
        private val onContextClickListener: View.OnContextClickListener
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemRecyclerViewAdapter.ViewHolder {

            val binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.nameView.text = item.name
            holder.startView.text = item.startDate
            holder.endView.text = item.endDate
            holder.urlView.text = item.url

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setOnContextClickListener(onContextClickListener)
                }

                setOnLongClickListener { v ->
                    // Setting the item id as the clip data so that the drop target is able to
                    // identify the id of the content
                    val clipItem = ClipData.Item(item.name)
                    val dragData = ClipData(
                        v.tag as? CharSequence,
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        clipItem
                    )

                    if (Build.VERSION.SDK_INT >= 24) {
                        v.startDragAndDrop(
                            dragData,
                            View.DragShadowBuilder(v),
                            null,
                            0
                        )
                    } else {
                        v.startDrag(
                            dragData,
                            View.DragShadowBuilder(v),
                            null,
                            0
                        )
                    }
                }
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(binding: ItemListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val nameView: TextView = binding.patternName
            val startView: TextView = binding.patternStartDate
            val endView: TextView = binding.patternEndDate
            val urlView: TextView = binding.patternUrl
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}