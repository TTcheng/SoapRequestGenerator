package wcc.components;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsdlRequestGenerator {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Boolean createRequest = true;
    private Boolean createResponse = true;

    public String generate(String url) throws Exception {
        WsdlProject project = new WsdlProject();
        WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, url);
        StringBuilder res = new StringBuilder();
        for (WsdlInterface wsdl : wsdls) {
            res.append("==========================\n");
            for (Operation operation : wsdl.getOperationList()) {
                WsdlOperation wsdlOperation = (WsdlOperation) operation;
                res.append("OP:").append(wsdlOperation.getName()).append('\n');
                if (createRequest) {
                    res.append("Request:").append('\n')
                            .append(wsdlOperation.createRequest(createRequest)).append('\n');
                }
                if (createResponse) {
                    res.append("Response:").append('\n')
                            .append(wsdlOperation.createResponse(createResponse)).append('\n');
                }
            }
            res.append("==========================\n");
        }
        return res.toString();
    }

    public Boolean getCreateResponse() {
        return createResponse;
    }

    public void setCreateResponse(Boolean createResponse) {
        this.createResponse = createResponse;
    }

    public Boolean getCreateRequest() {
        return createRequest;
    }

    public void setCreateRequest(Boolean createRequest) {
        this.createRequest = createRequest;
    }
}