package com.ourtimesheet.profile;

/**
 * Created by zohaib on 31/10/2016.
 */
public class RawSignUpData {

  private final String companyName;
  private final String companyDomain;
  private final String companyTimezone;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final String password;
  private final String phone;
  private final String openId;
  private final String realmId;

  public RawSignUpData(String companyName, String companyDomain, String companyTimezone, String email, String firstName, String lastName, String password, String phone, String openId, String realmId) {
    this.companyName = companyName;
    this.companyDomain = companyDomain;
    this.companyTimezone = companyTimezone;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.phone = phone;
    this.openId = openId;
    this.realmId = realmId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getCompanyDomain() {
    return companyDomain;
  }

  public String getCompanyTimezone() {
    return companyTimezone;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public String getOpenId() {
    return openId;
  }

  public String getRealmId() {
    return realmId;
  }
}

