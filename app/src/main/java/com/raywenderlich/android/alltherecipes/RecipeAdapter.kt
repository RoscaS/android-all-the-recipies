package com.raywenderlich.android.alltherecipes

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<Recipe>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    companion object {
        private val LABEL_COLORS = hashMapOf(
                "Low-Carb" to R.color.colorLowCarb,
                "Low-Fat" to R.color.colorLowFat,
                "Low-Sodium" to R.color.colorLowSodium,
                "Medium-Carb" to R.color.colorMediumCarb,
                "Vegetarian" to R.color.colorVegetarian,
                "Balanced" to R.color.colorBalanced
        )
    }

    private class ViewHolder {
        lateinit var title: TextView
        lateinit var sub: TextView
        lateinit var detail: TextView
        lateinit var image: ImageView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_recipe, parent, false)
            holder = ViewHolder()
            holder.image = view.findViewById(R.id.recipe_list_thumbnail) as ImageView
            holder.title = view.findViewById(R.id.recipe_list_title) as TextView
            holder.sub = view.findViewById(R.id.recipe_list_subtitle) as TextView
            holder.detail = view.findViewById(R.id.recipe_list_detail) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val title = holder.title
        val sub = holder.sub
        val detail = holder.detail
        val image = holder.image


        val recipe = getItem(position) as Recipe
        title.text = recipe.title
        sub.text = recipe.description
        detail.text = recipe.label

        Picasso.with(context)
                .load(recipe.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(image)

        title.typeface = ResourcesCompat.getFont(context, R.font.josefinsans_bold)
        sub.typeface = ResourcesCompat.getFont(context, R.font.josefinsans_semibolditalic)
        detail.typeface = ResourcesCompat.getFont(context, R.font.quicksand_bold)

        detail.setTextColor(ContextCompat.getColor(
                context, LABEL_COLORS[recipe.label] ?: R.color.colorPrimary)
        )

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

}