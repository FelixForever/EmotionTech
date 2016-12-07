package felix.lib.Base.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import felix.lib.Base.App.BaseApp;

/**
 * Created by felix on 11/10/2016.
 */

public class ViewUtil
{
    private static Context sContext;
    private static Resources sResources;

    public static void init(Context context) {
        sContext = context;
        sResources = sContext.getResources();
    }

    public static Drawable getDrawableById(int id) {
        if (sContext == null) {
            sContext = BaseApp.getContext();
        }
        if (Build.VERSION.SDK_INT > 19) {
            return sContext.getResources().getDrawable(id, sContext.getTheme());
        } else {
            return sContext.getDrawable(id);
        }
    }

    public interface OkClickListenner
    {
        void onClick(DialogInterface dialog, int which, String str);
    }

    public static void showInputDialog(Context context, String defStr, OkClickListenner okClickListenner) {

        TextInputLayout textInputLayout = new TextInputLayout(context);
        EditText editText = new EditText(context);
        editText.setHint("请输入文字");
        editText.setText(defStr);
        int padding = DentisityUtil.dp2px(8);
        editText.setPadding(padding, padding, padding, padding);
        textInputLayout.addView(editText);
        AlertDialog.Builder ab = new AlertDialog.Builder(context)
                .setView(textInputLayout)
                .setPositiveButton("确定", (dialog, which) -> {
                    if (okClickListenner != null) {
                        okClickListenner.onClick(dialog, which, textInputLayout.getEditText().getText().toString());
                    }
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.cancel();
                });
        ab.show();
    }
}
