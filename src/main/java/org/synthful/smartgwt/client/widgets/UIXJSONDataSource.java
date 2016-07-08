package org.synthful.smartgwt.client.widgets;

import org.synthful.smartgwt.client.UIMasqueradedDataSource;

import com.smartgwt.client.data.XJSONDataSource;

import java.util.Map;

public class UIXJSONDataSource
extends UIMasqueradedDataSource<XJSONDataSource>{

	@Override
	protected XJSONDataSource setDataSource(Map<String,String> parameters) {
		return new XJSONDataSource();
	}
}
