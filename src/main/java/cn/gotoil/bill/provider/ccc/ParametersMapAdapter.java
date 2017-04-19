/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.provider.ccc.ParametersMapAdapter
 *
 * cluries <cluries@me.com>,  August 2016
 *
 * LastModified: 8/29/16 9:47 PM
 *
 */

package cn.gotoil.bill.provider.ccc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ParametersMapAdapter extends XmlAdapter<Parameters, Map<String, String>> {

    @Override
    public Map<String, String> unmarshal(Parameters parameters) throws Exception {
        Map<String, String> r = new HashMap<>();
        for (ParameterElement mapelement : parameters.parameters) {
            r.put(mapelement.name, mapelement.value);
        }
        return r;
    }

    @Override
    public Parameters marshal(Map<String, String> map) throws Exception {

        ArrayList<ParameterElement> elementses = new ArrayList<>(map.size());

        map.forEach((k, v) -> elementses.add(new ParameterElement(k, v)));
        Parameters parameters = new Parameters();
        parameters.parameters = elementses.toArray(new ParameterElement[0]);
        return parameters;
    }
}


class ParameterElement {

    @XmlAttribute
    public String name;

    @XmlAttribute
    public String value;

    private ParameterElement() {

    }

    public ParameterElement(String name, String value) {
        this.name = name;
        this.value = value;
    }
}


class Parameters {

    @XmlElement(name = "parameter")
    public ParameterElement[] parameters;

}
