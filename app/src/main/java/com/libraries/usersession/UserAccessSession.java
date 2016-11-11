package com.libraries.usersession;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserAccessSession {

	private SharedPreferences sharedPref;
	private Editor editor;

	private static final String ID 				= "23vponrnkl32brlkn";
	private static final String HASH_LOGIN 		= "340bji4riwbnlrvas";
	private static final String FULL_NAME 		= "5b03i3ipbp3454LLK";
	private static final String EMAIL 			= "54690j945safnKNKI";
	private static final String IDENTITY_NUMBER	= "sadnka008adklasdk";
	private static final String PHONE_NUMBER	= "8dsfu99s121kn3jkk";

	private static final String FILTER_RADIUS_DISTANCE 		= "FILTER_RADIUS_DISTANCE";
	private static final String FILTER_RADIUS_DISTANCE_MAX 		= "FILTER_RADIUS_DISTANCE_MAX";

	
	private static final String SHARED = "UserAccessSession_Preferences";
	private static UserAccessSession instance;
	
	public static UserAccessSession getInstance(Context context) {
		if(instance == null)
			instance = new UserAccessSession(context);
		
		return instance;
	}

	public UserAccessSession(Context context) {
		sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
		editor = sharedPref.edit();
	}

	public void storeUserSession(UserSession session) {
		editor.putInt(ID, session.getId());
		editor.putString(HASH_LOGIN, session.getHash_login());
		editor.putString(FULL_NAME, session.getFull_name());
		editor.putString(EMAIL, session.getEmail());
		editor.putString(IDENTITY_NUMBER, session.getIdentity_number());
		editor.putString(PHONE_NUMBER, session.getPhone_number());
		editor.commit();
	}

	public void clearUserSession() {
		editor.putInt(ID, -1);
		editor.putString(HASH_LOGIN,null);
		editor.putString(FULL_NAME,null);
		editor.putString(EMAIL,null);
		editor.putString(IDENTITY_NUMBER,null);
		editor.putString(PHONE_NUMBER,null);
		editor.commit();
	}

	public Boolean isLoggedIn() {
		if(sharedPref == null)
			return false;

		return true;//sharedPref.getBoolean(IS_LOGIN, false);
	}

	public UserSession getUserSession() {
		int userId = sharedPref.getInt(ID, -1);
		if(userId <= 0)
			return null;
		
		UserSession session = new UserSession();
		session.setEmail( sharedPref.getString(EMAIL, null) );
		session.setFull_name(sharedPref.getString(FULL_NAME, null));
		session.setHash_login(sharedPref.getString(HASH_LOGIN, null));
		session.setPhone_number(sharedPref.getString(PHONE_NUMBER, null));
		session.setId(sharedPref.getInt(ID, -1));
		session.setIdentity_number(sharedPref.getString(IDENTITY_NUMBER, null));
		return session;
	}

	public int getFilterDistanceMax() {
		return  sharedPref.getInt(FILTER_RADIUS_DISTANCE_MAX, 0);
	}
}
