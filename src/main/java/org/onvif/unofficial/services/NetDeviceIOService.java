package org.onvif.unofficial.services;

import org.onvif.unofficial.NetOnvifDevice;
import org.onvif.unofficial.api.DeviceIOService;
import org.onvif.unofficial.soapclient.ISoapClient;
import org.onvif.ver10.schema.AbsoluteFocus;
import org.onvif.ver10.schema.FocusMove;
import org.onvif.ver10.schema.ImagingOptions20;
import org.onvif.ver10.schema.ImagingSettings20;
import org.onvif.ver20.imaging.wsdl.GetImagingSettings;
import org.onvif.ver20.imaging.wsdl.GetImagingSettingsResponse;
import org.onvif.ver20.imaging.wsdl.GetOptions;
import org.onvif.ver20.imaging.wsdl.GetOptionsResponse;
import org.onvif.ver20.imaging.wsdl.Move;
import org.onvif.ver20.imaging.wsdl.MoveResponse;
import org.onvif.ver20.imaging.wsdl.SetImagingSettings;
import org.onvif.ver20.imaging.wsdl.SetImagingSettingsResponse;

public class NetDeviceIOService extends ServiceBase implements DeviceIOService{


	public NetDeviceIOService(NetOnvifDevice onvifDevice, ISoapClient client, String serviceUrl) {
		super(onvifDevice, client, serviceUrl);
			}

	/* (non-Javadoc)
	 * @see org.onvif.unofficial.services.DeviceIOService#getOptions(java.lang.String)
	 */
	@Override
	public ImagingOptions20 getOptions(String videoSourceToken) throws Exception {
		if (videoSourceToken == null) {
			return null;
		}
		GetOptions request = new GetOptions();
		request.setVideoSourceToken(videoSourceToken);
		GetOptionsResponse response = client.processRequest(request, GetOptionsResponse.class, serviceUrl,
				true);
		if (response == null) {
			return null;
		}
		return response.getImagingOptions();
	}

	/* (non-Javadoc)
	 * @see org.onvif.unofficial.services.DeviceIOService#moveFocus(java.lang.String, float)
	 */
	@Override
	public boolean moveFocus(String videoSourceToken, float absoluteFocusValue) throws Exception {
		if (videoSourceToken == null) {
			return false;
		}
		AbsoluteFocus absoluteFocus = new AbsoluteFocus();
		absoluteFocus.setPosition(absoluteFocusValue);
		FocusMove focusMove = new FocusMove();
		focusMove.setAbsolute(absoluteFocus);
		Move request = new Move();
		request.setVideoSourceToken(videoSourceToken);
		request.setFocus(focusMove);
		client.processRequest(request, MoveResponse.class, serviceUrl, true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.onvif.unofficial.services.DeviceIOService#getImagingSettings(java.lang.String)
	 */
	@Override
	public ImagingSettings20 getImagingSettings(String videoSourceToken) throws Exception {
		if (videoSourceToken == null) {
			return null;
		}
		GetImagingSettings request = new GetImagingSettings();
		request.setVideoSourceToken(videoSourceToken);

		GetImagingSettingsResponse response = client.processRequest(request,
				GetImagingSettingsResponse.class, serviceUrl, true);
		if (response == null) {
			return null;
		}
		return response.getImagingSettings();
	}

	/* (non-Javadoc)
	 * @see org.onvif.unofficial.services.DeviceIOService#setImagingSettings(java.lang.String, org.onvif.ver10.schema.ImagingSettings20)
	 */
	@Override
	public boolean setImagingSettings(String videoSourceToken, ImagingSettings20 imagingSettings) throws Exception {
		if (videoSourceToken == null) {
			return false;
		}
		SetImagingSettings request = new SetImagingSettings();
		request.setVideoSourceToken(videoSourceToken);
		request.setImagingSettings(imagingSettings);
		SetImagingSettingsResponse response = client.processRequest(request,
				SetImagingSettingsResponse.class, serviceUrl, true);
		if (response == null) {
			return false;
		}
		return true;
	}
}