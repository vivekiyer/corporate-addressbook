package net.vivekiyer.GAL;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created with IntelliJ IDEA.
 * User: Dan
 * Date: 2013-01-14
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
public class ContactFragmentPager extends ViewPager {
	public ContactFragmentPager(Context context) {
		super(context);
	}

	public ContactFragmentPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();    //To change body of overridden methods use File | Settings | File Templates.
		setPageMargin(Utility.dip2Pixels(App.getInstance(), 3));
	}
}
