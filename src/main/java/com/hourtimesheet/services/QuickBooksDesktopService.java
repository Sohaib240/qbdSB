package com.hourtimesheet.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by Abdus Salam on 1/23/2017.
 */
@WebService
public interface QuickBooksDesktopService {

    @WebMethod(action = "http://developer.intuit.com/authenticate")
    @WebResult(name = "authenticateResult", targetNamespace = "http://developer.intuit.com/")
    @RequestWrapper(localName = "authenticate", targetNamespace = "http://developer.intuit.com/" , className ="com.hourtimesheet.services.Authenticate" )
    @ResponseWrapper(localName = "authenticateResponse", targetNamespace = "http://developer.intuit.com/", className = "com.hourtimesheet.services.AuthenticateResponse")
    ArrayOfString authenticate(@WebParam(name = "strUserName", targetNamespace = "http://developer.intuit.com/") String strUserName, @WebParam(name = "strPassword", targetNamespace = "http://developer.intuit.com/") String strPassword);

    @WebMethod(action = "http://developer.intuit.com/clientVersion")
    @RequestWrapper(localName = "clientVersion", targetNamespace = "http://developer.intuit.com/")
    @ResponseWrapper(localName = "clientVersionResponse", targetNamespace = "http://developer.intuit.com/")
    String clientVersion(@WebParam(name = "strVersion", targetNamespace = "http://developer.intuit.com/") String strVersion);

    @WebMethod(action = "http://developer.intuit.com/serverVersion")
    @RequestWrapper(localName = "serverVersion", targetNamespace = "http://developer.intuit.com/")
    @ResponseWrapper(localName = "serverVersionResponse", targetNamespace = "http://developer.intuit.com/")
    String serverVersion(@WebParam(name = "ticket", targetNamespace = "http://developer.intuit.com/") String ticket);

    @WebMethod(action = "http://developer.intuit.com/closeConnection")
    @RequestWrapper(localName = "closeConnection", targetNamespace = "http://developer.intuit.com/")
    @ResponseWrapper(localName = "closeConnectionResponse", targetNamespace = "http://developer.intuit.com/")
    String closeConnection(@WebParam(name = "ticket", targetNamespace = "http://developer.intuit.com/") String ticket);

    @WebMethod(action = "http://developer.intuit.com/connectionError")
    @RequestWrapper(localName = "connectionError", targetNamespace = "http://developer.intuit.com/", className ="com.hourtimesheet.services.ConnectionError")
    @ResponseWrapper(localName = "connectionErrorResponse", targetNamespace = "http://developer.intuit.com/", className ="com.hourtimesheet.services.ConnectionErrorResponse" )
    String connectionError(String ticket, String hresult, String message);

    @WebMethod(action = "http://developer.intuit.com/getLastError")
    @RequestWrapper(localName = "getLastError", targetNamespace = "http://developer.intuit.com/")
    @ResponseWrapper(localName = "getLastErrorResponse", targetNamespace = "http://developer.intuit.com/")
    String getLastError(@WebParam(name = "ticket", targetNamespace = "http://developer.intuit.com/") String ticket);

    String getInteractiveURL(String wcTicket, String sessionID);
    String interactiveDone(String wcTicket);
    String interactiveRejected(String wcTicket, String reason);

    @WebMethod(action = "http://developer.intuit.com/receiveResponseXML")
    @WebResult(name = "receiveResponseXMLResult", targetNamespace = "http://developer.intuit.com/")
    @RequestWrapper(localName = "receiveResponseXML", targetNamespace = "http://developer.intuit.com/")
    @ResponseWrapper(localName = "receiveResponseXMLResponse", targetNamespace = "http://developer.intuit.com/")
    int receiveResponseXML(
            @WebParam(name = "ticket", targetNamespace = "http://developer.intuit.com/") String ticket,
            @WebParam(name = "response", targetNamespace = "http://developer.intuit.com/") String response,
            @WebParam(name = "hresult", targetNamespace = "http://developer.intuit.com/") String hresult,
            @WebParam(name = "message", targetNamespace = "http://developer.intuit.com/") String message);

    @WebMethod(action = "http://developer.intuit.com/sendRequestXML")
    @WebResult(name = "sendRequestXMLResult", targetNamespace = "http://developer.intuit.com/")
    @RequestWrapper(localName = "sendRequestXML", targetNamespace = "http://developer.intuit.com/", className ="com.hourtimesheet.services.SendRequestXML")
    @ResponseWrapper(localName = "sendRequestXMLResponse", targetNamespace = "http://developer.intuit.com/", className ="com.hourtimesheet.services.SendRequestXMLResponse")
    String sendRequestXML(
            @WebParam(name = "ticket", targetNamespace = "http://developer.intuit.com/") String ticket,
            @WebParam(name = "strHCPResponse", targetNamespace = "http://developer.intuit.com/") String strHCPResponse,
            @WebParam(name = "strCompanyFileName", targetNamespace = "http://developer.intuit.com/") String strCompanyFileName,
            @WebParam(name = "qbXMLCountry", targetNamespace = "http://developer.intuit.com/") String qbXMLCountry,
            @WebParam(name = "qbXMLMajorVers", targetNamespace = "http://developer.intuit.com/") int qbXMLMajorVers,
            @WebParam(name = "qbXMLMinorVers", targetNamespace = "http://developer.intuit.com/") int qbXMLMinorVers);
}