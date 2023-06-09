// Generated by view binder compiler. Do not edit!
package com.example.easify.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.easify.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button forgotPassword;

  @NonNull
  public final ImageView googleAuth;

  @NonNull
  public final AppCompatButton loginBtn;

  @NonNull
  public final TextView logoName;

  @NonNull
  public final TextInputLayout password;

  @NonNull
  public final ProgressBar progress1;

  @NonNull
  public final Button signupView;

  @NonNull
  public final TextView sloganName;

  @NonNull
  public final TextInputLayout username;

  private ActivityLoginBinding(@NonNull LinearLayout rootView, @NonNull Button forgotPassword,
      @NonNull ImageView googleAuth, @NonNull AppCompatButton loginBtn, @NonNull TextView logoName,
      @NonNull TextInputLayout password, @NonNull ProgressBar progress1, @NonNull Button signupView,
      @NonNull TextView sloganName, @NonNull TextInputLayout username) {
    this.rootView = rootView;
    this.forgotPassword = forgotPassword;
    this.googleAuth = googleAuth;
    this.loginBtn = loginBtn;
    this.logoName = logoName;
    this.password = password;
    this.progress1 = progress1;
    this.signupView = signupView;
    this.sloganName = sloganName;
    this.username = username;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.forgotPassword;
      Button forgotPassword = ViewBindings.findChildViewById(rootView, id);
      if (forgotPassword == null) {
        break missingId;
      }

      id = R.id.googleAuth;
      ImageView googleAuth = ViewBindings.findChildViewById(rootView, id);
      if (googleAuth == null) {
        break missingId;
      }

      id = R.id.loginBtn;
      AppCompatButton loginBtn = ViewBindings.findChildViewById(rootView, id);
      if (loginBtn == null) {
        break missingId;
      }

      id = R.id.logo_name;
      TextView logoName = ViewBindings.findChildViewById(rootView, id);
      if (logoName == null) {
        break missingId;
      }

      id = R.id.password;
      TextInputLayout password = ViewBindings.findChildViewById(rootView, id);
      if (password == null) {
        break missingId;
      }

      id = R.id.progress1;
      ProgressBar progress1 = ViewBindings.findChildViewById(rootView, id);
      if (progress1 == null) {
        break missingId;
      }

      id = R.id.signupView;
      Button signupView = ViewBindings.findChildViewById(rootView, id);
      if (signupView == null) {
        break missingId;
      }

      id = R.id.slogan_name;
      TextView sloganName = ViewBindings.findChildViewById(rootView, id);
      if (sloganName == null) {
        break missingId;
      }

      id = R.id.username;
      TextInputLayout username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivityLoginBinding((LinearLayout) rootView, forgotPassword, googleAuth, loginBtn,
          logoName, password, progress1, signupView, sloganName, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
