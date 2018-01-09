package creative.can.com.suratpengantar.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

import creative.can.com.suratpengantar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private static final String KEY_PARAM = "key_param";
    public static PageFragment newInstance(String param) {
        PageFragment f = new PageFragment();
        f.setArguments(arguments(param));
        return f;
    }

    public static Bundle arguments(String param) {
        return new Bundler()
                .putString(KEY_PARAM, param)
                .get();
    }


    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedview =  inflater.inflate(R.layout.fragment_page, container, false);

        String param = getArguments().getString(KEY_PARAM);

        TextView pageText = (TextView)inflatedview.findViewById(R.id.page_text);
        //pageText.setText(param);
        return inflatedview;
    }
}
