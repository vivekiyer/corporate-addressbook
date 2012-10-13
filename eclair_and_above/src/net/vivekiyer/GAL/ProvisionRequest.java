package net.vivekiyer.GAL;

import java.io.IOException;

import android.os.Build;

import com.android.exchange.adapter.Serializer;
import com.android.exchange.adapter.Tags;

public class ProvisionRequest extends CommandRequest {

	public String getPolicyType(){
		if (getProtocolVersionFloat() >= 12.0)
			return "MS-EAS-Provisioning-WBXML";		
		else
			return  "MS-WAP-Provisioning-XML";
	}
	public ProvisionRequest(String _uri, String _authString, boolean _useSSL,
			String _protocolVersion, boolean _acceptAllCerts, String _policyKey) {
		super(_uri, _authString, _useSSL, _protocolVersion, _acceptAllCerts, _policyKey);

		// Create the request
		setUri(getUri() + "Provision");

	}

	@Override
	protected void generateWBXMLPayload() throws IOException {
		Serializer s = new Serializer();

		s.start(Tags.PROVISION_PROVISION);
		if (getProtocolVersionFloat() >= 14.0) {
			// Send settings information in 14.1 and greater
			s.start(Tags.SETTINGS_DEVICE_INFORMATION).start(Tags.SETTINGS_SET);
			s.data(Tags.SETTINGS_FRIENDLY_NAME, "Corporate Addressbook");
			s.data(Tags.SETTINGS_MODEL, Build.MODEL);
			s.data(Tags.SETTINGS_OS, "Android " + Build.VERSION.RELEASE);
			s.data(Tags.SETTINGS_USER_AGENT, CorporateAddressBook.VERSION_STRING);
			s.end().end();  // SETTINGS_SET, SETTINGS_DEVICE_INFORMATION
		}
		s.start(Tags.PROVISION_POLICIES);
		s.start(Tags.PROVISION_POLICY).data(Tags.PROVISION_POLICY_TYPE, getPolicyType()).end();
		s.end();  // PROVISION_POLICIES
		s.end().done(); // PROVISION_PROVISION

		setWbxmlBytes(s.toByteArray());
	}
}
