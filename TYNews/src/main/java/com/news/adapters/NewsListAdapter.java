package com.news.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.entities.NewsListType;

import net.tsz.afinal.FinalBitmap;

import org.androidx.frames.ImagesLoader;
import org.androidx.frames.adapters.BaseMultiAdapter;


/**
 * 新闻列表adapter
 *
 * @author slioe shu
 */
public class NewsListAdapter extends BaseMultiAdapter<NewsListType> {
    private ImagesLoader loader;
    private FinalBitmap imageLoader;

    public NewsListAdapter(Context context, SparseArray<Integer> views, ImagesLoader loader) {
        super(context, views);
        this.loader = loader;
        imageLoader = FinalBitmap.create(context);
    }

    @Override
    public int getItemViewType(int position) {
        String type = getItem(position).getNewstype();
        return Integer.parseInt(type) - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        NewsListType data = getItem(position);

        ImageHolder imageHolder = null, spacialHolder = null;
        ImagesHolder imagesHolder = null;
        TextHolder textHolder = null;

        if (convertView == null) {
            switch (viewType) {
                case ViewType.NEWS_IMAGE:
                    imageHolder = new ImageHolder();
                    convertView = View.inflate(context, views.get(viewType), null);
                    imageHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    imageHolder.ivDesc1 = (ImageView) convertView.findViewById(R.id.ivOne);
                    convertView.setTag(imageHolder);
                    break;
                case ViewType.NEWS_SPACIAL:
                    spacialHolder = new ImageHolder();
                    convertView = View.inflate(context, views.get(viewType), null);
                    spacialHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    spacialHolder.ivDesc1 = (ImageView) convertView.findViewById(R.id.ivOne);
                    convertView.setTag(spacialHolder);
                    break;
                case ViewType.NEWS_IMAGES:
                    imagesHolder = new ImagesHolder();
                    convertView = View.inflate(context, views.get(viewType), null);
                    imagesHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    imagesHolder.ivDesc1 = (ImageView) convertView.findViewById(R.id.ivOne);
                    imagesHolder.ivDesc2 = (ImageView) convertView.findViewById(R.id.ivTwo);
                    imagesHolder.ivDesc3 = (ImageView) convertView.findViewById(R.id.ivThree);
                    convertView.setTag(imagesHolder);
                    break;
                case ViewType.NEWS_TEXT:
                    textHolder = new TextHolder();
                    convertView = View.inflate(context, views.get(viewType), null);
                    textHolder.ivDesc1 = (ImageView) convertView.findViewById(R.id.ivOne);
                    textHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    textHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                    textHolder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
                    textHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
                    convertView.setTag(textHolder);
                    break;
                default:
                    break;
            }
        }

        switch (viewType) {
            case ViewType.NEWS_IMAGE:
                imageHolder = (ImageHolder) convertView.getTag();
                setViewImage(imageHolder, data);
                break;
            case ViewType.NEWS_SPACIAL:
                spacialHolder = (ImageHolder) convertView.getTag();
                setViewSpacia(spacialHolder, data);
                break;
            case ViewType.NEWS_IMAGES:
                imagesHolder = (ImagesHolder) convertView.getTag();
                setViewImages(imagesHolder, data);
                break;
            case ViewType.NEWS_TEXT:
                textHolder = (TextHolder) convertView.getTag();
                setViewText(textHolder, data);
            default:
                break;
        }
        return convertView;
    }

    private void setViewImage(ImageHolder holder, NewsListType data) {
        holder.tvTitle.setText(data.getNewstitle());
//        loader.display(holder.ivDesc1, data.getNewsicon());
        imageLoader.display(holder.ivDesc1, data.getNewsicon());
    }

    private void setViewSpacia(ImageHolder holder, NewsListType data) {
        holder.tvTitle.setText(data.getSpecialtitle());
        imageLoader.display(holder.ivDesc1, data.getSpecialimag());
    }

    private void setViewImages(ImagesHolder holder, NewsListType data) {
        holder.tvTitle.setText(data.getNewstitle());
        for (int i = 0; i < data.getNewspics().size(); i++) {
            switch (i) {
                case 0:
                    imageLoader.display(holder.ivDesc1, data.getNewspics().get(i).getPic());
                    break;
                case 1:
                    imageLoader.display(holder.ivDesc2, data.getNewspics().get(i).getPic());
                    break;
                case 2:
                    imageLoader.display(holder.ivDesc3, data.getNewspics().get(i).getPic());
                    break;
                default:
                    break;
            }
        }
    }

    private void setViewText(TextHolder holder, NewsListType data) {
        holder.tvTitle.setText(data.getNewstitle());
        if (!TextUtils.isEmpty(data.getNewsicon())) {
            loader.display(holder.ivDesc1, data.getNewsicon());
        } else {
            holder.ivDesc1.setVisibility(View.GONE);
        }
        holder.tvContent.setText(data.getNewssubtitle());
        String label = data.getNewslabel();
        if (TextUtils.isEmpty(label)) {
            holder.tvCategory.setVisibility(View.INVISIBLE);
        } else {
            holder.tvCategory.setText(data.getNewslabel());
        }
        if ("0".equals(data.getComment_count())) {
            holder.tvComment.setVisibility(View.INVISIBLE);
        } else {
            holder.tvComment.setText(data.getComment_count());
        }
    }

    //=======ViewHolder==========
    // 大图新闻
    private class ImageHolder {
        TextView tvTitle;
        ImageView ivDesc1;
    }

    // 多图新闻
    private class ImagesHolder {
        TextView tvTitle;
        ImageView ivDesc1;
        ImageView ivDesc2;
        ImageView ivDesc3;
    }

    // 简要新闻
    private class TextHolder {
        TextView tvTitle, tvContent, tvCategory, tvComment;
        ImageView ivDesc1;
    }
}
