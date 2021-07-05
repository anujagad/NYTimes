package com.anusha.nytimes.adapter


import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtilsCallBack<T>(val oldList: Iterable<T>,
                                  val newList: Iterable<T>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList.elementAt(oldItemPosition) === newList.elementAt(newItemPosition)

    override fun getOldListSize(): Int = oldList.count()

    override fun getNewListSize(): Int = newList.count()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList.elementAt(oldItemPosition) == newList.elementAt(newItemPosition)

}
