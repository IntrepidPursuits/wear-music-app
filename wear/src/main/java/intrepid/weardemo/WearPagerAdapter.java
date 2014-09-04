package intrepid.weardemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

public class WearPagerAdapter extends FragmentGridPagerAdapter {


    public WearPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(int i, int i2) {
        return null;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount(int i) {
        return 0;
    }
}
