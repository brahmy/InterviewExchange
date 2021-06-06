package com.asab.interviewexchange

import android.app.ActionBar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TopicImageAdapter(internal var context: Context, internal var itemList: List<String>) : PagerAdapter() {

    internal var mLayoutInflater: LayoutInflater
    private var pos = 0

    init {
        mLayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val holder = ViewHolder()
        val itemView = mLayoutInflater.inflate(R.layout.adapter_topic_images, container, false)
        holder.itemImage = itemView.findViewById(R.id.imageViewMain) as ImageView

        if (pos > this.itemList.size - 1)
            pos = 0

        holder.sliderItem = this.itemList[pos]

            Glide.with(context)
                .load(holder.sliderItem)
                .placeholder(android.R.drawable.picture_frame)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.itemImage)

        (container as ViewPager).addView(itemView)

        holder.itemImage.scaleType = ImageView.ScaleType.FIT_XY

        pos++
        return itemView
    }

    internal class ViewHolder {
        lateinit var sliderItem: String
        lateinit var itemImage: ImageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1 as View
    }
    override fun destroyItem(arg0: View, arg1: Int, arg2: Any) {
        (arg0 as ViewPager).removeView(arg2 as View)
    }
}