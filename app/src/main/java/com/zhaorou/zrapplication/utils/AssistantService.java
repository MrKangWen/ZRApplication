package com.zhaorou.zrapplication.utils;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AssistantService extends AccessibilityService {

    /**
     * 助手服务是否正在运行
     */
    public static boolean isAssistantRunning = false;
    public static String mMoments = "";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            //窗口发生改变时会调用该事件
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                /*
                    微信几个页面的包名+地址。用于判断在哪个页面
                 */

                String SnsLineUi = "com.tencent.mm.plugin.sns.ui.SnsUploadUI";
                if (className.equals(SnsLineUi)) {//发送朋友圈的界面
                    AccessibilityNodeInfo rootNode = getRootInActiveWindow();

                    if (rootNode == null) {
                        return;
                    }

                    List<AccessibilityNodeInfo> edt = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        edt = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/dp0");
                    }
                    if (edt.size() > 0) {

                     //   ClipboardManager cm = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        Bundle arguments = new Bundle();
                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,mMoments);
                        AccessibilityNodeInfo edtView = edt.get(0);

                        edtView.performAction(AccessibilityNodeInfo.FOCUS_INPUT);
                        edtView.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                    }

                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        isAssistantRunning = true;
    }
}
