package classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import gr.teicm.msc.beautez.R;

public class LazyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, Object>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, Object>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader=new ImageLoader(activity);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        TextView title = (TextView)vi.findViewById(R.id.cosmetic_item_title);
        TextView product = (TextView)vi.findViewById(R.id.cosmetic_item_product);
        TextView genre = (TextView)vi.findViewById(R.id.cosmetic_item_genre);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.imageViewCover);

        HashMap<String, Object> cosmetic= new HashMap<String, Object>();
        cosmetic = data.get(position);

        // Setting all values in listview
        title.setText((String)cosmetic.get(DataStore.KEY_TITLE));
        product.setText((String)cosmetic.get(DataStore.KEY_PRODUCT));
        genre.setText((String)cosmetic.get(DataStore.KEY_GENRENAME));
        imageLoader.DisplayImage((String)cosmetic.get(DataStore.KEY_IMAGEURL), thumb_image);
        return vi;
    }

}
