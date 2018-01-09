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
public class SuratPendingRTFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pd;
    Config config;
    String posisiRT,posisiRW;
    LinearLayout div;
    private List<SuratMenungguDiketahui> list = new ArrayList<>();

    public SuratPendingRTFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_surat_pending_rt, container, false);

        config = new Config(getActivity());
        posisiRT = config.getSpPosisirt();
        posisiRW = config.getSpPosisirw();
        div = (LinearLayout)inflaterView.findViewById(R.id.div);
        pd = new ProgressDialog(getActivity());
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
                    if ((s.getPERRT() != null && s.getPERRT().contains(posisiRT)) && (s.getPERRW() != null && s.getPERRW().contains(posisiRW))) {
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
                        status.setText(s.getPERSTATUS());

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
            }
        });
        return inflaterView;
    }

}
