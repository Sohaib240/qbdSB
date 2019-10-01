package com.ourtimesheet.notification.domain;

/**
 * Created by hassan on 10/6/16.
 */
public class Email {

  private final String from;

  private final String to;

  private final String body;

  private final String subject;

  public Email(String from, String to, String subject, String body) {
    this.from = from;
    this.to = to;
    this.body = body;
    this.subject = subject;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getBody() {
    return body;
  }

  public String getSubject() {
    return subject;
  }
}
