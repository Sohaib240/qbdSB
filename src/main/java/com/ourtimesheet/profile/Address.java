package com.ourtimesheet.profile;

/**
 * Created by Abdus Salam on 3/14/2016.
 */
public class Address {

  private final String street;

  private final String city;

  private final String state;

  private final String zip;

  private final String country;

  public Address(String street, String city, String state, String zip, String country) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.country = country;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public String getCountry() {
    return country;
  }
}
