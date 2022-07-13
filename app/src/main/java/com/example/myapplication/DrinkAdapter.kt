package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.DrinkListItemBinding
import com.example.myapplication.model.DrinkItemUI
import com.example.myapplication.util.loadImage

/**
 * Adapter in charge of handle data set,
 * Please note. Usually this implements a [DiffUtil] inteface to make this adapter performant
 */
class DrinkAdapter(
    private val listener: (drink: DrinkItemUI) -> Unit
) : RecyclerView.Adapter<DrinkAdapter.ViewHolder>() {

    private var items = emptyList<DrinkItemUI>()

    fun updateData(data: List<DrinkItemUI>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DrinkListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        private val binding: DrinkListItemBinding,
        val listener: (drink: DrinkItemUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(drink: DrinkItemUI) {
            binding.tVDrinkName.text = drink.name
            binding.tVDrinkCategory.text = drink.category
            binding.tVDrinkTags.text = drink.tags
            binding.iVDrinkImage.loadImage(drink.imageUrl)

            binding.root.setOnClickListener {
                listener(drink)
            }
        }
    }
}
