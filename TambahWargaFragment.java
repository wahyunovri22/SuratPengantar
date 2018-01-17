package creative.can.com.suratpengantar.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import creative.can.com.suratpengantar.Activity.SuratTidakMampuActivity;
import creative.can.com.suratpengantar.Model.ResponseModel;
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
public class TambahWargaFragment extends Fragment {


    private MaterialEditText edtNik;
    private MaterialEditText edtKk;
    private MaterialEditText edtNama;
    private MaterialEditText edtPekerjaan;
    private MaterialEditText edtTempatLahir;
    private MaterialEditText edtAgama;
    private MaterialEditText edtTanggal;
    private RadioGroup rbgKelamin;
    private RadioButton rbPria;
    private RadioButton rbWanita;
    private MaterialEditText edtAlamat;
    private MaterialEditText edtKelurahan;
    private MaterialEditText edtRt;
    private MaterialEditText edtRw;
    private MaterialEditText edtKecamatan;
    private MaterialEditText edtKabupaten;
    private MaterialEditText edtProvinsi;
    private MaterialEditText edtStatusNikah;
    private MaterialEditText edtWarganegara;
    private MaterialEditText edtStatusKk;
    private MaterialEditText edtPendidikan;
    private MaterialEditText edtUsername;
    private MaterialEditText edtPassword;
    private MaterialEditText edtEmail;
    private MaterialEditText edtPhone;
    private Button btnDaftar;

    ProgressDialog pd;
    String jk;

    Config config;
    DatePickerDialog datePickerDialog, datePickerDialog2;

    public TambahWargaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_tambah_warga, container, false);

        pd = new ProgressDialog(getActivity());

        config = new Config(getActivity());
        edtNik = (MaterialEditText)inflaterView.findViewById(R.id.edt_nik);
        edtKk = (MaterialEditText) inflaterView.findViewById(R.id.edt_kk);
        edtNama = (MaterialEditText) inflaterView.findViewById(R.id.edt_nama);
        edtPekerjaan = (MaterialEditText) inflaterView.findViewById(R.id.edt_pekerjaan);
        edtTempatLahir = (MaterialEditText) inflaterView.findViewById(R.id.edt_tempat_lahir);
        edtAgama = (MaterialEditText) inflaterView.findViewById(R.id.edt_agama);
        edtTanggal = (MaterialEditText) inflaterView.findViewById(R.id.edt_tanggal);
        rbgKelamin = (RadioGroup) inflaterView.findViewById(R.id.rbg_kelamin);
        rbPria = (RadioButton) inflaterView.findViewById(R.id.rb_pria);
        rbWanita = (RadioButton) inflaterView.findViewById(R.id.rb_wanita);
        edtAlamat = (MaterialEditText) inflaterView.findViewById(R.id.edt_alamat);
        edtKelurahan = (MaterialEditText) inflaterView.findViewById(R.id.edt_kelurahan);
        edtRt = (MaterialEditText) inflaterView.findViewById(R.id.edt_rt);
        edtRw = (MaterialEditText) inflaterView.findViewById(R.id.edt_rw);
        edtKecamatan = (MaterialEditText) inflaterView.findViewById(R.id.edt_kecamatan);
        edtKabupaten = (MaterialEditText) inflaterView.findViewById(R.id.edt_kabupaten);
        edtProvinsi = (MaterialEditText) inflaterView.findViewById(R.id.edt_provinsi);
        edtStatusNikah = (MaterialEditText) inflaterView.findViewById(R.id.edt_status_nikah);
        edtWarganegara = (MaterialEditText) inflaterView.findViewById(R.id.edt_warganegara);
        edtStatusKk = (MaterialEditText) inflaterView.findViewById(R.id.edt_status_kk);
        edtPendidikan = (MaterialEditText) inflaterView.findViewById(R.id.edt_pendidikan);
        edtUsername = (MaterialEditText) inflaterView.findViewById(R.id.edt_username);
        edtPassword = (MaterialEditText) inflaterView.findViewById(R.id.edt_password);
        edtEmail = (MaterialEditText) inflaterView.findViewById(R.id.edt_email);
        edtPhone = (MaterialEditText) inflaterView.findViewById(R.id.edt_phone);
        btnDaftar = (Button) inflaterView.findViewById(R.id.btn_daftar);

        edtPekerjaan.setFocusable(false);
        edtAgama.setFocusable(false);
        edtTanggal.setFocusable(false);
        edtKelurahan.setFocusable(false);
        edtRt.setFocusable(false);
        edtRw.setFocusable(false);
        edtKecamatan.setFocusable(false);
        edtStatusNikah.setFocusable(false);
        edtStatusKk.setFocusable(false);
        edtWarganegara.setText("WNI");
        edtPendidikan.setFocusable(false);
        edtKabupaten.setText("Kendal");
        edtProvinsi.setText("Jawa Tengah");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new
                DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String tanggal = year + "-" + (month + 1) + "-" + dayOfMonth;
                edtTanggal.setFocusable(false);
                edtTanggal.setCursorVisible(false);
                edtTanggal.setText(tanggal);
            }
        }, year, month, day);



        edtTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarWarga();
            }
        });
        edtPekerjaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogItem = {"PNS", "Wirausaha", "Swasta", "Buruh", "Lainnya"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pilih Pekerjaan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                edtPekerjaan.setText("PNS");
                                break;
                            case 1:
                                edtPekerjaan.setText("Wirausaha");
                                break;
                            case 2:
                                edtPekerjaan.setText("Swasta");
                                break;
                            case 3:
                                edtPekerjaan.setText("Buruh");
                                break;
                            case 4:
                                edtPekerjaan.setText("Lainnya");
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        edtAgama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogItem = {"Islam", "Kristen", "Katolik", "Hindu", "Budha"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pilih Agama");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                edtAgama.setText("Islam");
                                break;
                            case 1:
                                edtAgama.setText("Kristen");
                                break;
                            case 2:
                                edtAgama.setText("Katolik");
                                break;
                            case 3:
                                edtAgama.setText("Hindu");
                                break;
                            case 4:
                                edtAgama.setText("Budha");
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        edtStatusNikah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogItem = {"Nikah", "Lajang", "Cerai Hidup", "Cerai Mati"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pilih Status");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                edtStatusNikah.setText("Nikah");
                                break;
                            case 1:
                                edtStatusNikah.setText("Lajang");
                                break;
                            case 2:
                                edtStatusNikah.setText("Cerai Hidup");
                                break;
                            case 3:
                                edtStatusNikah.setText("Cerai Mati");
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        edtStatusKk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogItem = {"Anak", "Suami", "Isteri", "Cucu"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pilih Status KK");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                edtStatusKk.setText("Anak");
                                break;
                            case 1:
                                edtStatusKk.setText("Suami");
                                break;
                            case 2:
                                edtStatusKk.setText("Isteri");
                                break;
                            case 3:
                                edtStatusKk.setText("Cucu");
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        edtPendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogItem = {"SD", "SMP", "SMA", "D3", "Sarjana", "Lainnya"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pilih Pendidikan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                edtPendidikan.setText("SD");
                                break;
                            case 1:
                                edtPendidikan.setText("SMP");
                                break;
                            case 2:
                                edtPendidikan.setText("SMA");
                                break;
                            case 3:
                                edtPendidikan.setText("D3");
                                break;
                            case 4:
                                edtPendidikan.setText("Sarjana");
                                break;
                            case 5:
                                edtPendidikan.setText("Lainnya");
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

//        edtKelurahan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final CharSequence[] dialogItem = {"Krajankulon", "Plantaran"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Pilih Kelurahan");
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        switch (i) {
//                            case 0:
//                                edtKelurahan.setText("Krajankulon");
//                                break;
//                            case 1:
//                                edtKelurahan.setText("Plantaran");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//            }
//        });

//        edtRt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final CharSequence[] dialogItem = {"1", "2", "3", "4", "5", "6"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Pilih RT");
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        switch (i) {
//                            case 0:
//                                edtRt.setText("1");
//                                break;
//                            case 1:
//                                edtRt.setText("2");
//                                break;
//                            case 2:
//                                edtRt.setText("3");
//                                break;
//                            case 3:
//                                edtRt.setText("4");
//                                break;
//                            case 4:
//                                edtRt.setText("5");
//                                break;
//                            case 5:
//                                edtRt.setText("6");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//            }
//        });
//
//        edtRw.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                final CharSequence[] dialogItem = {"1", "2", "3", "4", "5", "6"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Pilih RW");
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        switch (i) {
//                            case 0:
//                                edtRw.setText("1");
//                                break;
//                            case 1:
//                                edtRw.setText("2");
//                                break;
//                            case 2:
//                                edtRw.setText("3");
//                                break;
//                            case 3:
//                                edtRw.setText("4");
//                                break;
//                            case 4:
//                                edtRw.setText("5");
//                                break;
//                            case 5:
//                                edtRw.setText("6");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//            }
//        });
//
//        edtKecamatan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final CharSequence[] dialogItem = {"Kaliwungu", "Kaliwungu Selatan"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Pilih Kecamatan");
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        switch (i) {
//                            case 0:
//                                edtKecamatan.setText("Kaliwungu");
//                                break;
//                            case 1:
//                                edtKecamatan.setText("Kaliwungu Selatan");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//            }
//        });

        return inflaterView;
    }

    public void daftarWarga(){
        String created = config.getSPNama();
        String rule = "U_WARGA";
        String status = "1";
        String nik2 = edtNik.getText().toString();
        String kk = edtKk.getText().toString();
        String nama = edtNama.getText().toString();
        String pekerjaan = edtPekerjaan.getText().toString();
        String tempatlahir = edtTempatLahir.getText().toString();
        String tgl = edtTanggal.getText().toString();
        String agama = edtAgama.getText().toString();
        if (rbPria.isChecked()){
            jk = "1";
        }else {
            jk = "2";
        }
        String rw = edtRw.getText().toString();
        String rt = edtRt.getText().toString();
        String alamat = edtAlamat.getText().toString();
        String kelurahan = edtKelurahan.getText().toString();
        String kecamatan = edtKecamatan.getText().toString();
        String kabupaten = edtKabupaten.getText().toString();
        String provinsi = edtProvinsi.getText().toString();
        String status_nikah = edtStatusNikah.getText().toString();
        String status_kk = edtStatusKk.getText().toString();
        String pendidikan = edtPendidikan.getText().toString();
        String penduduk = edtWarganegara.getText().toString();
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();
        String noTlp = edtPhone.getText().toString();
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();

        ApiRequest apiRequest = RetrofitConfig.getRetrofit().create(ApiRequest.class);
        Call<ResponseModel> get = apiRequest.insertWarga(nik2, kk, nama, tempatlahir, tgl,
                jk, alamat, rt, rw, kelurahan, kecamatan, kabupaten, provinsi, agama, status_nikah, status_kk, pendidikan,
                pekerjaan, penduduk,created);
        get.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.dismiss();
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                if (kode.equals("1")) {
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ApiRequest apiRequest2 = RetrofitConfig.getRetrofit().create(ApiRequest.class);
        Call<ResponseModel> get2 = apiRequest2.insertUser(username, nama, nik2, email, password, noTlp, rule,status);
        get2.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.dismiss();
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                if (kode.equals("1")) {
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        clear();
    }

    public void clear(){
        edtNik.setText(null);
        edtKk.setText(null);
        edtNama.setText(null);
        edtPekerjaan.setText(null);
        edtTempatLahir.setText(null);
        edtTanggal.setText(null);
        edtAgama.setText(null);
        edtAlamat.setText(null);
        edtKelurahan.setText(null);
        edtRt.setText(null);
        edtRw.setText(null);
        edtKecamatan.setText(null);
        edtStatusNikah.setText(null);
        edtStatusKk.setText(null);
        edtPendidikan.setText(null);
        edtUsername.setText(null);
        edtPassword.setText(null);
        edtEmail.setText(null);
        edtPhone.setText(null);
    }

}
