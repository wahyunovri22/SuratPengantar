package creative.can.com.suratpengantar.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import creative.can.com.suratpengantar.Activity.SPTJMActivity;
import creative.can.com.suratpengantar.Activity.SuratBelumpernahMenikahActivity;
import creative.can.com.suratpengantar.Activity.SuratHewanActivity;
import creative.can.com.suratpengantar.Activity.SuratHilangActivity;
import creative.can.com.suratpengantar.Activity.SuratKetNikahActivity;
import creative.can.com.suratpengantar.Activity.SuratKetUsahaActivity;
import creative.can.com.suratpengantar.Activity.SuratPernyataanKuasaActivity;
import creative.can.com.suratpengantar.Activity.SuratTidakMampuActivity;
import creative.can.com.suratpengantar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PermintaanSuratFragment extends Fragment {

    private CardView sHilang;
    private CardView sHewan;
    private CardView sNikah;
    private CardView sUsaha;
    private CardView sBelumMenikah;
    private CardView sKuasa;
    private CardView sSptjm;
    private CardView sTidakMampu;

    public static PermintaanSuratFragment newInstance() {
        PermintaanSuratFragment fragment = new PermintaanSuratFragment();
        return fragment;
    }

    public PermintaanSuratFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_permintaan_surat, container, false);
        sHilang = (CardView) inflaterView.findViewById(R.id.s_hilang);
        sHewan = (CardView) inflaterView.findViewById(R.id.s_hewan);
        sNikah = (CardView) inflaterView.findViewById(R.id.s_nikah);
        sUsaha = (CardView) inflaterView.findViewById(R.id.s_usaha);
        sBelumMenikah = (CardView) inflaterView.findViewById(R.id.s_belum_menikah);
        sKuasa = (CardView) inflaterView.findViewById(R.id.s_kuasa);
        sSptjm = (CardView) inflaterView.findViewById(R.id.s_sptjm);
        sTidakMampu = (CardView) inflaterView.findViewById(R.id.s_tidak_mampu);

        sHilang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratHilangActivity.class);
                startActivity(intent);
            }
        });
        sHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratHewanActivity.class);
                startActivity(intent);
            }
        });
        sNikah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratKetNikahActivity.class);
                startActivity(intent);
            }
        });
        sUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratKetUsahaActivity.class);
                startActivity(intent);
            }
        });
        sBelumMenikah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratBelumpernahMenikahActivity.class);
                startActivity(intent);
            }
        });
        sKuasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratPernyataanKuasaActivity.class);
                startActivity(intent);
            }
        });
        sSptjm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SPTJMActivity.class);
                startActivity(intent);
            }
        });
        sTidakMampu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuratTidakMampuActivity.class);
                startActivity(intent);
            }
        });

        return inflaterView;
    }

}
