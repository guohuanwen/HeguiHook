package com.bigwen.hegui.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigwen.hegui.util.ProcessUtil;
import com.bigwen.hegui.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ToolActivity extends Activity {

    private static final String TAG = "MainActicity";
    final String CHILD_PROCESSES = "CHILD_PROCESSES";
    final String VoiceRoomNotifyService = "VoiceRoomNotifyService";
    final String tao_accs_intent_action_service = "com.taobao.accs.intent.action.SERVICE";
    final String com_huawei_push_msg_NOTIFY_MSG = "com.huawei.push.msg.NOTIFY_MSG";
    final String com_huiwan_multiprocess = "com.huiwan.multiprocess";
    final String android_intent_action_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter stringAdapter = new StringAdapter(this);
        recyclerView.setAdapter(stringAdapter);
        List<String> strings = new ArrayList<>();
        strings.add(CHILD_PROCESSES);
        strings.add(VoiceRoomNotifyService);
        strings.add(tao_accs_intent_action_service);
        strings.add(com_huawei_push_msg_NOTIFY_MSG);
        strings.add(com_huiwan_multiprocess);
        strings.add(android_intent_action_PACKAGE_REMOVED);
        stringAdapter.refresh(strings);
        stringAdapter.setItemClick(new ItemClick() {
            @Override
            public void click(String param) {
                switch (param){
                    case CHILD_PROCESSES:
                        startActivity(new Intent(recyclerView.getContext(), ChildActivity.class));
                        break;
                    case VoiceRoomNotifyService:
                        ComponentName chatService = new ComponentName("com.wepie.wespy","com.wepie.wespy.module.voiceroom.util.VoiceRoomNotifyService");
//                ComponentName chatService = new ComponentName("com.wepie.wespy","com.taobao.accs.ChannelService");
                        Intent intent = new Intent();
                        intent.setComponent(chatService);
                        startService(intent);
                        break;
                    case tao_accs_intent_action_service:
                        Intent intent2 = new Intent();
                        intent2.setPackage("com.wepie.wespy");
                        intent2.setAction("com.taobao.accs.intent.action.SERVICE");
                        startService(intent2);
                        break;
                    case com_huawei_push_msg_NOTIFY_MSG:
                        Intent intent3 = new Intent();
                        intent3.setPackage("com.wepie.wespy");
                        intent3.setAction("com.huawei.push.msg.NOTIFY_MSG");
                        startService(intent3);
                        break;
                    case com_huiwan_multiprocess:
                        Intent intent4 = new Intent();
                        intent4.setPackage("com.huiwan.multiprocess");
                        intent4.setAction("com.bigwen");
                        startService(intent4);
                        break;
                    case android_intent_action_PACKAGE_REMOVED:
                        Intent intent5 = new Intent();
                        intent5.setAction("android.intent.action.PACKAGE_REMOVED");
                        sendBroadcast(intent5);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + param);
                }
            }
        });

        String name = ProcessUtil.getCurrentProcessName(this);
        Log.i(TAG, "onCreate: " + name);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.wepie.wespy", 0);
            Log.i(TAG, "onCreate: " + packageInfo.packageName);
            Log.i(TAG, "onCreate: " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class StringAdapter extends RecyclerView.Adapter<StringHolder> {

        List<String> dataList = new ArrayList<>();
        private Context context;
        private ItemClick itemClick;

        public StringAdapter(Context context) {
            this.context = context;
        }

        public void refresh(List<String> text) {
            dataList.clear();
            dataList.addAll(text);
            notifyDataSetChanged();
        }

        public void setItemClick(ItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @NonNull
        @NotNull
        @Override
        public StringHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(context);
            textView.setTextColor(0xff333333);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView.setGravity(Gravity.CENTER);
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60, parent.getResources().getDisplayMetrics());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)px);
            textView.setLayoutParams(layoutParams);
            return new StringHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull StringHolder holder, int position) {
            holder.textView.setText(dataList.get(position));
            holder.textView.setOnClickListener(v -> itemClick.click(dataList.get(position)));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private static class StringHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public StringHolder(@NonNull @NotNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }

    private interface ItemClick {
        void click(String param);
    }
}
