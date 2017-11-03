package org.onvif.unofficial.services;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.onvif.unofficial.OnvifDevice;
import org.onvif.unofficial.soapclient.ISoapClient;
import org.onvif.ver10.device.wsdl.GetCapabilities;
import org.onvif.ver10.device.wsdl.GetCapabilitiesResponse;
import org.onvif.ver10.device.wsdl.GetDeviceInformation;
import org.onvif.ver10.device.wsdl.GetDeviceInformationResponse;
import org.onvif.ver10.device.wsdl.GetHostname;
import org.onvif.ver10.device.wsdl.GetHostnameResponse;
import org.onvif.ver10.device.wsdl.GetScopes;
import org.onvif.ver10.device.wsdl.GetScopesResponse;
import org.onvif.ver10.device.wsdl.GetServices;
import org.onvif.ver10.device.wsdl.GetServicesResponse;
import org.onvif.ver10.device.wsdl.GetSystemDateAndTime;
import org.onvif.ver10.device.wsdl.GetSystemDateAndTimeResponse;
import org.onvif.ver10.device.wsdl.GetUsers;
import org.onvif.ver10.device.wsdl.GetUsersResponse;
import org.onvif.ver10.device.wsdl.Service;
import org.onvif.ver10.device.wsdl.SetHostname;
import org.onvif.ver10.device.wsdl.SetHostnameResponse;
import org.onvif.ver10.device.wsdl.SystemReboot;
import org.onvif.ver10.device.wsdl.SystemRebootResponse;
import org.onvif.ver10.schema.Capabilities;
import org.onvif.ver10.schema.Date;
import org.onvif.ver10.schema.Scope;
import org.onvif.ver10.schema.Time;
import org.onvif.ver10.schema.User;

public class DeviceManagementService extends AbstractService{

	public DeviceManagementService(OnvifDevice onvifDevice, ISoapClient client, String serviceUrl) {
		super(onvifDevice, client, serviceUrl);
	}

	public java.util.Date getDate() throws Exception {
		GetSystemDateAndTimeResponse response = client.processRequest(new GetSystemDateAndTime(),
				GetSystemDateAndTimeResponse.class, serviceUrl, true);
		Date date = response.getSystemDateAndTime().getUTCDateTime().getDate();
		Time time = response.getSystemDateAndTime().getUTCDateTime().getTime();
		Calendar cal = new GregorianCalendar(date.getYear(), date.getMonth() - 1, date.getDay(), time.getHour(),
				time.getMinute(), time.getSecond());
		return cal.getTime();
	}

	public GetDeviceInformationResponse getDeviceInformation() throws Exception {
		return client.processRequest(new GetDeviceInformation(), GetDeviceInformationResponse.class, serviceUrl,
				true);
	}

	public String getHostname() throws Exception {
		GetHostnameResponse response = client.processRequest(new GetHostname(), GetHostnameResponse.class,
				serviceUrl, true);
		return response.getHostnameInformation().getName();
	}

	public void setHostname(String hostname) throws Exception {
		SetHostname request = new SetHostname();
		request.setName(hostname);
		client.processRequest(request, SetHostnameResponse.class, serviceUrl, true);
	}

	public List<User> getUsers() throws Exception {
		GetUsersResponse response = client.processRequest(new GetUsers(), GetUsersResponse.class, serviceUrl, true);
		return response.getUser();
	}

	public Capabilities getCapabilities() throws Exception {
		GetCapabilitiesResponse response = client.processRequest(new GetCapabilities(), GetCapabilitiesResponse.class,
				serviceUrl, false);
		return response.getCapabilities();
	}

	public List<Service> getServices(boolean includeCapability) throws Exception {
		GetServices request = new GetServices();
		request.setIncludeCapability(includeCapability);
		GetServicesResponse response = client.processRequest(request, GetServicesResponse.class, serviceUrl, true);
		if (response == null) {
			return null;
		}
		return response.getService();
	}

	public List<Scope> getScopes() throws Exception {
		GetScopesResponse response = client.processRequest(new GetScopes(), GetScopesResponse.class, serviceUrl,
				true);
		if (response == null) {
			return null;
		}
		return response.getScopes();
	}

	public String reboot() throws Exception {
		SystemRebootResponse response = client.processRequest(new SystemReboot(), SystemRebootResponse.class,
				serviceUrl, true);
		if (response == null) {
			return null;
		}
		return response.getMessage();
	}
}
