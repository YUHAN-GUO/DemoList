package com.think.guoyh.ui.demo.function.shorcut;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.think.guoyh.R;
import com.think.guoyh.ui.activity.TestActivity;

import java.util.ArrayList;
import java.util.List;

public class ShortcutFragment extends Fragment implements View.OnClickListener {

    private Button shortCutBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shortcut, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        shortCutBt = (Button) view.findViewById(R.id.shortCutBt);

        shortCutBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shortCutBt:
                toAdd();
                break;
        }
    }

    private void toAdd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            Intent intent = new Intent(getActivity(), TestActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("key", "动态快捷的Key");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLUE);
//        String label = getResources().getString(R.string.dynamic_shortcut_long_label2);
            String label = "动态快捷长";
            SpannableStringBuilder colouredLabel = new SpannableStringBuilder(label);
            colouredLabel.setSpan(colorSpan, 0, label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            List<ShortcutInfo> shortcutInfos = new ArrayList<>();
            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(getActivity(), "id1")
                    .setShortLabel("动态快捷短")
                    .setLongLabel("动态快捷长")
                    .setIcon(Icon.createWithResource(getActivity(), R.drawable.icon_audio))
                    .setIntent(intent)
                    .build();
            shortcutInfos.add(shortcutInfo);
            ShortcutManager shortcutManager = getActivity().getSystemService(ShortcutManager.class);
            shortcutManager.addDynamicShortcuts(shortcutInfos);

            Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
        }
    }

}