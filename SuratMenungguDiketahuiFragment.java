package creative.can.com.suratpengantar.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import creative.can.com.suratpengantar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuratMenungguDiketahuiFragment extends Fragment {


    public SuratMenungguDiketahuiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_surat_menunggu_diketahui, container, false);
    }

}
