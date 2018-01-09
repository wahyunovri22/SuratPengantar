package creative.can.com.suratpengantar.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import creative.can.com.suratpengantar.Activity.EditProfilActivity;
import creative.can.com.suratpengantar.R;
import creative.can.com.suratpengantar.helper.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {


    private TextView txtNamaUser;
    private TextView txtNikUser;
    private TextView txtAlamat;
    private TextView txtTempatLahir;
    private TextView txtTanggal;
    private TextView txtPekerjaan;
    private TextView txtAgama;
    private FloatingActionButton btnEditData;

    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();
        return fragment;
    }


    public ProfilFragment() {
        // Required empty public constructor
    }


    Button btnEdit;
    Config config;
    Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_profil, container, false);

        config = new Config(getActivity());
        Bundle argument = getArguments();
        txtNamaUser = (TextView) inflaterView.findViewById(R.id.txt_nama_user);
        txtNikUser = (TextView) inflaterView.findViewById(R.id.txt_nik_user);
        txtAlamat = (TextView) inflaterView.findViewById(R.id.txt_alamat);
        txtTempatLahir = (TextView) inflaterView.findViewById(R.id.txt_tempat_lahir);
        txtTanggal = (TextView) inflaterView.findViewById(R.id.txt_tanggal);
        txtPekerjaan = (TextView) inflaterView.findViewById(R.id.txt_pekerjaan);
        txtAgama = (TextView) inflaterView.findViewById(R.id.txt_agama);
        btnEditData = (FloatingActionButton)inflaterView.findViewById(R.id.btn_edit);

        this.handler = new Handler();
        this.handler.postDelayed(runnable, 3000);
        txtNamaUser.setText(config.getSPNama());
        txtNikUser.setText(config.getSpId());
        txtAlamat.setText(config.getSpAlamat());
        txtTempatLahir.setText(config.getSpTempatLahir());
        txtTanggal.setText(config.getSpTtl());
        txtPekerjaan.setText(config.getSpPekerjaan());
        txtAgama.setText(config.getSP_AGAMa());
        //refresh();

//        txtNamaUser.setText(config.getSPNama());
//        txtNikUser.setText(config.getSpId());


        btnEdit = (Button) inflaterView.findViewById(R.id.btn_edit2);
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String nik = txtNikUser.getText().toString();
//                String nama = txtNamaUser.getText().toString();
//                Intent intent = new Intent(getActivity(), EditProfilActivity.class);
//                intent.putExtra("nikuser", nik);
//                intent.putExtra("namauser", nama);
//                startActivity(intent);
//            }
//        });

        btnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = txtNikUser.getText().toString();
                String nama = txtNamaUser.getText().toString();
                Intent intent = new Intent(getActivity(), EditProfilActivity.class);
                intent.putExtra("nikuser", nik);
                intent.putExtra("namauser", nama);
                startActivity(intent);
            }
        });

        return inflaterView;
    }

    public final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            txtNamaUser.setText(config.getSPNama());
            txtNikUser.setText(config.getSpId());
            txtAlamat.setText(config.getSpAlamat());
            txtTempatLahir.setText(config.getSpTempatLahir());
            txtTanggal.setText(config.getSpTtl());
            txtPekerjaan.setText(config.getSpPekerjaan());
            txtAgama.setText(config.getSP_AGAMa());
            ProfilFragment.this.handler.postDelayed(runnable, 3000);
        }
    };


//    public void refresh(){
//        txtNamaUser.setText(config.getSPNama());
//        txtNikUser.setText(config.getSpId());
//    }
}
