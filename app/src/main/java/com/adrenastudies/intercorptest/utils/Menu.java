package com.adrenastudies.intercorptest.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import com.adrenastudies.intercorptest.R;
import com.adrenastudies.intercorptest.adapters.DrawerAdapter;
import com.adrenastudies.moneelyte.models.DrawerMenu;

import java.util.ArrayList;

public class Menu {

    private static Activity activity;
    private static ListView listOptions;
    private static DrawerLayout drawerLayout;
    private static String reference;

    public Menu(Activity _activity, ListView _listOptions, DrawerLayout _drawerLayout, String _reference){
        activity     = _activity;
        listOptions  = _listOptions;
        drawerLayout = _drawerLayout;
        reference = _reference;
    }

    public static void open(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public static void close(){
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public static void setMenu() {

        final ArrayList<DrawerMenu> optDrawer = new ArrayList<>();

        optDrawer.add(new DrawerMenu(R.string.txt_opt_1, null));
        optDrawer.add(new DrawerMenu(R.string.txt_opt_2, null));
        optDrawer.add(new DrawerMenu(R.string.txt_opt_3, null));

        listOptions.setAdapter(new DrawerAdapter(activity, R.layout.node_menu_drawer, optDrawer) {
            @Override
            public void onEntry(Object data, View view) {

                TextView user = view.findViewById(R.id.txtItem);
                user.setText(((DrawerMenu) data).getText());

            }
        });

        listOptions.setOnItemClickListener((parent, view, position, id) -> {
            DrawerMenu item = (DrawerMenu) parent.getItemAtPosition(position);

            if (item.getText() == R.string.txt_opt_1) {
                if(reference.equals(Constants.MENU_HISTORY_CLIENT)){
                    Navigation.findNavController(view).navigate(R.id.action_history_client_to_add_clients);
                }
            }

            if (item.getText() == R.string.txt_opt_2) {
                if(reference.equals(Constants.MENU_ADD_CLIENT)){
                    Navigation.findNavController(view).navigate(R.id.action_add_clients_to_history_client);
                }
            }

            if (item.getText() == R.string.txt_opt_3) {
                Preferences.setIsLogged(Constants.FALSE);

                if(reference.equals(Constants.MENU_ADD_CLIENT)){
                    Navigation.findNavController(view).navigate(R.id.action_add_clients_to_login);
                }else{
                    Navigation.findNavController(view).navigate(R.id.action_history_clients_to_login);
                }
            }

        });
    }

}
