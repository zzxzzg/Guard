package com.zzxzzg.rxpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    CheckBox foo1Checkbox;
    CheckBox foo2Checkbox;
    Preference<Boolean> fooPreference;
    CompositeSubscription subscriptions;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Views
        setContentView(R.layout.activity_main);
        foo1Checkbox= (CheckBox) findViewById(R.id.foo_1);
        foo2Checkbox= (CheckBox) findViewById(R.id.foo_2);

        // Preferences
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

        // foo
        fooPreference = rxPreferences.getBoolean("foo");


        Preference<Bean> pBean = rxPreferences.getObject("haha",new GsonPreferenceAdapter<Bean>(Bean.class));
    }

    @Override protected void onResume() {
        super.onResume();

        subscriptions = new CompositeSubscription();
        bindPreference(foo1Checkbox, fooPreference);
        bindPreference(foo2Checkbox, fooPreference);
    }

    @Override protected void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }

    //as action相当于设置 preference asObservable相当于get值
    void bindPreference(CheckBox checkBox, Preference<Boolean> preference) {
        // Bind the preference to the checkbox.
        subscriptions.add(preference.asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxCompoundButton.checked(checkBox)));
        // Bind the checkbox to the preference.
        subscriptions.add(RxCompoundButton.checkedChanges(checkBox)
                .skip(1)
                .subscribe(preference.asAction()));
    }
}
