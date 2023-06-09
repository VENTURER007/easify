// Generated by view binder compiler. Do not edit!
package com.example.easify.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.easify.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button SignupBtn;

  @NonNull
  public final ProgressBar progress2;

  @NonNull
  public final TextInputLayout regEmail;

  @NonNull
  public final TextInputLayout regName;

  @NonNull
  public final TextInputLayout regPassword;

  @NonNull
  public final TextInputLayout regPhoneNo;

  private ActivitySignupBinding(@NonNull LinearLayout rootView, @NonNull Button SignupBtn,
      @NonNull ProgressBar progress2, @NonNull TextInputLayout regEmail,
      @NonNull TextInputLayout regName, @NonNull TextInputLayout regPassword,
      @NonNull TextInputLayout regPhoneNo) {
    this.rootView = rootView;
    this.SignupBtn = SignupBtn;
    this.progress2 = progress2;
    this.regEmail = regEmail;
    this.regName = regName;
    this.regPassword = regPassword;
    this.regPhoneNo = regPhoneNo;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.SignupBtn;
      Button SignupBtn = ViewBindings.findChildViewById(rootView, id);
      if (SignupBtn == null) {
        break missingId;
      }

      id = R.id.progress2;
      ProgressBar progress2 = ViewBindings.findChildViewById(rootView, id);
      if (progress2 == null) {
        break missingId;
      }

      id = R.id.reg_email;
      TextInputLayout regEmail = ViewBindings.findChildViewById(rootView, id);
      if (regEmail == null) {
        break missingId;
      }

      id = R.id.reg_name;
      TextInputLayout regName = ViewBindings.findChildViewById(rootView, id);
      if (regName == null) {
        break missingId;
      }

      id = R.id.reg_password;
      TextInputLayout regPassword = ViewBindings.findChildViewById(rootView, id);
      if (regPassword == null) {
        break missingId;
      }

      id = R.id.reg_phoneNo;
      TextInputLayout regPhoneNo = ViewBindings.findChildViewById(rootView, id);
      if (regPhoneNo == null) {
        break missingId;
      }

      return new ActivitySignupBinding((LinearLayout) rootView, SignupBtn, progress2, regEmail,
          regName, regPassword, regPhoneNo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
