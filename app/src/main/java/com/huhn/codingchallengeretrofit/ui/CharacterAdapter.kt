/*
 * Copyright (c) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huhn.codingchallengeretrofit.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huhn.codingchallengeretrofit.databinding.ItemListContentBinding
import com.huhn.codingchallengeretrofit.model.RelatedTopic

class CharacterAdapter(
    private val onItemClicked: (RelatedTopic) -> Boolean
) :
    ListAdapter<RelatedTopic, CharacterAdapter.CharacterViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder(
            ItemListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class CharacterViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.characterListItemName

        fun bind(character: RelatedTopic) {
            val characterName = filterCharacterNameFromUrl(character.FirstURL )
            nameView.text = characterName
        }

        private fun filterCharacterNameFromUrl(urlString: String) : String {
            return urlString.substringAfter(
                delimiter = "https://duckduckgo.com/",
                missingDelimiterValue = "Some Name"
            ).replace(
                oldChar = '_',
                newChar = ' ',
                ignoreCase = true
            ).replace(
                oldValue = "%22",
                newValue = "\"",
                ignoreCase = true
            )
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<RelatedTopic>() {
            override fun areItemsTheSame(oldItem: RelatedTopic, newItem: RelatedTopic): Boolean {
                return (oldItem.FirstURL == newItem.FirstURL ||
                        oldItem.Result == newItem.Result ||
                        oldItem.Text == newItem.Text  ||
                        oldItem.Icon.Height == oldItem.Icon.Height ||
                        oldItem.Icon.Width == oldItem.Icon.Width ||
                        oldItem.Icon.URL == oldItem.Icon.URL
                        )
            }

            override fun areContentsTheSame(oldItem: RelatedTopic, newItem: RelatedTopic): Boolean {
                return oldItem == newItem
            }
        }
    }
}
