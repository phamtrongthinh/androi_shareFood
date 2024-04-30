package com.example.myapplication.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class SinggleChoiceDialogFragment extends DialogFragment {
    private String[] categories = {"Đồ Uống", "Món Thịt", "Món Cơm", "Salad", "Món Cá", "Món Nướng"};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_single_choice, null);//ánh xạ ListView từ layout của dialog
        builder.setView(view)
                .setTitle("Chọn danh mục")
                .setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here because we handle the click event separately
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        ListView listView = view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_single_choice, categories);
        //tạo một ArrayAdapter để hiển thị danh sách các mục trong ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Chuyển thông tin của mục đã chọn sang TextView txtphanloaimonan
                String selectedCategory = categories[position];
                ((admin_addFood) getActivity()).onPositiveButtonClicked(categories, position);
                dismiss();// sau khi lấy thông tin xong thi đóng dialog
            }
        });
        return builder.create();
    }
}
