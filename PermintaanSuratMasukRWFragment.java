package creative.can.com.suratpengantar.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import creative.can.com.suratpengantar.ActivityRT.HomeRTctivity;
import creative.can.com.suratpengantar.ActivityRW.HomeRWActivity;
import creative.can.com.suratpengantar.Adapter.SuratMasukAdapter;
import creative.can.com.suratpengantar.Adapter.SuratMasukRW;
import creative.can.com.suratpengantar.Adapter.SuratWaitingAdapter;
import creative.can.com.suratpengantar.Model.ModelReadSurat;
import creative.can.com.suratpengantar.Model.ResponseModel;
import creative.can.com.suratpengantar.Model.SuratMenungguDiketahui;
import creative.can.com.suratpengantar.R;
import creative.can.com.suratpengantar.helper.Config;
import creative.can.com.suratpengantar.retrofit.ApiRequest;
import creative.can.com.suratpengantar.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PermintaanSuratMasukRWFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pd;
    Config config;
    String posisi, posisiku;
    LinearLayout div;
    String mStatus;
    private List<SuratMenungguDiketahui> list = new ArrayList<>();

    public PermintaanSuratMasukRWFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_permintaan_surat_masuk_rw, container, false);

        config = new Config(getActivity());
        posisiku = config.getSpPosisirw();
        div = (LinearLayout) inflaterView.findViewById(R.id.div);
        pd = new ProgressDialog(getActivity());
        recyclerView = (RecyclerView) inflaterView.findViewById(R.id.rv_surat_rw);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();

        ApiRequest apiRequest = RetrofitConfig.getRetrofit().create(ApiRequest.class);
        Call<ResponseModel> get = apiRequest.getSuratRW();
        get.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.dismiss();
                list = response.body().getSuratMenungguDiketahui();
                if (list == null) {
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    for (final SuratMenungguDiketahui s : list) {
                        if (s.getPERRW() != null && s.getPERRW().contains(posisiku)) {
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.list_waiting, null);

                            final TextView nik = (TextView) addView.findViewById(R.id.txt_nik);
                            final TextView nama = (TextView) addView.findViewById(R.id.txt_nama);
                            final TextView alamat = (TextView) addView.findViewById(R.id.txt_alamat);
                            final TextView rt = (TextView) addView.findViewById(R.id.txt_rt);
                            final TextView rw = (TextView) addView.findViewById(R.id.txt_rw);
                            final TextView surat = (TextView) addView.findViewById(R.id.txt_surat);
                            final TextView keperluan = (TextView) addView.findViewById(R.id.txt_keperluan);
                            final TextView tanggal = (TextView) addView.findViewById(R.id.txt_tanggal);
                            final TextView status = (TextView) addView.findViewById(R.id.txt_status);
                            final CardView cv = (CardView) addView.findViewById(R.id.cv);

                            nik.setText(s.getNIK());
                            nama.setText(s.getPERNAMAWARGA());
                            alamat.setText(s.getPERALAMAT());
                            rt.setText(s.getPERRT());
                            rw.setText(s.getPERRW());
                            surat.setText(s.getSNAMA());
                            keperluan.setText(s.getPERKEPERLUAN());
                            tanggal.setText(s.getPERCREATEDAT());
                            mStatus = s.getPERSTATUS();
                            if (mStatus.equalsIgnoreCase("REJECT")){
                                status.setText("SURAT ANDA DITOLAK");
                                status.setTextColor(getResources().getColor(R.color.merah));
                            }else if (mStatus.equalsIgnoreCase("WAITING")){
                                status.setText("MENUNGGU PERSETUJUAN");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }
                            else if (mStatus.equalsIgnoreCase("WAITING_APPROVAL_RT")){
                                status.setText("MENUNGGU PERSETUJUAN RT");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }else if (mStatus.equalsIgnoreCase("WAITING_APPROVAL_RW")){
                                status.setText("MENUNGGU PERSETUJUAN RW");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }else if (mStatus.equalsIgnoreCase("WAITING_KADES")){
                                status.setText("MENUNGGU KEPALA DESA");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }else if (mStatus.equalsIgnoreCase("PRINT")){
                                status.setText("SURAT ANDA TELAH DICETAK");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }

                            cv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final CharSequence[] dialogItem = {"ACCEPT"};

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setTitle("Pilihan");
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String mNik = s.getPERID();
                                            String mStatus = "WAITING_KADES";
                                            String mRw = config.getSpPosisirw();
                                            switch (i) {
                                                case 0:
                                                    ApiRequest ap3 = RetrofitConfig.getRetrofit().create(ApiRequest.class);
                                                    Call<ResponseModel> send3 = ap3.acceptRW(mNik, mStatus,mRw);
                                                    send3.enqueue(new Callback<ResponseModel>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                                            Log.d("RETRO", "response : " + response.body().toString());
                                                            String kode = response.body().getKode();
                                                            String pesan = response.body().getPesan();

                                                            if (kode.equals("1")) {
                                                                Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getActivity(), HomeRWActivity.class);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            Log.d("RETRO", "Falure : " + t.getMessage());

                                                        }
                                                    });
                                                    break;
                                            }
                                        }
                                    });
                                    builder.create().show();
                                }
                            });
                            div.addView(addView);


//                        mAdapter = new SuratMasukAdapter(getActivity(), list);
//                        recyclerView.setAdapter(mAdapter);
//                        mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return inflaterView;
    }

}
