package com.gujiunet.code;

import static com.gujiunet.code.util.UnzipFile.unzip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.filedialog.FileDialog;
import com.kongzue.filedialog.interfaces.FileSelectCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_post#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_post extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_post() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_post.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_post newInstance(String param1, String param2) {
        fragment_post fragment = new fragment_post();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        View post=getView();
        CardView upload_code=post.findViewById(R.id.upload_code);
        upload_code.setOnClickListener(v -> {
            new Thread(() -> {
                String folderPath = "/storage/emulated/0/Android/data/com.gujiunet.code/upload/"; // 根据自己的情况修改路径
                File folder = new File(folderPath);
                if (folder.exists()) {
                    deleteDirectory(folder);
                }
                FileDialog.build().setSuffixArray(new String[]{".iApp"})
                        .selectFile(new FileSelectCallBack() {
                            @Override
                            public void onSelect(File file, String filePath) {
                                PopTip.show("选择的文件：" + filePath);

                                JSONObject jsonObj = new JSONObject();
                                try {
                                    jsonObj.put("file", filePath);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                //String filePath = "/storage/emulated/0/Android/data/com.gujiunet.codeback/account.json";
                                File filea = new File("/storage/emulated/0/Android/data/com.gujiunet.code/file.json");
                                try {
                                    // 判断文件是否存在，如果不存在则创建文件
                                    if (!filea.exists()) {
                                        requireActivity().getExternalFilesDir(null);
                                        filea.createNewFile();
                                    }
                                    FileWriter writer = new FileWriter(filea);
                                    writer.write(jsonObj.toString());
                                    writer.close();
                                    try {
                                        unzip(filePath,"/storage/emulated/0/Android/data/com.gujiunet.code/upload/");
                                        Intent intent = new Intent();
                                        intent.setClass(requireActivity(), code_upload.class);
                                        startActivity(intent);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }




                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }).start();

        });
        CardView post_text=post.findViewById(R.id.post_post_comment);
        post_text.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(requireActivity(), post.class);
            startActivity(intent);
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }
    FileDialog fileDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (fileDialog != null) {
            fileDialog.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private static boolean deleteDirectory(File file) {
        if (file != null && file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return file.delete();
    }
}