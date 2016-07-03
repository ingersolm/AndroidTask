package orionhealth.ingersoll.com.androidtask.Utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by Ingersoll Mohan on 3/7/16.
 */
public class Utility {

    /** Returns boolean flag (true or false), whether the Phone is connected to an active network. */

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /** Returns boolean flag (true or false), whether the Phone is connected to Wifi network. */

    public static boolean isWifiConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    /** Returns boolean flag (true or false), whether the Phone is connected to Mobile network. */

    public static boolean isMobileConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE);
    }

    private static boolean isConnected(Context context, int type) {
        ConnectivityManager connMgr = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(type);
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return isConnected(connMgr, type);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean isConnected(ConnectivityManager connMgr, int type) {
        Network[] networks = connMgr.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connMgr.getNetworkInfo(mNetwork);
            if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()){
                return true;
            }
        }
        return false;
    }

    /**
     * Convenient function to show dialog with one button
     *
     * @param context
     *            - application context
     * @param msg
     *            - dialog message
     * @param title
     *            - dialog title message
     */

    public static void ShowDialogOneButton(Context context, String msg, String title) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
