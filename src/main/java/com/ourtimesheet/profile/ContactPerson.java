package com.ourtimesheet.profile;

/**
 * Created by Abdus Salam on 3/14/2016.
 */
public class ContactPerson {

  private final String firstName;
  private final String lastName;
  private final String phone;
  private final String fax;

  public ContactPerson(String firstName, String lastName, String phone, String fax) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.fax = fax;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhone() {
    return phone;
  }

  public String getFax() {
    return fax;
  }

}
