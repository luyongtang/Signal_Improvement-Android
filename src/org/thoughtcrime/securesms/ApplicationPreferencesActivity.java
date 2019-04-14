/*
 * Copyright (C) 2011 Whisper Systems
 * Copyright (C) 2013-2017 Open Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.thoughtcrime.securesms;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.preference.Preference;
import android.view.MenuItem;

import org.thoughtcrime.securesms.preferences.AdvancedPreferenceFragment;
import org.thoughtcrime.securesms.preferences.AnalyticsPreferenceFragment;
import org.thoughtcrime.securesms.preferences.AppProtectionPreferenceFragment;
import org.thoughtcrime.securesms.preferences.AppearancePreferenceFragment;
import org.thoughtcrime.securesms.preferences.ChatsPreferenceFragment;
import org.thoughtcrime.securesms.preferences.CorrectedPreferenceFragment;
import org.thoughtcrime.securesms.preferences.CustomizationPreferenceFragment;
import org.thoughtcrime.securesms.preferences.NotificationsPreferenceFragment;
import org.thoughtcrime.securesms.preferences.SmsMmsPreferenceFragment;
import org.thoughtcrime.securesms.preferences.widgets.ProfilePreference;
import org.thoughtcrime.securesms.service.KeyCachingService;
import org.thoughtcrime.securesms.util.DynamicBackground;
import org.thoughtcrime.securesms.util.DynamicLanguage;
import org.thoughtcrime.securesms.util.DynamicTheme;
import org.thoughtcrime.securesms.util.TextSecurePreferences;


/**
 * The Activity for application preference display and management.
 *
 * @author Moxie Marlinspike
 *
 */

public class ApplicationPreferencesActivity extends PassphraseRequiredActionBarActivity
    implements SharedPreferences.OnSharedPreferenceChangeListener
{
  @SuppressWarnings("unused")
  private static final String TAG = ApplicationPreferencesActivity.class.getSimpleName();
  private static final String PREFERENCE_CATEGORY_PROFILE        = "preference_category_profile";
  private static final String PREFERENCE_CATEGORY_ANALYTICS      = "preference_category_analytics";
  private static final String PREFERENCE_CATEGORY_CUSTOMIZATION  = "preference_category_customization";
  private static final String PREFERENCE_CATEGORY_SMS_MMS        = "preference_category_sms_mms";
  private static final String PREFERENCE_CATEGORY_NOTIFICATIONS  = "preference_category_notifications";
  private static final String PREFERENCE_CATEGORY_APP_PROTECTION = "preference_category_app_protection";
  private static final String PREFERENCE_CATEGORY_APPEARANCE     = "preference_category_appearance";
  private static final String PREFERENCE_CATEGORY_CHATS          = "preference_category_chats";
  private static final String PREFERENCE_CATEGORY_DEVICES        = "preference_category_devices";
  private static final String PREFERENCE_CATEGORY_ADVANCED       = "preference_category_advanced";

  private final DynamicTheme    dynamicTheme    = new DynamicTheme();
  private final DynamicLanguage dynamicLanguage = new DynamicLanguage();
  private final DynamicBackground dynamicBackground = new DynamicBackground();

  @Override
  protected void onPreCreate() {
    dynamicTheme.onCreate(this);
    dynamicLanguage.onCreate(this);
    dynamicBackground.onCreate(this);
  }

  @Override
  protected void onCreate(Bundle icicle, boolean ready) {
    //noinspection ConstantConditions
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.application_preference_activity);

    BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.application_preference_navigation_bar);

    navigationView.setSelectedItemId(R.id.navigation_bar_settings);

    navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
          case R.id.navigation_bar_call_logs: {
            Intent intent = new Intent(ApplicationPreferencesActivity.this, CallLogsActivity.class);
            startActivity(intent);
            ApplicationPreferencesActivity.this.overridePendingTransition(R.anim.stationary, R.anim.stationary);
            break;
          }
          case R.id.navigation_bar_chats: {
            Intent intent = new Intent(ApplicationPreferencesActivity.this, ConversationListActivity.class);
            startActivity(intent);
            ApplicationPreferencesActivity.this.overridePendingTransition(R.anim.stationary, R.anim.stationary);
            break;
          }
          default: {
            Intent intent = new Intent(ApplicationPreferencesActivity.this, ApplicationPreferencesActivity.class);
            startActivity(intent);
            ApplicationPreferencesActivity.this.overridePendingTransition(R.anim.stationary, R.anim.stationary);
            break;
          }
        }
        return true;
      }
    });

    if (getIntent() != null && getIntent().getCategories() != null && getIntent().getCategories().contains("android.intent.category.NOTIFICATION_PREFERENCES")) {
      initFragment(android.R.id.content, new NotificationsPreferenceFragment());
    } else if (icicle == null) {
      initFragment(R.id.preferences_fragment, new ApplicationPreferenceFragment());
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    dynamicTheme.onResume(this);
    dynamicLanguage.onResume(this);
    dynamicBackground.onResume(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.preferences_fragment);
    fragment.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public boolean onSupportNavigateUp() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if (fragmentManager.getBackStackEntryCount() > 0) {
      fragmentManager.popBackStack();
    } else {
      Intent intent = new Intent(this, ConversationListActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      overridePendingTransition(R.anim.stationary, R.anim.stationary);
      finish();
    }
    return true;
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(TextSecurePreferences.THEME_PREF)) {
      recreate();
    } else if (key.equals(TextSecurePreferences.LANGUAGE_PREF)) {
      recreate();

      Intent intent = new Intent(this, KeyCachingService.class);
      intent.setAction(KeyCachingService.LOCALE_CHANGE_EVENT);
      startService(intent);
    }
  }

  @Override
  public void onBackPressed() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if (fragmentManager.getBackStackEntryCount() > 0) {
      fragmentManager.popBackStack();
    } else {
      Intent intent = new Intent(this, ConversationListActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      overridePendingTransition(R.anim.stationary, R.anim.stationary);
      finish();
    }
  }

  public static class ApplicationPreferenceFragment extends CorrectedPreferenceFragment {

    @Override
    public void onCreate(Bundle icicle) {
      super.onCreate(icicle);

      this.findPreference(PREFERENCE_CATEGORY_PROFILE)
          .setOnPreferenceClickListener(new ProfileClickListener());
      this.findPreference(PREFERENCE_CATEGORY_ANALYTICS)
          .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_ANALYTICS));
      this.findPreference(PREFERENCE_CATEGORY_CUSTOMIZATION)
          .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_CUSTOMIZATION));
      this.findPreference(PREFERENCE_CATEGORY_SMS_MMS)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_SMS_MMS));
      this.findPreference(PREFERENCE_CATEGORY_NOTIFICATIONS)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_NOTIFICATIONS));
      this.findPreference(PREFERENCE_CATEGORY_APP_PROTECTION)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_APP_PROTECTION));
      this.findPreference(PREFERENCE_CATEGORY_APPEARANCE)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_APPEARANCE));
      this.findPreference(PREFERENCE_CATEGORY_CHATS)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_CHATS));
      this.findPreference(PREFERENCE_CATEGORY_DEVICES)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_DEVICES));
      this.findPreference(PREFERENCE_CATEGORY_ADVANCED)
        .setOnPreferenceClickListener(new CategoryClickListener(PREFERENCE_CATEGORY_ADVANCED));

      if (VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        tintIcons(getActivity());
      }
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
      addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
      super.onResume();
      //noinspection ConstantConditions
      ((ApplicationPreferencesActivity) getActivity()).getSupportActionBar().setTitle(R.string.text_secure_normal__menu_settings);
      setCategorySummaries();
      setCategoryVisibility();
    }

    private void setCategorySummaries() {
      ((ProfilePreference)this.findPreference(PREFERENCE_CATEGORY_PROFILE)).refresh();
      this.findPreference(PREFERENCE_CATEGORY_ANALYTICS)
          .setSummary(AnalyticsPreferenceFragment.getSummary(getActivity()));
      this.findPreference(PREFERENCE_CATEGORY_CUSTOMIZATION)
          .setSummary(CustomizationPreferenceFragment.getSummary(getActivity()));
      this.findPreference(PREFERENCE_CATEGORY_SMS_MMS)
          .setSummary(SmsMmsPreferenceFragment.getSummary(getActivity()));
      this.findPreference(PREFERENCE_CATEGORY_NOTIFICATIONS)
          .setSummary(NotificationsPreferenceFragment.getSummary(getActivity()));
      this.findPreference(PREFERENCE_CATEGORY_APP_PROTECTION)
          .setSummary(AppProtectionPreferenceFragment.getSummary(getActivity()));
      this.findPreference(PREFERENCE_CATEGORY_APPEARANCE)
          .setSummary(AppearancePreferenceFragment.getSummary(getActivity()));
      this.findPreference(PREFERENCE_CATEGORY_CHATS)
          .setSummary(ChatsPreferenceFragment.getSummary(getActivity()));
    }

    private void setCategoryVisibility() {
      Preference devicePreference = this.findPreference(PREFERENCE_CATEGORY_DEVICES);
      if (devicePreference != null && !TextSecurePreferences.isPushRegistered(getActivity())) {
        getPreferenceScreen().removePreference(devicePreference);
      }
    }

    @TargetApi(11)
    private void tintIcons(Context context) {
      Drawable analytics     = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.analytics_report));
      Drawable customization = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.star));
      Drawable sms           = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_textsms_white_24dp));
      Drawable notifications = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_notifications_white_24dp));
      Drawable privacy       = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_security_white_24dp));
      Drawable appearance    = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_brightness_6_white_24dp));
      Drawable chats         = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_forum_white_24dp));
      Drawable devices       = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_laptop_white_24dp));
      Drawable advanced      = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_advanced_white_24dp));

      int[]      tintAttr   = new int[]{R.attr.pref_icon_tint};
      TypedArray typedArray = context.obtainStyledAttributes(tintAttr);
      int        color      = typedArray.getColor(0, 0x0);
      typedArray.recycle();

      DrawableCompat.setTint(analytics, color);
      DrawableCompat.setTint(customization, color);
      DrawableCompat.setTint(sms, color);
      DrawableCompat.setTint(notifications, color);
      DrawableCompat.setTint(privacy, color);
      DrawableCompat.setTint(appearance, color);
      DrawableCompat.setTint(chats, color);
      DrawableCompat.setTint(devices, color);
      DrawableCompat.setTint(advanced, color);

      this.findPreference(PREFERENCE_CATEGORY_ANALYTICS).setIcon(analytics);
      this.findPreference(PREFERENCE_CATEGORY_CUSTOMIZATION).setIcon(customization);
      this.findPreference(PREFERENCE_CATEGORY_SMS_MMS).setIcon(sms);
      this.findPreference(PREFERENCE_CATEGORY_NOTIFICATIONS).setIcon(notifications);
      this.findPreference(PREFERENCE_CATEGORY_APP_PROTECTION).setIcon(privacy);
      this.findPreference(PREFERENCE_CATEGORY_APPEARANCE).setIcon(appearance);
      this.findPreference(PREFERENCE_CATEGORY_CHATS).setIcon(chats);
      this.findPreference(PREFERENCE_CATEGORY_DEVICES).setIcon(devices);
      this.findPreference(PREFERENCE_CATEGORY_ADVANCED).setIcon(advanced);
    }

    private class CategoryClickListener implements Preference.OnPreferenceClickListener {
      private String category;

      CategoryClickListener(String category) {
        this.category = category;
      }

      @Override
      public boolean onPreferenceClick(Preference preference) {
        Fragment fragment = null;

        switch (category) {
        case PREFERENCE_CATEGORY_ANALYTICS:
          fragment = new AnalyticsPreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_CUSTOMIZATION:
          fragment = new CustomizationPreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_SMS_MMS:
          fragment = new SmsMmsPreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_NOTIFICATIONS:
          fragment = new NotificationsPreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_APP_PROTECTION:
          fragment = new AppProtectionPreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_APPEARANCE:
          fragment = new AppearancePreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_CHATS:
          fragment = new ChatsPreferenceFragment();
          break;
        case PREFERENCE_CATEGORY_DEVICES:
          Intent intent = new Intent(getActivity(), DeviceActivity.class);
          startActivity(intent);
          break;
        case PREFERENCE_CATEGORY_ADVANCED:
          fragment = new AdvancedPreferenceFragment();
          break;
        default:
          throw new AssertionError();
        }

        if (fragment != null) {
          Bundle args = new Bundle();
          fragment.setArguments(args);

          FragmentManager     fragmentManager     = getActivity().getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.replace(R.id.preferences_fragment, fragment);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
        }

        return true;
      }
    }

    private class ProfileClickListener implements Preference.OnPreferenceClickListener {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        Intent intent = new Intent(preference.getContext(), CreateProfileActivity.class);
        intent.putExtra(CreateProfileActivity.EXCLUDE_SYSTEM, true);

        getActivity().startActivity(intent);
//        ((BaseActionBarActivity)getActivity()).startActivitySceneTransition(intent, getActivity().findViewById(R.id.avatar), "avatar");
        return true;
      }
    }
  }

}
