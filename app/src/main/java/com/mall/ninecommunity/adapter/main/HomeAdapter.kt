/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mall.ninecommunity.adapter.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mall.ninecommunity.databinding.ListItemNewsBinding
import com.mall.ninecommunity.model.TopStoriesBean
import com.mall.ninecommunity.view.fragment.PagerFragmentDirections
import javax.inject.Inject

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class HomeAdapter @Inject constructor() : ListAdapter<TopStoriesBean, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(ListItemNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topStoriesBean = getItem(position)
        (holder as HomeViewHolder).bind(topStoriesBean)
    }

    class HomeViewHolder(
            private val binding: ListItemNewsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.bean?.let { plant ->
                    navigateToPlant(plant, it)
                }
            }
        }

        private fun navigateToPlant(
                topStoriesBean: TopStoriesBean?,
                view: View,
        ) {
            val directions = PagerFragmentDirections.actionPagerFragmentToWebViewFragment(topStoriesBean?.url ?: "")
            view.findNavController().navigate(directions)
        }

        fun bind(item: TopStoriesBean) {
            binding.apply {
                bean = item
                executePendingBindings()
            }
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<TopStoriesBean>() {

    override fun areItemsTheSame(oldItem: TopStoriesBean, newItem: TopStoriesBean): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: TopStoriesBean, newItem: TopStoriesBean): Boolean {
        return oldItem == newItem
    }
}