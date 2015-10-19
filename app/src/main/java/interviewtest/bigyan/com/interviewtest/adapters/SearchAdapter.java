package interviewtest.bigyan.com.interviewtest.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import interviewtest.bigyan.com.interviewtest.R;
import interviewtest.bigyan.com.interviewtest.RoundedTransformation;
import interviewtest.bigyan.com.interviewtest.model.DescriptionModel;

/**
 * Created by bigyanthapa on 10/15/15.
 */
public class SearchAdapter extends BaseAdapter {

    private List<DescriptionModel> descriptionModelList;
    private Context context;

    //create a view
    private View newView;

    public SearchAdapter(List<DescriptionModel> descriptionModelList, Context context) {
        this.descriptionModelList = descriptionModelList;
        this.context = context;
    }


    @Override
    public int getCount() {

        return descriptionModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return descriptionModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return descriptionModelList.indexOf(getItem(i));
    }


    @Override

    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        newView = inflater.inflate(R.layout.list_row_design, null);


        TextView titleText = (TextView) newView.findViewById(R.id.titleTextView);
        TextView descriptionText = (TextView) newView.findViewById(R.id.descriptionTextView);

        DescriptionModel descriptionModel = descriptionModelList.get(i);

        // we will set values here later

        titleText.setText(descriptionModel.getTitle());
        descriptionText.setText(descriptionModel.getDescription());
        //Initialize ImageView
        ImageView imageView = (ImageView) newView.findViewById(R.id.imageView);

       //Using the Picasso API

        Picasso.with(context)
                .load(descriptionModel.getImageSource())
                .transform(new RoundedTransformation(100, 0))
                .fit()
                .centerCrop()
                .into(imageView);
               // .into(imageView);


        return newView;
    }

}
