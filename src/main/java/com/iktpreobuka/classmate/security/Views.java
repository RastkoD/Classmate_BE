package com.iktpreobuka.classmate.security;

public class Views {
	
	public static class PrivateView{};
	public static class StudentView extends PrivateView{};
	public static class GuardianView extends StudentView{};
	public static class TeacherView extends GuardianView{};
	public static class AdminView extends TeacherView{};
}
