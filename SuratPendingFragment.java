package creative.can.com.suratpengantar.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
public class SuratPendingFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pd;
    Config config;
    String nikku;
    LinearLayout div;
    String mStatus;
    private List<SuratMenungguDiketahui> list = new ArrayList<>();
    private LinearLayout lyError;
    private Button btnReload;

    public SuratPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_surat_pending, container, false);
        initView(inflaterView);

        config = new Config(getActivity());
        nikku = config.getSpId();
        div = (LinearLayout) inflaterView.findViewById(R.id.div);
        pd = new ProgressDialog(getActivity());
        recyclerView = (RecyclerView) inflaterView.findViewById(R.id.rv_ditolak);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Reload();

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyError.setVisibility(View.GONE);
                Reload();
            }
        });

        return inflaterView;
    }

    private void Reload() {
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();

        ApiRequest apiRequest = RetrofitConfig.getRetrofit().create(ApiRequest.class);
        Call<ResponseModel> get = apiRequest.getDitolak();
        get.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.dismiss();
                list = response.body().getSuratMenungguDiketahui();
                for (final SuratMenungguDiketahui s : list) {
                    if (s.getNIK() != null && s.getNIK().contains(nikku)) {
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
                        if (mStatus.equalsIgnoreCase("REJECT")) {
                            status.setText("SURAT ANDA DITOLAK");
                            status.setTextColor(getResources().getColor(R.color.merah));
                        } else if (mStatus.equalsIgnoreCase("WAITING")) {
                            status.setText("MENUNGGU PERSETUJUAN");
                            status.setTextColor(getResources().getColor(R.color.bg_login));
                        } else if (mStatus.equalsIgnoreCase("WAITING_APPROVAL_RT")) {
                            status.setText("MENUNGGU PERSETUJUAN RT");
                            status.setTextColor(getResources().getColor(R.color.bg_login));
                        } else if (mStatus.equalsIgnoreCase("WAITING_APPROVAL_RW")) {
                            status.setText("MENUNGGU PERSETUJUAN RW");
                            status.setTextColor(getResources().getColor(R.color.bg_login));
                        } else if (mStatus.equalsIgnoreCase("WAITING_KADES")) {
                            status.setText("MENUNGGU KEPALA DESA");
                            status.setTextColor(getResources().getColor(R.color.bg_login));
                        } else if (mStatus.equalsIgnoreCase("PRINT")) {
                            status.setText("SURAT ANDA TELAH DICETAK");
                            status.setTextColor(getResources().getColor(R.color.bg_login));
                        }

                        div.addView(addView);


//                        mAdapter = new SuratMasukAdapter(getActivity(), list);
//                        recyclerView.setAdapter(mAdapter);
//                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                lyError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView(View inflaterView) {
        lyError = (LinearLayout) inflaterView.findViewById(R.id.ly_error);
        btnReload = (Button) inflaterView.findViewById(R.id.btn_reload);
    }
}
