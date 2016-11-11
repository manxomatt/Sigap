package com.akhbra.panicbutton;

import android.content.Context;
import android.content.SharedPreferences;

public class Store_Pref {
	SharedPreferences pref_master;
	SharedPreferences.Editor editor_pref_master;
	Context c;

	public Store_Pref(Context con) {
		c = con;
		pref_master = con.getSharedPreferences("App_pref", 0);
	}

	public void setClickAlert(){

	}

	public void open_editor() {
		// TODO Auto-generated method stub
		editor_pref_master = pref_master.edit();
	}

	// ...message
	public void set_message_sender_name(String name) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("message_sender_name", name);
		editor_pref_master.commit();
	}

	public String get_message_sender_name() {
		// TODO Auto-generated method stub
		return pref_master.getString("message_sender_name", "");
	}

	public void set_message_content(String content) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("message_content", content);
		editor_pref_master.commit();
	}

	public String get_message_content() {
		// TODO Auto-generated method stub
		return pref_master.getString("message_content", "");
	}

	// ...power button click
	public void set_power_button_click(int click) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putInt("power_button_click", click);
		editor_pref_master.commit();
	}

	public int get_power_button_click() {
		// TODO Auto-generated method stub
		return pref_master.getInt("power_button_click", 0);
	}

	public void set_power_button_enable(Boolean enable) {
		open_editor();
		editor_pref_master.putBoolean("button_enable", enable);
		editor_pref_master.commit();
	}

	public boolean get_power_button_enable() {
		return pref_master.getBoolean("button_enable", true);
	}

	public void set_time(Long mTime) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putLong("_time", mTime);
		editor_pref_master.commit();
	}

	public long get_time() {
		// TODO Auto-generated method stub
		long b = pref_master.getLong("_time", 5);
		return b;

	}
	
	public void set_Count(int count) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putInt("_Count", count);
		editor_pref_master.commit();
	}

	public int get_Count() {
		// TODO Auto-generated method stub
		int b = pref_master.getInt("_Count", 0);
		return b;

	}
	
	// ...contact list
	public void set_contact_count(String count) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact_count", count);
		editor_pref_master.commit();
	}

	public String get_contact_count() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact_count", "0");
	}

	public void set_contact1(String contact1) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact1", contact1);
		editor_pref_master.commit();
	}

	public String get_contact1() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact1", "");
	}

	public void set_contact1_number(String contact1) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact1_number", contact1);
		editor_pref_master.commit();
	}

	public String get_contact1_number() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact1_number", "");
	}

	public void set_contact2(String contact2) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact2", contact2);
		editor_pref_master.commit();
	}

	public String get_contact2() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact2", "");
	}

	public void set_contact2_number(String contact2) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact2_number", contact2);
		editor_pref_master.commit();
	}

	public String get_contact2_number() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact2_number", "");
	}

	public void set_contact3(String contact3) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact3", contact3);
		editor_pref_master.commit();
	}

	public String get_contact3() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact3", "");
	}

	public void set_contact3_number(String contact3) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact3_number", contact3);
		editor_pref_master.commit();
	}

	public String get_contact3_number() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact3_number", "");
	}

	public void set_contact4(String contact4) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact4", contact4);
		editor_pref_master.commit();
	}

	public String get_contact4() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact4", "");
	}

	public void set_contact4_number(String contact4) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact4_number", contact4);
		editor_pref_master.commit();
	}

	public String get_contact4_number() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact4_number", "");
	}

	public void set_contact5(String contact5) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact5", contact5);
		editor_pref_master.commit();
	}

	public String get_contact5() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact5", "");
	}

	public void set_contact5_number(String contact5) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("contact5_number", contact5);
		editor_pref_master.commit();
	}

	public String get_contact5_number() {
		// TODO Auto-generated method stub
		return pref_master.getString("contact5_number", "");
	}

	public void set_alert_id(String id){
		open_editor();
		editor_pref_master.putString("alert_id",id);
		editor_pref_master.commit();
	}

	public String get_alert_id(){
		return pref_master.getString("alert_id","");
	}

	public void set_member_id(String id){
		open_editor();
		editor_pref_master.putString("member_id",id);
		editor_pref_master.commit();
	}

	public String get_member_id(){
		return pref_master.getString("member_id","");
	}

	public void set_member_name(String name){
		open_editor();
		editor_pref_master.putString("member_name",name);
		editor_pref_master.commit();
	}

	public String get_member_name(){
		return pref_master.getString("member_name","");
	}

	public void set_member_phone(String phone){
		open_editor();
		editor_pref_master.putString("member_phone",phone);
		editor_pref_master.commit();
	}

	public String get_member_phone(){
		return pref_master.getString("member_phone","");
	}

	public void set_member_email(String email){
		open_editor();
		editor_pref_master.putString("member_email",email);
		editor_pref_master.commit();
	}

	public String get_member_email(){
		return pref_master.getString("member_email","");
	}

}
