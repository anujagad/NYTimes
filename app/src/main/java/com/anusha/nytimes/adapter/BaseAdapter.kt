package com.anusha.nytimes.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<Adapter : BaseAdapter<Adapter, Item, ViewHolder>, Item, ViewHolder : RecyclerView.ViewHolder> : RecyclerView.Adapter<ViewHolder>() {

    protected var itemList = mutableListOf<Item>()

    protected var itemClickListener: (Item) -> Unit = {}
    protected var itemTouchListener: View.OnTouchListener? = null
    protected var emptyListener: () -> Unit = {}
    protected var longClickListener: (Item) -> Unit = {}

    open fun setItemListener(listener: (Item) -> Unit): Adapter {
        this.itemClickListener = listener
        return this as Adapter
    }

    open fun setItemTouchListener(listener: View.OnTouchListener): Adapter {
        this.itemTouchListener = listener
        return this as Adapter
    }

    fun setItemEmptyListener(listener: () -> Unit): Adapter {
        this.emptyListener = listener
        return this as Adapter
    }

    fun setItemLongClickListener(longClickListener: (Item) -> Unit) = apply {
        this.longClickListener = longClickListener
        return this as Adapter
    }

    override fun getItemCount() = itemList.size

    open fun clearList() {
        itemList.clear()
        notifyDataSetChanged()
    }

    open fun setList(list: List<Item>) {
        val diffUtils = DiffUtil.calculateDiff(
            GenericDiffUtilsCallBack(
                itemList,
                list
            )
        )
        itemList = list.toMutableList()
        diffUtils.dispatchUpdatesTo(this)
    }

    open fun resetList(list: List<Item>) {
        val diffUtils = DiffUtil.calculateDiff(
            GenericDiffUtilsCallBack(
                itemList,
                list
            )
        )
        itemList.clear()
        itemList = list.toMutableList()
        diffUtils.dispatchUpdatesTo(this)
    }

    open fun addList(list: List<Item>) {
        val newList = itemList + list
        val diffUtils = DiffUtil.calculateDiff(
            GenericDiffUtilsCallBack(
                itemList,
                newList
            )
        )
        list.forEach { itemList.add(it) }
        diffUtils.dispatchUpdatesTo(this)
    }

    fun getList(): List<Item> = itemList


    open fun addItem(item: Item) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    open fun removeItem(item: Item) {
        itemList.remove(item)
        notifyDataSetChanged()
    }

    open fun removeItems(items: List<Item>) {
        itemList.removeAll(items)
        notifyDataSetChanged()
    }

    //fixme handle case if position is -1
    open fun getItem(position: Int): Item {
        return itemList[position]
    }

}